package com.bekzodkeldiyarov.collectionstore.controllers;

import com.bekzodkeldiyarov.collectionstore.commands.UserCommand;
import com.bekzodkeldiyarov.collectionstore.model.Role;
import com.bekzodkeldiyarov.collectionstore.service.RoleService;
import com.bekzodkeldiyarov.collectionstore.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("")
    public String getAdminPage() {
        return "admin/index";
    }


    @GetMapping("/allusers")
    public String getAllUsers(Model model) {
        List<UserCommand> users = userService.findAll();
        model.addAttribute("users", users);
        return "admin/index";
    }


    @PostMapping(value = "/allusers")
    public String blockUsers(@RequestParam(required = false) Integer[] ids, @RequestParam String action) {
        for (Integer id : ids) {
            UserCommand userCommand = userService.findUserCommandById(id.longValue());
            changeUserStatus(action, userCommand);
            userService.saveUserCommand(userCommand);
        }
        return "redirect:/admin/allusers";
    }

    @PostMapping(value = "/allusers", params = "action=add-admin")
    public String addAdmin(@RequestParam(required = false) Integer[] ids) {
        for (Integer id : ids) {
            UserCommand userCommand = userService.findUserCommandById(id.longValue());
            userCommand.setEnabled(true);
            userService.saveUserCommand(userCommand);
        }
        return "redirect:/admin/allusers";
    }


    private void changeUserStatus(String action, UserCommand userCommand) {
        switch (action) {
            case "block": {
                userCommand.setEnabled(false);
                break;
            }
            case "unblock": {
                userCommand.setEnabled(true);
                break;
            }
            case "addAdmin": {
                Role admin = roleService.findByName("ROLE_ADMIN");
                userCommand.getRoles().add(admin);
                roleService.addUserCommand(admin, userCommand);
                break;
            }
            case "removeAdmin": {
                Role admin = roleService.findByName("ROLE_ADMIN");
                userCommand.getRoles().remove(admin);
                roleService.removeUserCommand(admin, userCommand);
                break;
            }
        }
    }
}
