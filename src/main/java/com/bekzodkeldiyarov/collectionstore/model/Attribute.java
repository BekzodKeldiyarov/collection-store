package com.bekzodkeldiyarov.collectionstore.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Attribute extends BaseEntity {
    private String attributeName;
    private String type;

    @Builder
    public Attribute(Long id, String attributeName, String type, Collection collection) {
        super(id);
        this.attributeName = attributeName;
        this.type = type;
        this.collection = collection;
    }

    @ManyToOne
    @JoinColumn(name = "collection_id", referencedColumnName = "id", nullable = false)
    private Collection collection;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Attribute attribute = (Attribute) o;

        if (attributeName != null ? !attributeName.equals(attribute.attributeName) : attribute.attributeName != null)
            return false;
        if (type != null ? !type.equals(attribute.type) : attribute.type != null) return false;
        return collection != null ? collection.equals(attribute.collection) : attribute.collection == null;
    }

    @Override
    public int hashCode() {
        int result = attributeName != null ? attributeName.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (collection != null ? collection.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Attribute{" +
                "name='" + attributeName + '\'' +
                ", type='" + type + '\'' +
                ", collection=" + collection +
                "} " + super.toString();
    }
}
