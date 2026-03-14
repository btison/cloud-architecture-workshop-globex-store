package org.globex.retail.store.order.model.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.panache.common.Parameters;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Order")
@Table(name = "orders")
@SequenceGenerator(name="OrderIdSeq", sequenceName="order_id_seq", allocationSize = 1)
@NamedQueries({
        @NamedQuery(name = "Order.findByCustomerIdAndOrderId", query = "from Order where customer = :customerId and id = :orderId "),
        @NamedQuery(name = "Order.findByCustomerId", query = "from Order where customer = :customerId")
})
public class Order extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="OrderIdSeq")
    @Column(name = "id")
    public long id;

    @Column(name = "customer_id")
    public String customer;

    @Column(name = "order_ts")
    public Instant timestamp;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, optional = false)
    public ShippingAddress shippingAddress;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<OrderLineItem> orderLineItems = new ArrayList<>();

    public void addItem(OrderLineItem item) {
        orderLineItems.add(item);
        item.order = this;
    }

    public void addShippingAddress(ShippingAddress shippingAddress) {
        if (shippingAddress == null) {
            if (this.shippingAddress != null) {
                this.shippingAddress.order = null;
            }
        } else {
            shippingAddress.order = this;
        }
        this.shippingAddress = shippingAddress;
    }

    public void removeItem(OrderLineItem item) {
        orderLineItems.remove(item);
        item.order = null;
    }

    public static Order findByCustomerIdAndOrderId(String customerId, String orderId) {
        long orderIdLong;
        try {
            orderIdLong = Long.parseLong(orderId);
        } catch (NumberFormatException e) {
            return null;
        }
        return find("#Order.findByCustomerIdAndOrderId", Parameters.with("customerId", customerId).and("orderId", orderIdLong)).firstResult();
    }

    public static List<Order> findByCustomerId(String customerId) {
        return find("#Order.findByCustomerId", Parameters.with("customerId", customerId)).list();
    }
}
