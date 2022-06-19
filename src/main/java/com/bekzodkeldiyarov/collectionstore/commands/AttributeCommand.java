package com.bekzodkeldiyarov.collectionstore.commands;

import com.bekzodkeldiyarov.collectionstore.model.Collection;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
@Setter
@Getter
@ToString
@Builder
public class AttributeCommand {
    @NotEmpty
    @NotNull
    private String attributeName;

    @NotEmpty
    @NotNull
    private String type;

    private Collection collection;


}
