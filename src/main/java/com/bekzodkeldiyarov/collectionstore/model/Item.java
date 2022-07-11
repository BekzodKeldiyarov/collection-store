package com.bekzodkeldiyarov.collectionstore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Indexed
@Table(name = "item")
@NoArgsConstructor
public class Item extends BaseEntity {
    @JsonIgnore
    @Field(termVector = TermVector.YES)
    private String name;

    @ManyToMany
    @JoinTable(name = "item_tags", joinColumns = @JoinColumn(name = "item_id"), inverseJoinColumns = @JoinColumn(name = "tab_id"))
    @JsonIgnore
    private Set<Tag> tags = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "collection_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    @IndexedEmbedded
    private Collection collection;

    @OneToMany(mappedBy = "item", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Like> likes = new ArrayList<>();

    @Builder
    public Item(Long id, String name, Collection collection, List<ItemAttributeValue> itemAttributeValues, Set<Tag> tags, List<Comment> comments, List<Like> likes) {
        super(id);
        this.name = name;
        this.collection = collection;
        this.itemAttributeValues = itemAttributeValues;
        this.tags = tags;
        this.comments = comments;
        this.likes = likes;
    }

    @OneToMany(mappedBy = "item", cascade = CascadeType.REMOVE)
    @JsonIgnore
    List<ItemAttributeValue> itemAttributeValues = new ArrayList<>();


    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + "id = " + getId() + ", " + "name = " + getName() + ", " + "collection = " + getCollection() + ")";
    }
}
