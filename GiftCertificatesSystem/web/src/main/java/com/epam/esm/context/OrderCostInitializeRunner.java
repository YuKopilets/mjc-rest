package com.epam.esm.context;

import com.epam.esm.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderCostInitializeRunner implements CommandLineRunner {
    private final OrderService orderService;

    @Override
    public void run(String... args) {
        //orderService.reviewOrdersCost();
    }
}
