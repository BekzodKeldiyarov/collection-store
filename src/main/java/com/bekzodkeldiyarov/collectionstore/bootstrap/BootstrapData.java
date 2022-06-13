package com.bekzodkeldiyarov.collectionstore.bootstrap;

import com.bekzodkeldiyarov.collectionstore.model.Role;
import com.bekzodkeldiyarov.collectionstore.model.User;
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
    @Autowired
    private PasswordEncoder passwordEncoder;

    public BootstrapData(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        User user = new User();
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("admin"));
        user.setEnabled(true);


        Role role = new Role();
        role.setName("ROLE_ADMIN");

        user.getRoles().add(role);
        role.getUsers().add(user);
        userService.save(user);
        roleService.save(role);

        log.info("Bootstrap data loaded");
    }

}
