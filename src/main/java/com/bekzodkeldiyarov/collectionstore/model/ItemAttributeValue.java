package com.bekzodkeldiyarov.collectionstore.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "item_attribute")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItemAttributeValue extends BaseEntity {

    private String value;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Item item;
    
    @ManyToOne
    @JoinColumn(name = "attribute_id")
    private Attribute attribute;
}
