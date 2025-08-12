package org.globex.retail.store.order.service;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.globex.retail.store.order.model.dto.LineItemDto;
import org.globex.retail.store.order.model.dto.OrderDto;
import org.globex.retail.store.order.model.dto.ShippingAddressDto;
import org.globex.retail.store.order.model.entity.Order;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@QuarkusTest
public class OrderServiceTest {

    @Inject
    OrderService orderService;

    @Test
    void testStoreOrder() {
        OrderDto orderDto = buildOrderDto();
        OrderDto response = orderService.storeOrder(orderDto);
        assertThat(response, notNullValue());
        assertThat(response.getId(), notNullValue());
        assertThat(response.getId() > 0, is(true));
        Order order = Order.findById(response.getId());
        assertThat(order, notNullValue());
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
