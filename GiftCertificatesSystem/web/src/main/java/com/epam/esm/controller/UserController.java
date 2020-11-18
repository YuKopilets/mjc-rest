package com.epam.esm.controller;

import com.epam.esm.dao.PageRequest;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * The type User controller.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see RestController
 */
@RestController
@RequestMapping("/users")
@Validated
@Api(value = "/users", tags = "User operations")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final OrderService orderService;

    @GetMapping("/id/{id}")
    @ApiOperation(value = "get user by id")
    public User getUserById(@PathVariable @Min(value = 1) long id) {
        User user = userService.getUserById(id);
        Link link = WebMvcLinkBuilder.linkTo(UserController.class)
                .slash(user.getLogin())
                .slash("orders")
                .withRel("orders");
        user.add(link);
        return user;
    }

    @GetMapping("/login/{login}")
    @ApiOperation(value = "get user by login")
    public User getUserByLogin(@PathVariable @Size(min = 4, max = 50) String login) {
        User user = userService.getUserByLogin(login);
        Link link = WebMvcLinkBuilder.linkTo(UserController.class)
                .slash(login)
                .slash("orders")
                .withRel("orders");
        user.add(link);
        return user;
    }

    @GetMapping(value = "/{login}/orders")
    @ApiOperation(value = "get list of user's orders by login")
    public CollectionModel<Order> getUserOrders(
            @PathVariable @Size(min = 4, max = 50) String login,
            @RequestParam(name = "page", required = false, defaultValue = "1") @Min(value = 1) int page,
            @RequestParam(name = "page_size", required = false, defaultValue = "10")
            @Min(value = 8) @Max(value = 20) int pageSize
    ) {
        PageRequest pageRequest = new PageRequest(page, pageSize);
        List<Order> orders = orderService.getUserOrders(login, pageRequest);
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
    @ApiOperation(value = "get user's order by id")
    public EntityModel<Order> getUserOrderById(@PathVariable @Size(min = 4, max = 50) String login,
                                               @PathVariable @Min(value = 1) long id) {
        Order order = orderService.getUserOrderById(id);
        order.getGiftCertificates().forEach(giftCertificate -> {
            Link link = WebMvcLinkBuilder.linkTo(GiftCertificateController.class)
                    .slash(giftCertificate.getId())
                    .withRel("giftCertificates");
            order.add(link);
        });
        Link link = WebMvcLinkBuilder.linkTo(UserController.class)
                .slash(login)
                .slash("orders")
                .withRel("allUserOrders");
        return EntityModel.of(order, link);
    }
}
