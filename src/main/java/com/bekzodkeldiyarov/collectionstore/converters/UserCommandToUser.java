package com.bekzodkeldiyarov.collectionstore.converters;

import com.bekzodkeldiyarov.collectionstore.commands.UserCommand;
import com.bekzodkeldiyarov.collectionstore.model.User;
import com.bekzodkeldiyarov.collectionstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserCommandToUser implements Converter<UserCommand, User> {

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public User convert(UserCommand source) {
        // todo think about what to do is source null

        User user = new User();
        user.setUsername(source.getUsername());
        user.setPassword(passwordEncoder.encode(source.getPassword()));
        user.setEmail(source.getEmail());
        user.setEnabled(true);
        return user;
    }
}
