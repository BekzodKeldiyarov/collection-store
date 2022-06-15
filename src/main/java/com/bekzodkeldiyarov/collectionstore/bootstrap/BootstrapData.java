package com.bekzodkeldiyarov.collectionstore.bootstrap;

import com.bekzodkeldiyarov.collectionstore.model.Collection;
import com.bekzodkeldiyarov.collectionstore.model.Role;
import com.bekzodkeldiyarov.collectionstore.model.User;
import com.bekzodkeldiyarov.collectionstore.repository.CollectionRepository;
import com.bekzodkeldiyarov.collectionstore.service.CollectionService;
import com.bekzodkeldiyarov.collectionstore.service.RoleService;
import com.bekzodkeldiyarov.collectionstore.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Slf4j
public class BootstrapData implements ApplicationListener<ContextRefreshedEvent> {
    private final UserService userService;
    private final RoleService roleService;
    private final CollectionService collectionService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public BootstrapData(UserService userService, RoleService roleService, CollectionService collectionService) {
        this.userService = userService;
        this.roleService = roleService;
        this.collectionService = collectionService;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        User admin = new User();
        admin.setUsername("admin");
        admin.setEmail("admin@gmail.com");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setEnabled(true);

        User user = new User();
        user.setUsername("bekzod");
        user.setEmail("bekzod@gmail.com");
        user.setPassword(passwordEncoder.encode("bekzod"));
        user.setEnabled(true);

        User blockedUser = new User();
        blockedUser.setUsername("user");
        blockedUser.setEmail("user@gmail.com");
        blockedUser.setPassword(passwordEncoder.encode("user"));
        blockedUser.setEnabled(false);

        Collection collection = new Collection();
        collection.setUser(admin);
        collection.setName("My book");
        collection.setDescription("My books collection");

        Role role = new Role();
        role.setName("ROLE_ADMIN");
        role.getUsers().add(admin);

        admin.getRoles().add(role);

        admin.getCollections().add(collection);

        userService.save(admin);
        roleService.save(role);
        collectionService.save(collection);

        userService.save(user);
        userService.save(blockedUser);
        log.info("Data Bootstrapped");

    }
}
