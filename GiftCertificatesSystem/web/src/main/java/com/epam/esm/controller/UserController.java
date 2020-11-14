package com.epam.esm.controller;

import com.epam.esm.entity.Order;
import com.epam.esm.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
@RequiredArgsConstructor
public class UserController {
    private final OrderService orderService;

    @GetMapping(value = "/{login}/orders")
    public CollectionModel<Order> getUserOrders(
            @PathVariable String login,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "page_size", required = false, defaultValue = "10") int pageSize
    ) {
        List<Order> orders = orderService.getUserOrders(login, page, pageSize);
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
    public EntityModel<Order> getUserOrderById(@PathVariable String login, @PathVariable long id) {
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
