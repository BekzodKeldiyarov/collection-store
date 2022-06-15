package com.bekzodkeldiyarov.collectionstore.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Tag  extends BaseEntity{
    private String name;
}
