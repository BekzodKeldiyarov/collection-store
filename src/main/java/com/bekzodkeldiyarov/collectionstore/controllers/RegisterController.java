package com.bekzodkeldiyarov.collectionstore.controllers;

import com.bekzodkeldiyarov.collectionstore.commands.UserCommand;
import com.bekzodkeldiyarov.collectionstore.exceptions.UserExistsException;
import com.bekzodkeldiyarov.collectionstore.model.User;
import com.bekzodkeldiyarov.collectionstore.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Collections;

@Controller
@Slf4j
public class RegisterController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public RegisterController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        UserCommand userCommand = new UserCommand();
        model.addAttribute("user", userCommand);
        return "register";
    }


    @PostMapping("/register")
    public String handleRegisterRequest(@Valid UserCommand user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("UserCommand has errors");
            log.error(Collections.singletonList(bindingResult.getAllErrors()) + "");
            return "redirect:/register";
        }
        try {
            UserCommand savedUserCommand = userService.registerUserCommand(user);
        } catch (UserExistsException e) {
            return "redirect:/register";
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }
}
