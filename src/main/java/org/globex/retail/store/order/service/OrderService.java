package org.globex.retail.store.order.service;

import io.smallrye.common.annotation.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.globex.retail.store.order.model.dto.OrderDto;
import org.globex.retail.store.order.model.dto.OrderMapper;
import org.globex.retail.store.order.model.entity.Order;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class OrderService {

    @Transactional
    public OrderDto storeOrder(OrderDto orderDto) {
        Order order = OrderMapper.toEntity(orderDto);
        order.persist();
        return OrderMapper.toDto(order);
    }

    @Transactional
    public OrderDto getOrderByCustomerIdAndOrderId(String customerId, String orderId) {
        Order order = Order.findByCustomerIdAndOrderId(customerId, orderId);
        if (order == null) {
            return null;
        }
        return OrderMapper.toDto(order);
    }

    @Transactional
    public List<OrderDto> getOrderByCustomerId(String customerId) {
        List<Order> orders = Order.findByCustomerId(customerId);
        return orders.stream().map(OrderMapper::toDto).toList();
    }

}
