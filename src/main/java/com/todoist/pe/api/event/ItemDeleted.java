package com.todoist.pe.api.event;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ItemDeleted implements Event {

    private String itemId;

}
