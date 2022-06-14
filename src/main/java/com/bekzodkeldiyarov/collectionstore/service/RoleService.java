package com.bekzodkeldiyarov.collectionstore.service;

import com.bekzodkeldiyarov.collectionstore.commands.UserCommand;
import com.bekzodkeldiyarov.collectionstore.model.Role;

public interface RoleService {
    Role save(Role role);

    Role findByName(String name);

    Role addUserCommand(Role role, UserCommand userCommand);
    Role removeUserCommand(Role role, UserCommand userCommand);
}
