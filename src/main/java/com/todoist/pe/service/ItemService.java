package com.todoist.pe.service;


import com.todoist.pe.api.ItemPatchResource;
import com.todoist.pe.api.ItemResource;
import com.todoist.pe.api.ItemUpdateResource;
import com.todoist.pe.api.NewItemResource;
import com.todoist.pe.api.event.Event;
import com.todoist.pe.model.Item;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ItemService {
    public Mono<ItemResource> create(final NewItemResource item);
    public Flux<ItemResource> findAll();
    public Mono<ItemResource> findById(final String id, final Long version);
    public Flux<Event> listenToEvents();
    public Mono<ItemResource> update(final String id, final Long version, final ItemUpdateResource itemUpdateResource);
    public Mono<ItemResource> patch(final String id, final Long version, final ItemPatchResource itemPatchResource);
    public Mono<Void> deleteById(final String id, final Long version);
    public Mono<Item> findItemById(final String id, final Long expectedVersion);
}
