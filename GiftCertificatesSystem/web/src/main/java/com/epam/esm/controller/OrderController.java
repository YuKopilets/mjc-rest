package com.epam.esm.controller;

import com.epam.esm.converter.OrderDtoConverter;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.Order;
import com.epam.esm.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * The type Order controller.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see RestController
 */
@RestController
@RequestMapping("/orders")
@Api(value = "/orders", tags = "User's order operations")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderDtoConverter dtoConverter;

    @PostMapping
    @ApiOperation(value = "add new order")
    public Order createOrder(@RequestBody @Valid OrderDto dto) {
        Order order = dtoConverter.convertToOrder(dto);
        return orderService.addOrder(order);
    }
}
