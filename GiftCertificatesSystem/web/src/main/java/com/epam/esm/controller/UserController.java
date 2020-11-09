package com.epam.esm.controller;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping(value = "/{login}/orders")
    public List<Order> getUserOrders(@PathVariable String login) {
        return userService.getUserOrders(login);
    }
}
