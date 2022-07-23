package com.bekzodkeldiyarov.collectionstore.converters;

import com.bekzodkeldiyarov.collectionstore.dto.UserDto;
import com.bekzodkeldiyarov.collectionstore.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserCommandToUser implements Converter<UserDto, User> {

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public User convert(UserDto source) {
        // todo think about what to do is source null

        User user = new User();
        user.setId(source.getId());
        user.setUsername(source.getUsername());
        user.setPassword(source.getPassword());
        user.setEmail(source.getEmail());
        user.setRoles(source.getRoles());
        user.setEnabled(source.isEnabled());
        user.setCollections(source.getCollections());
        return user;
    }
}
