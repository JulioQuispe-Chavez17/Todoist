package com.todoist.pe.repository;

import com.todoist.pe.model.Item;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ItemRepository extends ReactiveMongoRepository<Item, String> {
    
}
