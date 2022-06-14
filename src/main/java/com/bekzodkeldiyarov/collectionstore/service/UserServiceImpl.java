package com.bekzodkeldiyarov.collectionstore.service;

import com.bekzodkeldiyarov.collectionstore.commands.UserCommand;
import com.bekzodkeldiyarov.collectionstore.converters.UserCommandToUser;
import com.bekzodkeldiyarov.collectionstore.converters.UserToUserCommand;
import com.bekzodkeldiyarov.collectionstore.exceptions.UserExistsException;
import com.bekzodkeldiyarov.collectionstore.model.User;
import com.bekzodkeldiyarov.collectionstore.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserCommandToUser userCommandToUser;
    private final UserToUserCommand userToUserCommand;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserCommandToUser userCommandToUser, UserToUserCommand userToUserCommand, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userCommandToUser = userCommandToUser;
        this.userToUserCommand = userToUserCommand;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }

    @Override
    public UserCommand findUserCommandById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        UserCommand userCommand = null;
        if (optionalUser.isPresent()) {
            userCommand = userToUserCommand.convert(optionalUser.get());
        }
        return userCommand;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public UserCommand registerUserCommand(UserCommand userCommand) throws UserExistsException {
        User userToSave;
        if (findByUsername(userCommand.getUsername()) == null) {
            userToSave = userCommandToUser.convert(userCommand);
            if (userToSave != null) {
                userToSave.setPassword(passwordEncoder.encode(userToSave.getPassword()));
                userToSave.setEnabled(true);
                userRepository.save(userToSave);
            } else {
                log.error("Failed to save");
            }
        } else {
            throw new UserExistsException("The user with username " + userCommand.getUsername() + " exists");
        }
        return userToUserCommand.convert(userToSave);
    }

    @Override
    public List<UserCommand> findAll() {
        List<User> users = userRepository.findAll();
        List<UserCommand> usersToReturn = new ArrayList<>();
        for (User user : users) {
            UserCommand userCommand = userToUserCommand.convert(user);
            if (userCommand != null) {
                usersToReturn.add(userCommand);
            }
        }
        return usersToReturn;
    }


    @Override
    public UserCommand saveUserCommand(UserCommand userCommand) {
        User userToSave = userCommandToUser.convert(userCommand);
        if (userToSave != null) {
            User savedUser = userRepository.save(userToSave);
            return userToUserCommand.convert(savedUser);
        }
        log.error("Failed to save changes for user " + userCommand.getUsername());
        return userCommand;
    }
}
