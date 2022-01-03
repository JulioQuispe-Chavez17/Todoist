package com.todoist.pe.service.impl;

import com.mongodb.client.model.changestream.OperationType;
import com.todoist.pe.api.ItemPatchResource;
import com.todoist.pe.api.ItemResource;
import com.todoist.pe.api.ItemUpdateResource;
import com.todoist.pe.api.NewItemResource;
import com.todoist.pe.api.event.Event;
import com.todoist.pe.exception.ItemNotFoundException;
import com.todoist.pe.exception.UnexpectedItemVersionException;
import com.todoist.pe.mapper.ItemMapper;
import com.todoist.pe.model.Item;
import com.todoist.pe.repository.ItemRepository;
import com.todoist.pe.service.ItemService;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ChangeStreamOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    
    private static final Sort DEFAULT_SORT = Sort.by(Sort.Order.by("lastModifiedDate"));

    private final ReactiveMongoTemplate reactiveMongoTemplate;
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    
    @Override
    public Mono<ItemResource> create(NewItemResource item) {
        return itemRepository.save(itemMapper.toModel(item))
                .map(itemMapper::toResource);
    }

    @Override
    public Flux<ItemResource> findAll() {
        return itemRepository.findAll(DEFAULT_SORT)
                .map(itemMapper::toResource);
    }

    @Override
    public Mono<ItemResource> findById(String id, Long version) {
        return findItemById(id, version)
                .map(itemMapper::toResource);
    }

    @Override
    public Flux<Event> listenToEvents() {
        final ChangeStreamOptions changeStreamOptions = ChangeStreamOptions.builder()
                .returnFullDocumentOnUpdate()
                .filter(Aggregation.newAggregation(
                        Aggregation.match(Criteria.where("operationType")
                                .in(OperationType.INSERT.getValue(),
                                    OperationType.REPLACE.getValue(),
                                    OperationType.UPDATE.getValue(),
                                    OperationType.DELETE.getValue()))))
                                    .build();
        return reactiveMongoTemplate.changeStream("item", changeStreamOptions, Item.class)
                .map(itemMapper::toEvent);
    }

    @Override
    public Mono<ItemResource> update(String id, Long version, ItemUpdateResource itemUpdateResource) {
        return findItemById(id, version)
                .flatMap(item -> {
                    itemMapper.update(itemUpdateResource, item);
                    return itemRepository.save(item);
                })
                .map(itemMapper::toResource);
    }
    
    @Override
    @SuppressWarnings({"OptionalAssignedToNull", "OptionalGetWithoutIsPresent"})
    public Mono<ItemResource> patch(String id, Long version, ItemPatchResource itemPatchResource) {
        return findItemById(id, version)
        .flatMap(item -> {
            if (itemPatchResource.getDescription() != null) {
                // The description has been provided in the patch
                item.setDescription(itemPatchResource.getDescription().get());
            }

            if (itemPatchResource.getStatus() != null) {
                // The status has been provided in the patch
                item.setStatus(itemPatchResource.getStatus().get());
            }
            return itemRepository.save(item);
        })
        .map(itemMapper::toResource);
    }

    @Override
    public Mono<Void> deleteById(String id, Long version) {
        return findItemById(id, version)
            .flatMap(itemRepository::delete);
    }

    @Override
    public Mono<Item> findItemById(String id, Long expectedVersion) {
        return itemRepository.findById(id)
                .switchIfEmpty(Mono.error(new ItemNotFoundException(id)))
                .handle((item, sink) -> {
                    if (expectedVersion != null && !expectedVersion.equals(item.getVersion())) {
                        sink.error(new UnexpectedItemVersionException(expectedVersion, item.getVersion()));
                    } else {
                        sink.next(item);
                    }
                });
    }
    
}
