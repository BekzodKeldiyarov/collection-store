package com.bekzodkeldiyarov.collectionstore.service;

import com.bekzodkeldiyarov.collectionstore.commands.UserCommand;
import com.bekzodkeldiyarov.collectionstore.exceptions.UserExistsException;
import com.bekzodkeldiyarov.collectionstore.model.User;

import java.util.Optional;

public interface UserService {
    User findById(Long id);

    User findByUsername(String username);

    User save(User user);

    UserCommand saveUserCommand(UserCommand userCommand) throws UserExistsException;
}
