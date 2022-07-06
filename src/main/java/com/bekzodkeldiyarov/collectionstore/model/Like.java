package com.bekzodkeldiyarov.collectionstore.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "likes")
@Table(uniqueConstraints=
@UniqueConstraint(columnNames = {"user_id", "item_id"}))
@Getter
@Setter
@NoArgsConstructor
public class Like extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
}
