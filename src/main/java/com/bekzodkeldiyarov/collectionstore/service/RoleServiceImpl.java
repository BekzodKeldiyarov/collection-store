package com.bekzodkeldiyarov.collectionstore.service;

import com.bekzodkeldiyarov.collectionstore.commands.UserCommand;
import com.bekzodkeldiyarov.collectionstore.converters.UserCommandToUser;
import com.bekzodkeldiyarov.collectionstore.model.Role;
import com.bekzodkeldiyarov.collectionstore.model.User;
import com.bekzodkeldiyarov.collectionstore.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final UserCommandToUser userCommandToUser;

    public RoleServiceImpl(RoleRepository roleRepository, UserCommandToUser userCommandToUser) {
        this.roleRepository = roleRepository;
        this.userCommandToUser = userCommandToUser;
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public Role addUserCommand(Role role, UserCommand userCommand) {
        User userToAdd = userCommandToUser.convert(userCommand);
        role.getUsers().add(userToAdd);
        log.info(role.toString());
        roleRepository.save(role);
        return role;
    }

    @Override
    public Role removeUserCommand(Role role, UserCommand userCommand) {
        User userToAdd = userCommandToUser.convert(userCommand);
        role.getUsers().remove(userToAdd);
        log.info(role.toString());
        roleRepository.save(role);
        return role;
    }
}
