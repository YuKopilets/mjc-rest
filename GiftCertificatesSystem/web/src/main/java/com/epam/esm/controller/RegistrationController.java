package com.epam.esm.controller;

import com.epam.esm.entity.User;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * The type Registration controller.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see Controller
 */
@Controller
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String createUser(User user) {
        userService.singUp(user);
        return "redirect:/login";
    }

    @GetMapping("/")
    public String greeting() {
        return "greeting";
    }
}
