package org.globex.retail.store.order.model.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity(name = "OrderLineItem")
@Table(name = "line_item")
@SequenceGenerator(name="LineItemIdSeq", sequenceName="line_item_id_seq", allocationSize = 1)
public class OrderLineItem extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="LineItemIdSeq")
    @Column(name = "id")
    public long id;

    @Column(name = "product_code")
    public String product;

    @Column(name = "quantity")
    public int quantity;

    @Column(name = "price", precision = 8, scale = 2, columnDefinition = "NUMERIC (8, 2)")
    public BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    public Order order;
}
