package com.bekzodkeldiyarov.collectionstore.service;

import com.bekzodkeldiyarov.collectionstore.commands.UserCommand;
import com.bekzodkeldiyarov.collectionstore.converters.UserCommandToUser;
import com.bekzodkeldiyarov.collectionstore.converters.UserToUserCommand;
import com.bekzodkeldiyarov.collectionstore.exceptions.UserExistsException;
import com.bekzodkeldiyarov.collectionstore.model.User;
import com.bekzodkeldiyarov.collectionstore.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
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

    public UserServiceImpl(UserRepository userRepository, UserCommandToUser userCommandToUser, UserToUserCommand userToUserCommand) {
        this.userRepository = userRepository;
        this.userCommandToUser = userCommandToUser;
        this.userToUserCommand = userToUserCommand;
    }

    @Override
    public User findById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
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
    public UserCommand saveUserCommand(UserCommand userCommand) throws UserExistsException {
        User userToSave;
        if (findByUsername(userCommand.getUsername()) == null) {
            userToSave = userCommandToUser.convert(userCommand);
            if (userToSave != null) {
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
}
