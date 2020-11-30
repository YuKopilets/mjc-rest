package com.epam.esm.controller;

import com.epam.esm.converter.OrderDtoConverter;
import com.epam.esm.converter.UserDtoConverter;
import com.epam.esm.dto.representation.OrderRepresentationDto;
import com.epam.esm.dto.representation.UserRepresentationDto;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

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
    @PreAuthorize("hasPermission(#id, 'userId', 'hasPermissionToGetUser') or hasRole('ADMIN')")
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
    @PreAuthorize("#login == authentication.principal.username or hasRole('ADMIN')")
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
    @PreAuthorize("#userLogin == authentication.principal.username or hasRole('ADMIN')")
    public Page<OrderRepresentationDto> getUserOrders(
            @PathVariable @Size(min = 4, max = 50) String login,
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable
            ) {
        Page<Order> orders = orderService.getUserOrders(login, pageable);
        Page<OrderRepresentationDto> dtoPage = orderDtoConverter.convertOrdersToDtoPage(orders);
        addSelfOrderLinksInDtoPage(dtoPage, login);
        return dtoPage;
    }

    @GetMapping(value = "/{login}/orders/{id}")
    @ApiOperation(value = "get user's order by id")
    @PreAuthorize("#userLogin == authentication.principal.username or hasRole('ADMIN')")
    public OrderRepresentationDto getUserOrderById(@PathVariable @Size(min = 4, max = 50) String login,
                                                   @PathVariable @Min(value = 1) long id) {
        Order order = orderService.getUserOrderById(login, id);
        OrderRepresentationDto dto = orderDtoConverter.convertToRepresentationDto(order);
        addGiftCertificatesLinksInDto(dto);
        Link link = WebMvcLinkBuilder.linkTo(UserController.class)
                .slash(login)
                .slash(LINK_ORDERS)
                .withRel(LINK_ALL_USER_ORDERS);
        dto.add(link);
        return dto;
    }

    private void addSelfOrderLinksInDtoPage(Page<OrderRepresentationDto> dtoPage, String login) {
        dtoPage.forEach(dto -> {
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
