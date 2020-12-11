package com.epam.esm.converter;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.representation.OrderRepresentationDto;
import com.epam.esm.entity.Order;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

/**
 * The {@code Order dto converter} converts dto to order and conversely.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see ModelMapper
 */
@Component
@RequiredArgsConstructor
public class OrderDtoConverter {
    private final ModelMapper modelMapper;

    /**
     * Convert dto to order after request with <b>POST</b> http method.
     *
     * @param dto the dto
     * @return the order
     */
    public Order convertToOrder(OrderDto dto) {
        return modelMapper.map(dto, Order.class);
    }

    /**
     * Convert order to representation dto before preparing response.
     *
     * @param order the order
     * @return the order representation dto
     */
    public OrderRepresentationDto convertToRepresentationDto(Order order) {
        return modelMapper.map(order, OrderRepresentationDto.class);
    }

    /**
     * Convert orders to dto page for pagination.
     *
     * @param orders the orders
     * @return the page
     */
    public Page<OrderRepresentationDto> convertOrdersToDtoPage(Page<Order> orders) {
        return orders.map(this::convertToRepresentationDto);
    }
}
