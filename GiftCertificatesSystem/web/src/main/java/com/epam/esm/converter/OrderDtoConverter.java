package com.epam.esm.converter;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.Order;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderDtoConverter {
    private final ModelMapper modelMapper;

    public Order convertToOrder(OrderDto dto) {
        return modelMapper.map(dto, Order.class);
    }
}
