package com.bekzodkeldiyarov.collectionstore.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Tag extends BaseEntity {
    @Builder
    public Tag(Long id, String name) {
        super(id);
        this.name = name;
    }

    private String name;
    @ManyToMany(mappedBy = "tags")
    @ToString.Exclude
    private Set<Item> items = new HashSet<>();
}
