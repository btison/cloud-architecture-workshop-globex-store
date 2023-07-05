package org.globex.retail.store.order.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.smallrye.common.annotation.Blocking;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.globex.retail.store.order.model.dto.OrderDto;
import org.globex.retail.store.order.model.dto.OrderMapper;
import org.globex.retail.store.order.model.entity.Order;
import org.globex.retail.store.order.model.entity.OutboxEvent;
import org.globex.retail.store.order.model.outbox.OrderCreatedEvent;
import org.globex.retail.store.order.outbox.OutboxEmitter;
import org.globex.retail.store.order.service.avro.AvroSerializerService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.UUID;

@ApplicationScoped
public class OrderService {

    @ConfigProperty(name = "outbox.order.type")
    String aggregateType;

    @ConfigProperty(name = "outbox.order.event.created")
    String orderCreatedEventType;

    @ConfigProperty(name = "outbox.order.invoking-service")
    String invokingService;

    @Inject
    OutboxEmitter outboxEmitter;

    @Inject
    AvroSerializerService serializerService;

    @Transactional()
    @Blocking
    public OrderDto storeOrder(OrderDto orderDto) {
        Order order = OrderMapper.toEntity(orderDto);
        order.persist();
        OrderDto result = OrderMapper.toDto(order);
        OrderCreatedEvent orderCreatedEvent = OrderCreatedEvent.builder().withId(UUID.randomUUID().toString())
                .withMessageType(orderCreatedEventType)
                .withInvokingService(invokingService)
                .withTimestamp(result.getTimestamp())
                .withPayload(result).build();
        OutboxEvent outboxEvent = new OutboxEvent(Long.toString(order.id), aggregateType, orderCreatedEventType,
                serializerService.serialize(orderCreatedEvent, invokingService, orderCreatedEventType));
        outboxEmitter.emit(outboxEvent);
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
