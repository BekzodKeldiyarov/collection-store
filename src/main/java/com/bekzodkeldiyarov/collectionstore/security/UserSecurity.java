package com.bekzodkeldiyarov.collectionstore.security;

import com.bekzodkeldiyarov.collectionstore.model.Collection;
import com.bekzodkeldiyarov.collectionstore.model.Role;
import com.bekzodkeldiyarov.collectionstore.model.User;
import com.bekzodkeldiyarov.collectionstore.service.CollectionService;
import com.bekzodkeldiyarov.collectionstore.service.RoleService;
import com.bekzodkeldiyarov.collectionstore.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component("userSecurity")
@Slf4j
public class UserSecurity {
    private final CollectionService collectionService;
    private final UserService userService;
    private final RoleService roleService;

    public UserSecurity(CollectionService collectionService, UserService userService, RoleService roleService) {
        this.collectionService = collectionService;
        this.userService = userService;
        this.roleService = roleService;

    }

    public boolean hasUserId(Authentication authentication, String collectionId) {
        long id;
        try {
            id = Long.parseLong(collectionId);
            MyUserDetails user = (MyUserDetails) authentication.getPrincipal();
            User userFromDb = userService.findByUsername(user.getUsername());
            Role adminRole = roleService.findByName("ROLE_ADMIN");
            Collection collection = collectionService.findCollectionById(id);
            log.info(userFromDb.getRoles().iterator().next().getName());
            log.info(userFromDb.getRoles().contains(adminRole) + "");
            if (Objects.equals(collection.getUser().getId(), userFromDb.getId()) || userFromDb.getRoles().contains(adminRole)) {
                log.info("returning true");
                return true;
            }
        } catch (NumberFormatException e) {
            log.error("Not trying to connect any collection");
            e.getMessage();
        }

        log.info("returning false");
        return false;
    }

    public boolean userIsAdmin(String username) {
        User userFromDb = userService.findByUsername(username);
        Role adminRole = roleService.findByName("ROLE_ADMIN");
        if (userFromDb.getRoles().contains(adminRole)) {
            return true;
        }
        return false;
    }


}
