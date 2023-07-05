package org.globex.retail.store.order.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Instant;
import java.util.List;

public class OrderDto {

    private long id;

    private String customer;

    private String status;

    @JsonFormat(shape=JsonFormat.Shape.STRING)
    private Instant timestamp;

    private ShippingAddressDto shippingAddress;

    private List<LineItemDto> lineItems;

    public Long getId() {
        return id;
    }

    public String getCustomer() {
        return customer;
    }

    public String getStatus() {
        return status;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public ShippingAddressDto getShippingAddress() {
        return shippingAddress;
    }

    public List<LineItemDto> getLineItems() {
        return lineItems;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final OrderDto orderDto;

        public Builder() {
            orderDto = new OrderDto();
        }

        public Builder withId(Long id) {
            orderDto.id = id;
            return this;
        }

        public Builder withCustomer(String customer) {
            orderDto.customer = customer;
            return this;
        }

        public Builder withStatus(String status) {
            orderDto.status = status;
            return this;
        }

        public Builder withTimestamp(Instant ts) {
            orderDto.timestamp = ts;
            return this;
        }

        public Builder withShippingAddress(ShippingAddressDto address) {
            orderDto.shippingAddress = address;
            return this;
        }

        public Builder withOrderLineItems(List<LineItemDto> lineItems) {
            orderDto.lineItems = lineItems;
            return this;
        }

        public OrderDto build() {
            return orderDto;
        }
    }
}
