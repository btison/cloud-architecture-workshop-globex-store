package org.globex.retail.store.order.service;

import io.smallrye.common.annotation.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.globex.retail.store.order.model.dto.OrderDto;
import org.globex.retail.store.order.model.dto.OrderMapper;
import org.globex.retail.store.order.model.entity.Order;

@ApplicationScoped
public class OrderService {

    @Transactional
    public OrderDto storeOrder(OrderDto orderDto) {
        Order order = OrderMapper.toEntity(orderDto);
        order.persist();
        return OrderMapper.toDto(order);
    }

}
