package org.globex.retail.store.order.model.entity;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@QuarkusTest
public class OrderTest {

    @Test
    @Transactional
    void testPersistOrder() {
        Order order = buildOrder();
        order.persist();
        assertThat(order.id, notNullValue());
        assertThat(order.orderLineItems.get(0).id, notNullValue());
        assertThat(order.orderLineItems.get(1).id, notNullValue());
        assertThat(order.shippingAddress.id, notNullValue());
    }

    private Order buildOrder() {
        Order order = new Order();
        order.customer = "testcustomer";
        order.timestamp = Instant.parse("2023-01-31T09:00:00.0Z");
        OrderLineItem item1 = new OrderLineItem();
        item1.product = "product1";
        item1.price = new BigDecimal("8.99");
        item1.quantity = 1;
        order.addItem(item1);
        OrderLineItem item2 = new OrderLineItem();
        item2.product = "product2";
        item2.price = new BigDecimal("18.99");
        item2.quantity = 2;
        order.addItem(item2);
        ShippingAddress shippingAddress = new ShippingAddress();
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
}
