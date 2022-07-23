package com.bekzodkeldiyarov.collectionstore.service;

import com.bekzodkeldiyarov.collectionstore.dto.UserDto;
import com.bekzodkeldiyarov.collectionstore.exceptions.UserExistsException;
import com.bekzodkeldiyarov.collectionstore.model.User;

import java.util.List;
import java.util.Locale;

public interface UserService {
    User findById(Long id);

    UserDto findUserCommandById(Long id);

    User findByUsername(String username);

    User save(User user);

    UserDto registerUserCommand(UserDto userDto) throws UserExistsException;

    UserDto saveUserCommand(UserDto userDto);

    List<UserDto> findAll();

    void deleteById(Long id);

    void refreshUserSession();

    String getUsersPreferedLocaleOption(String username);

    void saveUsersPreferedLocaleOption(String username, Locale locale);
}
