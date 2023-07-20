package org.globex.retail.store.order.service;

import io.smallrye.common.annotation.Blocking;
import org.globex.retail.store.order.model.dto.OrderDto;
import org.globex.retail.store.order.model.dto.OrderMapper;
import org.globex.retail.store.order.model.entity.Order;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class OrderService {

    @Transactional
    @Blocking
    public OrderDto storeOrder(OrderDto orderDto) {
        Order order = OrderMapper.toEntity(orderDto);
        order.persist();
        return OrderMapper.toDto(order);
    }

    @Transactional
    public OrderDto updateOrder(OrderDto orderDto) {
        Order order = Order.findById(orderDto.getId());
        if (order == null) {
            return null;
        }
        OrderMapper.updateEntity(order, orderDto);
        return OrderMapper.toDto(order);
    }

}
