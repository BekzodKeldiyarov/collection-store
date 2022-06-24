package com.bekzodkeldiyarov.collectionstore.bootstrap;

import com.bekzodkeldiyarov.collectionstore.commands.AttributeCommand;
import com.bekzodkeldiyarov.collectionstore.commands.CollectionCommand;
import com.bekzodkeldiyarov.collectionstore.model.*;
import com.bekzodkeldiyarov.collectionstore.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Component
@Slf4j
public class BootstrapData implements ApplicationListener<ContextRefreshedEvent> {
    private final UserService userService;
    private final RoleService roleService;
    private final CollectionService collectionService;
    private final ItemService itemService;
    private final AttributeService attributeService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public BootstrapData(UserService userService, RoleService roleService, CollectionService collectionService, ItemService itemService, AttributeService attributeService) {
        this.userService = userService;
        this.roleService = roleService;
        this.collectionService = collectionService;
        this.itemService = itemService;
        this.attributeService = attributeService;
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
        userService.save(user);

        User blockedUser = new User();
        blockedUser.setUsername("user");
        blockedUser.setEmail("user@gmail.com");
        blockedUser.setPassword(passwordEncoder.encode("user"));
        blockedUser.setEnabled(false);
        userService.save(blockedUser);


        CollectionCommand collectionCommand = CollectionCommand.builder()
                .name("My book")
                .description("My books collection")
                .user(admin)
                .items(new HashSet<>())
                .attributes(new HashSet<>())
                .build();

        Set<AttributeCommand> attributeCommands = new HashSet<>();
        attributeCommands.add(AttributeCommand.builder()
                .attributeName("Author")
                .type("String")
                .build());
        attributeCommands.add(AttributeCommand.builder()
                .attributeName("Published in")
                .type("Date")
                .build());


        Item item = new Item();
        item.setName("Robinzon");


        Role role = new Role();
        role.setName("ROLE_ADMIN");
        role.getUsers().add(admin);

        admin.getRoles().add(role);

//        admin.getCollections().add(collection);

        userService.save(admin);
        roleService.save(role);
        collectionService.saveCollectionCommand(collectionCommand);
        attributeService.bindAttributesToCollection(attributeCommands, collectionCommand);
//        itemService.save(item);
        log.info("Data Bootstrapped");

    }
}
