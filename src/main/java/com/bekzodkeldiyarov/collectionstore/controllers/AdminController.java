package com.bekzodkeldiyarov.collectionstore.controllers;

import com.bekzodkeldiyarov.collectionstore.commands.UserCommand;
import com.bekzodkeldiyarov.collectionstore.model.User;
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

    public AdminController(UserService userService) {
        this.userService = userService;
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


    @PostMapping(value = "/allusers", params = "action=block")
    public String blockUsers(@RequestParam(required = false) Integer[] ids) {
        for (Integer id : ids) {
            UserCommand userCommand = userService.findUserCommandById(id.longValue());
            userCommand.setEnabled(false);
            userService.saveUserCommand(userCommand);
        }
        return "redirect:/admin/allusers";
    }

    @PostMapping(value = "/allusers", params = "action=unblock")
    public String unblockUsers(@RequestParam(required = false) Integer[] ids) {
        for (Integer id : ids) {
            UserCommand userCommand = userService.findUserCommandById(id.longValue());
            userCommand.setEnabled(true);
            userService.saveUserCommand(userCommand);
        }
        return "redirect:/admin/allusers";
    }
}
