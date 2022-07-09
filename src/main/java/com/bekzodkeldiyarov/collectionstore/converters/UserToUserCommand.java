package com.bekzodkeldiyarov.collectionstore.converters;

import com.bekzodkeldiyarov.collectionstore.commands.UserCommand;
import com.bekzodkeldiyarov.collectionstore.model.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToUserCommand implements Converter<User, UserCommand> {
    @Override
    public UserCommand convert(User source) {
        UserCommand userCommand = new UserCommand();
        userCommand.setId(source.getId());
        userCommand.setUsername(source.getUsername());
        userCommand.setPassword(source.getPassword());
        userCommand.setEmail(source.getEmail());
        userCommand.setEnabled(source.isEnabled());
        userCommand.setRoles(source.getRoles());
        userCommand.setCollections(source.getCollections());
        return userCommand;
    }
}
