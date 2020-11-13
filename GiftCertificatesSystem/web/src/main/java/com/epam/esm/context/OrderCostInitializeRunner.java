package com.epam.esm.context;

import com.epam.esm.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * The {@code Order cost initialize runner} scans orders in database
 * and recalculates them cost when it's necessary.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see CommandLineRunner
 */
@Component
@RequiredArgsConstructor
public class OrderCostInitializeRunner implements CommandLineRunner {
    private final OrderService orderService;

    @Override
    public void run(String... args) {
        orderService.reviewOrdersCost();
    }
}
