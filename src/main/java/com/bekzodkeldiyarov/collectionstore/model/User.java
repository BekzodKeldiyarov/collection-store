package com.bekzodkeldiyarov.collectionstore.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "usr")
@ToString
public class User extends BaseEntity {
    private String username;
    @ToString.Exclude
    @JsonIgnore
    private String password;
    @JsonIgnore
    private String email;
    @JsonIgnore
    private boolean isEnabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    @JsonIgnore
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    @JsonIgnore
    private Set<Collection> collections = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    @JsonIgnore
    private List<Comment> comments = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!username.equals(user.username)) return false;
        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        int result = username.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }
}
