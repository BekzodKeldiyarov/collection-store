package com.bekzodkeldiyarov.collectionstore.converters;

import com.bekzodkeldiyarov.collectionstore.dto.UserDto;
import com.bekzodkeldiyarov.collectionstore.model.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToUserCommand implements Converter<User, UserDto> {
    @Override
    public UserDto convert(User source) {
        UserDto userDto = new UserDto();
        userDto.setId(source.getId());
        userDto.setUsername(source.getUsername());
        userDto.setPassword(source.getPassword());
        userDto.setEmail(source.getEmail());
        userDto.setEnabled(source.isEnabled());
        userDto.setRoles(source.getRoles());
        userDto.setCollections(source.getCollections());
        return userDto;
    }
}
