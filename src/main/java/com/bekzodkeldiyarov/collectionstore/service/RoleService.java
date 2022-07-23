package com.bekzodkeldiyarov.collectionstore.service;

import com.bekzodkeldiyarov.collectionstore.dto.UserDto;
import com.bekzodkeldiyarov.collectionstore.model.Role;

public interface RoleService {
    Role save(Role role);

    Role findByName(String name);

    Role addUserCommand(Role role, UserDto userDto);
    Role removeUserCommand(Role role, UserDto userDto);
}
