package com.todoist.pe.exception;

public class ItemNotFoundException extends NotFoundException {

    public ItemNotFoundException(String id) {
        super(String.format("Item [%s] is not found", id));
    }
    
}
