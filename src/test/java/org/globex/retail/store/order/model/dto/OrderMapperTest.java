package org.globex.retail.store.order.model.dto;

import org.globex.retail.store.order.model.entity.Order;
import org.globex.retail.store.order.model.entity.OrderLineItem;
import org.globex.retail.store.order.model.entity.ShippingAddress;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class OrderMapperTest {

    @Test
    void testToDto() {
        Order order = buildOrder();
        OrderDto orderDto = OrderMapper.toDto(order);
        assertThat(orderDto, notNullValue());
        assertThat(orderDto.getId(), is(order.id));
        assertThat(orderDto.getCustomer(), is(order.customer));
        assertThat(orderDto.getTimestamp(), is(order.timestamp));
        assertThat(orderDto.getShippingAddress(), notNullValue());
        ShippingAddressDto shippingAddressDto = orderDto.getShippingAddress();
        assertThat(shippingAddressDto.getName(), is(order.shippingAddress.name));
        assertThat(shippingAddressDto.getPhone(), is(order.shippingAddress.phone));
        assertThat(shippingAddressDto.getAddress1(), is(order.shippingAddress.address1));
        assertThat(shippingAddressDto.getAddress2(), is(order.shippingAddress.address2));
        assertThat(shippingAddressDto.getCity(), is(order.shippingAddress.city));
        assertThat(shippingAddressDto.getZip(), is(order.shippingAddress.zipCode));
        assertThat(shippingAddressDto.getState(), is(order.shippingAddress.state));
        assertThat(shippingAddressDto.getCountry(), is(order.shippingAddress.country));
        assertThat(orderDto.getLineItems().size(), equalTo(2));
        LineItemDto lineItemDto = orderDto.getLineItems().getFirst();
        OrderLineItem orderLineItem = order.orderLineItems.stream().filter(oli -> oli.product.equals(lineItemDto.getProduct())).findFirst().orElse(null);
        assertThat(orderLineItem, notNullValue());
        assertThat(lineItemDto.getPrice(), is(orderLineItem.price));
        assertThat(lineItemDto.getQuantity(), is(orderLineItem.quantity));
    }

    @Test
    void testToEntity() {
        OrderDto od = buildOrderDto();
        Order o = OrderMapper.toEntity(od);
        assertThat(o, notNullValue());
        assertThat(o.customer, is(od.getCustomer()));
        assertThat(o.timestamp, is(od.getTimestamp()));
        assertThat(o.shippingAddress, notNullValue());
        assertThat(o.shippingAddress.name, is(od.getShippingAddress().getName()));
        assertThat(o.shippingAddress.phone, is(od.getShippingAddress().getPhone()));
        assertThat(o.shippingAddress.address1, is(od.getShippingAddress().getAddress1()));
        assertThat(o.shippingAddress.address2, is(od.getShippingAddress().getAddress2()));
        assertThat(o.shippingAddress.city, is(od.getShippingAddress().getCity()));
        assertThat(o.shippingAddress.zipCode, is(od.getShippingAddress().getZip()));
        assertThat(o.shippingAddress.state, is(od.getShippingAddress().getState()));
        assertThat(o.shippingAddress.country, is(od.getShippingAddress().getCountry()));
        assertThat(o.orderLineItems, notNullValue());
        assertThat(o.orderLineItems.size(), is(2));
        OrderLineItem orderLineItem = o.orderLineItems.getFirst();
        LineItemDto lineItemDto = od.getLineItems().stream().filter(li -> orderLineItem.product.equals(li.getProduct())).findFirst().orElse(null);
        assertThat(lineItemDto, notNullValue());
        assertThat(orderLineItem.price, is(lineItemDto.getPrice()));
        assertThat(orderLineItem.quantity, is(lineItemDto.getQuantity()));
    }

    private Order buildOrder() {
        Order order = new Order();
        order.id = 1;
        order.customer = "testcustomer";
        order.timestamp = Instant.parse("2023-01-31T09:00:00.0Z");
        OrderLineItem item1 = new OrderLineItem();
        item1.id = 1;
        item1.product = "product1";
        item1.price = new BigDecimal("8.99");
        item1.quantity = 1;
        order.addItem(item1);
        OrderLineItem item2 = new OrderLineItem();
        item2.id = 2;
        item2.product = "product2";
        item2.price = new BigDecimal("18.99");
        item2.quantity = 2;
        order.addItem(item2);
        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.id = 1;
        shippingAddress.name = "John Doe";
        shippingAddress.phone = "111 111 111";
        shippingAddress.address1 = "1 Some Street";
        shippingAddress.address2 = "";
        shippingAddress.city = "New York";
        shippingAddress.zipCode = "12345";
        shippingAddress.state = "NY";
        shippingAddress.country = "USA";
        order.addShippingAddress(shippingAddress);
        return order;
    }

    private OrderDto buildOrderDto() {
        return OrderDto.builder()
                .withCustomer("testcustomer")
                .withTimestamp(Instant.now())
                .withShippingAddress(ShippingAddressDto.builder()
                        .withName("john Doe")
                        .withPhone("111 111 111")
                        .withAddress2("1 Some Street")
                        .withAddress2("")
                        .withCity("New York")
                        .withZip("12345")
                        .withState("NY")
                        .withCountry("USA")
                        .build())
                .withOrderLineItems(List.of(LineItemDto.builder()
                                .withProduct("product1")
                                .withPrice(new BigDecimal("8.99"))
                                .withQuantity(1)
                                .build(),
                        LineItemDto.builder()
                                .withProduct("product2")
                                .withPrice(new BigDecimal("18.99"))
                                .withQuantity(2)
                                .build()))
                .build();
    }

}
