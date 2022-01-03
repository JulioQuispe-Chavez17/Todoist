package com.todoist.pe.api.event;

import com.todoist.pe.api.ItemResource;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ItemSaved implements Event {

    private ItemResource item;

}