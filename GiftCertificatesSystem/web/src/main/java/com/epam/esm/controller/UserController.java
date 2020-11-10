package com.epam.esm.controller;

import com.epam.esm.entity.Order;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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
    public CollectionModel<Order> getUserOrders(@PathVariable String login) {
        List<Order> orders = userService.getUserOrders(login);
        orders.forEach(order -> {
            Link link = WebMvcLinkBuilder.linkTo(UserController.class)
                    .slash(login)
                    .slash("orders")
                    .slash(order.getId())
                    .withSelfRel();
            order.add(link);
        });
        return CollectionModel.of(orders);
    }

    @GetMapping(value = "/{login}/orders/{id}")
    public Order getUserOrderById(@PathVariable String login, @PathVariable long id) {
        Order order = userService.getUserOrderById(id);

        return order;
    }
}
