package com.bekzodkeldiyarov.collectionstore.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Collection extends BaseEntity {
    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "collection")
    @ToString.Exclude
    private Set<Item> items = new HashSet<>();

    @OneToMany(mappedBy = "collection")
    @ToString.Exclude
    private Set<Attribute> attributes = new HashSet<>();

    @Builder
    public Collection(Long id, String name, String description, User user, Set<Item> items, Set<Attribute> attributes) {
        super(id);
        this.name = name;
        this.description = description;
        this.user = user;
        this.items = items;
        this.attributes = attributes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Collection that = (Collection) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public List<Attribute> getAttributesAsList() {
        List<Attribute> attributes = new ArrayList<>(this.attributes);
        return attributes;
    }

    public void setAttributesAsList(List<Attribute> attributes) {
        this.attributes = new HashSet<>(attributes);
    }
}
