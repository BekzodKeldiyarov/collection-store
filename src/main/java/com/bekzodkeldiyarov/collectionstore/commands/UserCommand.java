package com.bekzodkeldiyarov.collectionstore.commands;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Setter
@Getter
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

    @NotNull(message = "password matching  cannot be empty.")
    @NotEmpty(message = "password matching  cannot be empty.")
    private String matchingPassword;
}
