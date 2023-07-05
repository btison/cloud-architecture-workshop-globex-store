package org.globex.retail.store.order.model.outbox;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.globex.retail.store.order.model.dto.OrderDto;

import java.time.Instant;

public class OrderCreatedEvent extends OutboxEvent<OrderDto> {

    public String getId() {
        return id;
    }

    public String getMessageType() {
        return messageType;
    }

    public String getInvokingService() {
        return invokingService;
    }

    @JsonFormat(shape=JsonFormat.Shape.STRING)
    public Instant getTimestamp() {
        return timestamp;
    }

    public OrderDto getPayload() {
        return payload;
    }

    @Override
    public Class<?> type() {
        return getClass();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final OrderCreatedEvent orderCreatedEvent;

        public Builder() {
            this.orderCreatedEvent = new OrderCreatedEvent();
        }

        public Builder withId(String id) {
            this.orderCreatedEvent.id = id;
            return this;
        }

        public Builder withTimestamp(Instant timestamp) {
            this.orderCreatedEvent.timestamp = timestamp;
            return this;
        }

        public Builder withMessageType(String messageType) {
            this.orderCreatedEvent.messageType = messageType;
            return this;
        }

        public Builder withInvokingService(String invokingService) {
            this.orderCreatedEvent.invokingService = invokingService;
            return this;
        }

        public Builder withPayload(OrderDto order) {
            this.orderCreatedEvent.payload = order;
            return this;
        }

        public OrderCreatedEvent build() {
            return orderCreatedEvent;
        }
    }
}
