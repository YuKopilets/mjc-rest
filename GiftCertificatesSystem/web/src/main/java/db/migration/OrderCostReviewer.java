package db.migration;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class OrderCostReviewer {
    private final OrderRepository orderRepository;

    public void reviewOrdersCost() {
        long countOfOrders = orderRepository.count();
        int pageSize = 100;
        IntStream.range(0, countPages(countOfOrders, pageSize))
                .mapToObj(i -> PageRequest.of(i, pageSize))
                .map(pageRequest -> recalculateOrdersCost(orderRepository.findAll(pageRequest).getContent()))
                .forEach(this::updateOrdersCost);
    }

    private int countPages(long countOfOrders, int pageSize) {
        int pages = (int) countOfOrders / pageSize;
        return countOfOrders % pageSize == 0 ? pages : pages + 1;
    }

    private List<Order> recalculateOrdersCost(List<Order> orders) {
        orders.stream()
                .filter(order -> order.getCost().doubleValue() == 0.0)
                .forEach(order -> order.setCost(calculateOrderCost(order)));
        return orders;
    }

    private BigDecimal calculateOrderCost(Order order) {
        return order.getGiftCertificates().stream()
                .map(GiftCertificate::getPrice)
                .reduce(BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_UP), BigDecimal::add);
    }

    private void updateOrdersCost(List<Order> orders) {
        orders.forEach(orderRepository::save);
    }
}
