package com.bekzodkeldiyarov.collectionstore.model;

import com.bekzodkeldiyarov.collectionstore.deserializer.ItemAttributeValueDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "item_attribute")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonDeserialize(using = ItemAttributeValueDeserializer.class)
public class ItemAttributeValue extends BaseEntity {

    private String value;

    @ManyToOne()
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Item item;

    @ManyToOne()
    @JoinColumn(name = "attribute_id")
    private Attribute attribute;

    public void setItemOfItemAttributeValue(Item item) {
        this.item = item;
        item.getItemAttributeValues().add(this);
    }

    public void setAttributeOfItemAttributeValue(Attribute attribute) {
        this.attribute = attribute;
        attribute.getItemAttributeValues().add(this);
    }
}

