package com.bekzodkeldiyarov.collectionstore.commands;


import com.bekzodkeldiyarov.collectionstore.model.Collection;
import com.bekzodkeldiyarov.collectionstore.model.Comment;
import com.bekzodkeldiyarov.collectionstore.model.Role;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Getter
@Setter
@NoArgsConstructor
public class UserCommand {
    private Long id;
    @NotNull(message = "Username cannot be empty.")
    @NotEmpty(message = "Username name cannot be empty.")
    private String username;

    @NotNull(message = "password cannot be empty.")
    @NotEmpty(message = "password cannot be empty.")
    private String password;

    @NotNull(message = "email cannot be empty")
    @NotEmpty(message = "email cannot be empty")
    @Email(message = "enter valid email")
    private String email;

    private Set<Collection> collections = new HashSet<>();

    private boolean isEnabled;
    private Set<Role> roles = new HashSet<>();

    private List<Comment> comments = new ArrayList<>();
}
