package com.todoist.pe.api;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.todoist.pe.model.ItemStatus;

@Data
@Accessors(chain = true)
public class ItemUpdateResource {

    @NotBlank
    private String description;

    @NotNull
    private ItemStatus status;

}