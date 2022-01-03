package com.todoist.pe.model;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(of = "id")
@Data
@Accessors(chain = true)
@Document(collection = "item")
public class Item {
    @Id
    private String id;
    @Version
    private Long version;
    private String description;
    private ItemStatus status = ItemStatus.TODO;
    @CreatedDate
    private Instant createdDate;
    @LastModifiedDate
    private Instant lastModifiedDate;
}
