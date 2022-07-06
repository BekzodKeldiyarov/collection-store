package com.bekzodkeldiyarov.collectionstore.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Comment extends BaseEntity {
    private String text;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @Builder
    public Comment(Long id, String text, User user, Item item) {
        super(id);
        this.text = text;
        this.user = user;
        this.item = item;
    }
}
