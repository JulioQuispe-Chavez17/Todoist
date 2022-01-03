package com.todoist.pe.controller;

import java.time.Duration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.todoist.pe.api.ItemPatchResource;
import com.todoist.pe.api.ItemResource;
import com.todoist.pe.api.ItemUpdateResource;
import com.todoist.pe.api.NewItemResource;
import com.todoist.pe.api.event.Event;
import com.todoist.pe.api.event.HeartBeat;
import com.todoist.pe.config.ServerSentEventConfig;
import com.todoist.pe.service.ItemService;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpHeaders.IF_MATCH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.http.codec.ServerSentEvent;

@RestController
@RequestMapping(value = "/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ServerSentEventConfig sseConfig;
    private final ItemService itemService;


    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ItemResource> getAllItems() {
        return itemService.findAll();
    }

    @GetMapping(value = "events")
    public Flux<ServerSentEvent<Event>> getEventStream() {

        Flux<Event> eventMessageFlux = itemService.listenToEvents();

        // Send a heart beat every x ms to keep the connection alive
        if(sseConfig.getHeartBeatDelayMs() > 0) {
            log.info("Send a heart beat every {}ms ", sseConfig.getHeartBeatDelayMs());
            final Flux<Event> beats = Flux.interval(Duration.ofMillis(sseConfig.getHeartBeatDelayMs()))
                .map(sequence -> new HeartBeat());
            eventMessageFlux = Flux.merge(beats, eventMessageFlux);
        }

        return eventMessageFlux
                .map(event -> ServerSentEvent.<Event>builder()
                    .retry(Duration.ofMillis(sseConfig.getReconnectionDelayMs()))
                    .event(event.getClass().getSimpleName())
                    .data(event).build())
                .doFinally(signal -> log.info("Item event stream - {}", signal));
    }


    @PostMapping
    public Mono<ItemResource> create(@Valid @RequestBody final NewItemResource item) {
        return itemService.create(item);
    }


    @PutMapping(value = "/{id}")
    public Mono<ItemResource> update(@PathVariable @NotNull final String id,
                                     @RequestHeader(name = IF_MATCH, required = false) Long version,
                                     @Valid @RequestBody ItemUpdateResource itemUpdateResource) {

        return itemService.update(id, version, itemUpdateResource);
    }

    @PatchMapping(value = "/{id}")
    public Mono<ItemResource> update(@PathVariable @NotNull final String id,
                                     @RequestHeader(name = IF_MATCH, required = false) Long version,
                                     @Valid @RequestBody ItemPatchResource itemPatchResource) {

        return itemService.patch(id, version, itemPatchResource);
    }



    @GetMapping(value = "/{id}", produces = {APPLICATION_JSON_VALUE})
    public Mono<ItemResource> findById(@PathVariable String id) {
        return itemService.findById(id, null);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable final String id,
                             @RequestHeader(name = IF_MATCH, required = false) Long version) {

        return itemService.deleteById(id, version);
    }

}