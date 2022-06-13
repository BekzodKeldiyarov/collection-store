package com.bekzodkeldiyarov.collectionstore.bootstrap;

import com.bekzodkeldiyarov.collectionstore.model.Privilege;
import com.bekzodkeldiyarov.collectionstore.service.PrivilegeService;
import com.bekzodkeldiyarov.collectionstore.service.RoleService;
import com.bekzodkeldiyarov.collectionstore.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Slf4j
public class BootstrapData implements ApplicationListener<ContextRefreshedEvent> {
    private final UserService userService;
    private final RoleService roleService;
    private final PrivilegeService privilegeService;

    public BootstrapData(UserService userService, RoleService roleService, PrivilegeService privilegeService) {
        this.userService = userService;
        this.roleService = roleService;
        this.privilegeService = privilegeService;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

    }

    public Privilege createPrivilege(String name) {
        if (privilegeService.findByName(name) != null) {
            log.error("Privilege with name " + name + " already exists");
        }
        Privilege privilege = new Privilege();
        privilege.setName(name);
        return privilegeService.save(privilege);
    }
}
