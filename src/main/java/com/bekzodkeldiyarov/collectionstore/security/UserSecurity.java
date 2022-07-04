package com.bekzodkeldiyarov.collectionstore.security;

import com.bekzodkeldiyarov.collectionstore.model.Collection;
import com.bekzodkeldiyarov.collectionstore.model.User;
import com.bekzodkeldiyarov.collectionstore.service.CollectionService;
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

    public UserSecurity(CollectionService collectionService, UserService userService) {
        this.collectionService = collectionService;
        this.userService = userService;
    }

    public boolean hasUserId(Authentication authentication, Long collectionId) {
        MyUserDetails user = (MyUserDetails) authentication.getPrincipal();
        User userFromDb = userService.findByUsername(user.getUsername());
        Collection collection = collectionService.findCollectionById(collectionId);
        log.info("the id of collection" + collectionId);
        log.info("the id of collection user" + collection.getUser().getId());
        log.info("the id of logged in user" + userFromDb.getId());
        if (Objects.equals(collection.getUser().getId(), userFromDb.getId())) {
            log.info("returning true");
            return true;
        }
        log.info("returning false");
        return false;
    }
}
