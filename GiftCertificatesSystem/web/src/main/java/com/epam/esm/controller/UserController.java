package com.epam.esm.controller;

import com.epam.esm.converter.OrderDtoConverter;
import com.epam.esm.converter.UserDtoConverter;
import com.epam.esm.dao.PageRequest;
import com.epam.esm.dto.OrderRepresentationDto;
import com.epam.esm.dto.UserRepresentationDto;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
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
import java.util.stream.Collectors;

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
    private static final String LINK_ORDERS = "orders";
    private static final String LINK_ALL_USER_ORDERS = "allUserOrders";
    private static final String LINK_GIFT_CERTIFICATES = "giftCertificates";

    private final UserService userService;
    private final OrderService orderService;
    private final UserDtoConverter userDtoConverter;
    private final OrderDtoConverter orderDtoConverter;

    @GetMapping("/id/{id}")
    @ApiOperation(value = "get user by id")
    public UserRepresentationDto getUserById(@PathVariable @Min(value = 1) long id) {
        User user = userService.getUserById(id);
        UserRepresentationDto dto = userDtoConverter.convertToRepresentationDto(user);
        Link link = WebMvcLinkBuilder.linkTo(UserController.class)
                .slash(user.getLogin())
                .slash(LINK_ORDERS)
                .withRel(LINK_ORDERS);
        dto.add(link);
        return dto;
    }

    @GetMapping("/login/{login}")
    @ApiOperation(value = "get user by login")
    public UserRepresentationDto getUserByLogin(@PathVariable @Size(min = 4, max = 50) String login) {
        User user = userService.getUserByLogin(login);
        UserRepresentationDto dto = userDtoConverter.convertToRepresentationDto(user);
        Link link = WebMvcLinkBuilder.linkTo(UserController.class)
                .slash(login)
                .slash(LINK_ORDERS)
                .withRel(LINK_ORDERS);
        dto.add(link);
        return dto;
    }

    @GetMapping(value = "/{login}/orders")
    @ApiOperation(value = "get list of user's orders by login")
    public List<OrderRepresentationDto> getUserOrders(
            @PathVariable @Size(min = 4, max = 50) String login,
            @RequestParam(name = "page", required = false, defaultValue = "1") @Min(value = 1) int page,
            @RequestParam(name = "page_size", required = false, defaultValue = "10")
            @Min(value = 8) @Max(value = 20) int pageSize
    ) {
        PageRequest pageRequest = new PageRequest(page, pageSize);
        List<Order> orders = orderService.getUserOrders(login, pageRequest);
        List<OrderRepresentationDto> dtoList = convertOrdersToDtoList(orders);
        addSelfOrderLinksInDtoList(dtoList, login);
        return dtoList;
    }

    @GetMapping(value = "/{login}/orders/{id}")
    @ApiOperation(value = "get user's order by id")
    public OrderRepresentationDto getUserOrderById(@PathVariable @Size(min = 4, max = 50) String login,
                                                   @PathVariable @Min(value = 1) long id) {
        Order order = orderService.getUserOrderById(id);
        OrderRepresentationDto dto = orderDtoConverter.convertToRepresentationDto(order);
        addGiftCertificatesLinksInDto(dto);
        Link link = WebMvcLinkBuilder.linkTo(UserController.class)
                .slash(login)
                .slash(LINK_ORDERS)
                .withRel(LINK_ALL_USER_ORDERS);
        dto.add(link);
        return dto;
    }

    private List<OrderRepresentationDto> convertOrdersToDtoList(List<Order> orders) {
        return orders.stream()
                .map(orderDtoConverter::convertToRepresentationDto)
                .collect(Collectors.toList());
    }

    private void addSelfOrderLinksInDtoList(List<OrderRepresentationDto> dtoList, String login) {
        dtoList.forEach(dto -> {
            Link link = WebMvcLinkBuilder.linkTo(UserController.class)
                    .slash(login)
                    .slash(LINK_ORDERS)
                    .slash(dto.getId())
                    .withSelfRel();
            dto.add(link);
        });
    }

    private void addGiftCertificatesLinksInDto(OrderRepresentationDto dto) {
        dto.getGiftCertificates().stream()
                .distinct()
                .forEach(giftCertificate -> {
                    Link link = WebMvcLinkBuilder.linkTo(GiftCertificateController.class)
                            .slash(giftCertificate.getId())
                            .withRel(LINK_GIFT_CERTIFICATES);
                    dto.add(link);
                });
    }
}
