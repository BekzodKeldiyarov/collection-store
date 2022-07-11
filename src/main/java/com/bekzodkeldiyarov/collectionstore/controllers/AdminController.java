package com.bekzodkeldiyarov.collectionstore.controllers;

import com.bekzodkeldiyarov.collectionstore.commands.UserCommand;
import com.bekzodkeldiyarov.collectionstore.model.Role;
import com.bekzodkeldiyarov.collectionstore.repository.AttributeRepository;
import com.bekzodkeldiyarov.collectionstore.service.CollectionService;
import com.bekzodkeldiyarov.collectionstore.service.RoleService;
import com.bekzodkeldiyarov.collectionstore.service.UserService;
import com.bekzodkeldiyarov.collectionstore.service.UserSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@Controller
@Slf4j
@RequestMapping("/dashboard/users")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;
    private final CollectionService collectionService;
    private final AttributeRepository attributeRepository; //todo change reposityory implementation to service

    public AdminController(UserService userService, RoleService roleService, CollectionService collectionService, AttributeRepository attributeRepository) {
        this.userService = userService;
        this.roleService = roleService;
        this.collectionService = collectionService;
        this.attributeRepository = attributeRepository;
    }


    @GetMapping()
    @Secured("ROLE_ADMIN")
    public String getAllUsers(Model model) {
        List<UserCommand> users = userService.findAll();
        model.addAttribute("users", users);
        return "admin/users/users";
    }

    @GetMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public String getSingleUserPage(@PathVariable Long id, Model model) {
        UserCommand user = userService.findUserCommandById(id);
        model.addAttribute("user", user);
        return "admin/users/single-user";
    }

    @PostMapping(value = "/{idOfUser}", params = "action=delete-collections")
    @Secured("ROLE_ADMIN")
    public String changeUserCollectionAndItems(@PathVariable Long idOfUser, @RequestParam(required = false) Long[] ids) {
        if (ids == null) {
            return "redirect:/dashboard/users/" + idOfUser;
        }
        collectionService.deleteCollectionsOfUserById(ids, idOfUser);
        return "redirect:/dashboard/users/" + idOfUser;
    }


    @PostMapping()
    @Secured("ROLE_ADMIN")
    public String changeUsers(@RequestParam(required = false) Integer[] ids, @RequestParam String action) {
        if (ids == null) {
            return "redirect:/dashboard/users";
        }
        for (Integer id : ids) {
            UserCommand userCommand = userService.findUserCommandById(id.longValue());
            changeUserStatus(action, userCommand);

        }
        return "redirect:/dashboard/users";
    }

    private void changeUserStatus(String action, UserCommand userCommand) {
        switch (action) {
            case "block": {
                userCommand.setEnabled(false);
                userService.saveUserCommand(userCommand);
                log.info("calling expire method() in controller");
                userService.refreshUserSession();
                break;
            }
            case "unblock": {
                userCommand.setEnabled(true);
                userService.saveUserCommand(userCommand);
                break;
            }
            case "addAdmin": {
                Role admin = roleService.findByName("ROLE_ADMIN");
                userCommand.getRoles().add(admin);
                roleService.addUserCommand(admin, userCommand);
                userService.saveUserCommand(userCommand);
                break;
            }
            case "removeAdmin": {
                Role admin = roleService.findByName("ROLE_ADMIN");
                userCommand.getRoles().remove(admin);
                roleService.removeUserCommand(admin, userCommand);
                userService.saveUserCommand(userCommand);
                userService.refreshUserSession();
                break;
            }
            case "remove": {
                userService.deleteById(userCommand.getId());
                userService.refreshUserSession();
                break;
            }
        }
    }
}
