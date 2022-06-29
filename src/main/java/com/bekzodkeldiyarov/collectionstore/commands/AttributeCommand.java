package com.bekzodkeldiyarov.collectionstore.commands;

import com.bekzodkeldiyarov.collectionstore.model.Collection;
import com.bekzodkeldiyarov.collectionstore.model.Item;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;


@Data
@Setter
@Getter
@ToString
@Builder
public class AttributeCommand {
    private Long id;
    @NotEmpty
    @NotNull
    private String attributeName;

    @NotEmpty
    @NotNull
    private String type;

    private Collection collection;

    private Set<Item> items = new HashSet<>();


}
