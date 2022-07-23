package com.bekzodkeldiyarov.collectionstore.service;

import com.bekzodkeldiyarov.collectionstore.dto.UserDto;
import com.bekzodkeldiyarov.collectionstore.converters.UserCommandToUser;
import com.bekzodkeldiyarov.collectionstore.converters.UserToUserCommand;
import com.bekzodkeldiyarov.collectionstore.exceptions.UserExistsException;
import com.bekzodkeldiyarov.collectionstore.model.User;
import com.bekzodkeldiyarov.collectionstore.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserCommandToUser userCommandToUser;
    private final UserToUserCommand userToUserCommand;
    private final PasswordEncoder passwordEncoder;

    private final UserSession userSession;

    public UserServiceImpl(UserRepository userRepository, UserCommandToUser userCommandToUser, UserToUserCommand userToUserCommand, PasswordEncoder passwordEncoder, @Lazy UserSession userSession) {
        this.userRepository = userRepository;
        this.userCommandToUser = userCommandToUser;
        this.userToUserCommand = userToUserCommand;
        this.passwordEncoder = passwordEncoder;
        this.userSession = userSession;
    }


    @Override
    public User findById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }

    @Override
    public UserDto findUserCommandById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        UserDto userDto = null;
        if (optionalUser.isPresent()) {
            userDto = userToUserCommand.convert(optionalUser.get());
        }
        return userDto;
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
    public UserDto registerUserCommand(UserDto userDto) throws UserExistsException {
        User userToSave;
        if (findByUsername(userDto.getUsername()) == null) {
            userToSave = userCommandToUser.convert(userDto);
            if (userToSave != null) {
                userToSave.setPassword(passwordEncoder.encode(userToSave.getPassword()));
                userToSave.setEnabled(true);
                userRepository.save(userToSave);
            } else {
                log.error("Failed to save");
            }
        } else {
            throw new UserExistsException("The user with username " + userDto.getUsername() + " exists");
        }
        return userToUserCommand.convert(userToSave);
    }

    @Override
    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        List<UserDto> usersToReturn = new ArrayList<>();
        for (User user : users) {
            UserDto userDto = userToUserCommand.convert(user);
            if (userDto != null) {
                usersToReturn.add(userDto);
            }
        }
        return usersToReturn;
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }


    @Override
    public UserDto saveUserCommand(UserDto userDto) {
        User userToSave = userCommandToUser.convert(userDto);
        if (userToSave != null) {
            User savedUser = userRepository.save(userToSave);
            return userToUserCommand.convert(savedUser);
        }
        log.error("Failed to save changes for user " + userDto.getUsername());
        return userDto;
    }

    @Override
    public void refreshUserSession() {
        userSession.expireSessionForNonActiveUsers();
    }

    @Override
    public String getUsersPreferedLocaleOption(String username) {
        return userRepository.findByUsername(username).getPreferLocale();
    }

    @Override
    public void saveUsersPreferedLocaleOption(String username, Locale locale) {
        User user = userRepository.findByUsername(username);
        user.setPreferLocale(locale.getLanguage());
        userRepository.save(user);
    }
}
