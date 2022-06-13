package com.bekzodkeldiyarov.collectionstore.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Privilege extends BaseEntity {
    private String name;

    @ManyToMany(mappedBy = "privileges")
    private List<Role> roles;
}
