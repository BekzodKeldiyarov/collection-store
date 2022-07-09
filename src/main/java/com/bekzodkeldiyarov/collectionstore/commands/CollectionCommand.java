package com.bekzodkeldiyarov.collectionstore.commands;

import com.bekzodkeldiyarov.collectionstore.model.Attribute;
import com.bekzodkeldiyarov.collectionstore.model.Item;
import com.bekzodkeldiyarov.collectionstore.model.User;
import lombok.*;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
@Setter
@Getter
@ToString
@Builder
public class CollectionCommand {
    private Long id;
    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String description;

    @NotNull
    @NotEmpty
    private User user;

    private Set<Item> items = new HashSet<>();

    private List<Attribute> attributes = new ArrayList<>();

    public CollectionCommand(Long id, String name, String description, User user, Set<Item> items, List<Attribute> attributes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.user = user;
        this.items = items;
        this.attributes = attributes;
    }

    public CollectionCommand() {
    }
}