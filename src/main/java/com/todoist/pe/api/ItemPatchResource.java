package com.todoist.pe.api;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.todoist.pe.model.ItemStatus;

import java.util.Optional;

@Data
@Accessors(chain = true)
public class ItemPatchResource {

    private Optional<@NotBlank String> description;
    private Optional<@NotNull ItemStatus> status;

}