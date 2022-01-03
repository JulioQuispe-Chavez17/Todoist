package com.todoist.pe.api;

import lombok.Data;
import lombok.experimental.Accessors;
import java.time.Instant;
import com.todoist.pe.model.ItemStatus;

@Data
@Accessors(chain = true)
public class ItemResource {

    private String id;
    private Long version;

    private String description;
    private ItemStatus status;

    private Instant createdDate;
    private Instant lastModifiedDate;

}
