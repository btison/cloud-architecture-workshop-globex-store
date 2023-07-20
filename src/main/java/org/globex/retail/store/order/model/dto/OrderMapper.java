package org.globex.retail.store.order.model.dto;

import org.globex.retail.store.order.model.entity.Order;
import org.globex.retail.store.order.model.entity.OrderLineItem;
import org.globex.retail.store.order.model.entity.ShippingAddress;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    public static Order toEntity(OrderDto orderDto) {
        if (orderDto == null) {
            return null;
        }
        Order order = new Order();
        order.customer = orderDto.getCustomer();
        order.status = orderDto.getStatus() == null? "created" : orderDto.getStatus();
        order.timestamp = orderDto.getTimestamp() == null? Instant.now() : orderDto.getTimestamp();
        ShippingAddressDto shippingAddress = orderDto.getShippingAddress();
        if (shippingAddress != null) {
            ShippingAddress address = new ShippingAddress();
            address.name = shippingAddress.getName();
            address.phone = shippingAddress.getPhone();
            address.address1 = shippingAddress.getAddress1();
            address.address2 = shippingAddress.getAddress2();
            address.city = shippingAddress.getCity();
            address.zipCode = shippingAddress.getZip();
            address.state = shippingAddress.getState();
            address.country = shippingAddress.getCountry();
            order.addShippingAddress(address);
        }
        if (orderDto.getLineItems() != null) {
            orderDto.getLineItems().forEach(lineItemDto -> {
                OrderLineItem orderLineItem = new OrderLineItem();
                orderLineItem.product = lineItemDto.getProduct();
                orderLineItem.quantity = lineItemDto.getQuantity();
                orderLineItem.price = lineItemDto.getPrice();
                order.addItem(orderLineItem);
            });
        }
        return order;
    }

    public static OrderDto toDto(Order order) {
        if (order == null) {
            return null;
        }
        ShippingAddress shippingAddress = order.shippingAddress;
        List<OrderLineItem> orderLineItems = order.orderLineItems;
        return OrderDto.builder()
                .withId(order.id)
                .withCustomer(order.customer)
                .withStatus(order.status)
                .withTimestamp(order.timestamp)
                .withShippingAddress(shippingAddress == null? null : ShippingAddressDto.builder()
                        .withName(shippingAddress.name)
                        .withPhone(shippingAddress.phone)
                        .withAddress1(shippingAddress.address1)
                        .withAddress2(shippingAddress.address2)
                        .withCity(shippingAddress.city)
                        .withZip(shippingAddress.zipCode)
                        .withState(shippingAddress.state)
                        .withCountry(shippingAddress.country)
                        .build())
                .withOrderLineItems(orderLineItems == null? null : orderLineItems.stream().map(orderLineItem -> LineItemDto.builder()
                        .withProduct(orderLineItem.product)
                        .withPrice(orderLineItem.price)
                        .withQuantity(orderLineItem.quantity)
                        .build()).collect(Collectors.toList()))
                .build();
    }

    public static void updateEntity(Order order, OrderDto orderDto) {
        if (orderDto.getStatus() != null) {
            order.status = orderDto.getStatus();
        }
    }

}
