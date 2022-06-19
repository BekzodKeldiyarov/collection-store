package com.bekzodkeldiyarov.collectionstore.service;

import com.bekzodkeldiyarov.collectionstore.commands.UserCommand;
import com.bekzodkeldiyarov.collectionstore.exceptions.UserExistsException;
import com.bekzodkeldiyarov.collectionstore.model.User;

import java.util.List;

public interface UserService {
    User findById(Long id);

    UserCommand findUserCommandById(Long id);

    User findByUsername(String username);

    User save(User user);

    UserCommand registerUserCommand(UserCommand userCommand) throws UserExistsException;

    UserCommand saveUserCommand(UserCommand userCommand);

    List<UserCommand> findAll();
}
