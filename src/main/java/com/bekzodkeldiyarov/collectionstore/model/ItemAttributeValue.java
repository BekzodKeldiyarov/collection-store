package com.bekzodkeldiyarov.collectionstore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "item_attribute")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItemAttributeValue extends BaseEntity {

    private String value;

    @ManyToOne()
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Item item;

    @ManyToOne()
    @JoinColumn(name = "attribute_id")
    @JsonIgnore
    private Attribute attribute;
}
