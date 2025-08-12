package org.globex.retail.store.order.model.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity(name = "ShippingAddress")
@Table(name = "shipping_address")
@SequenceGenerator(name="ShippingAddressIdSeq", sequenceName="shipping_address_id_seq", allocationSize = 1)
public class ShippingAddress extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="ShippingAddressIdSeq")
    @Column(name = "id")
    public long id;

    @Column(name = "name")
    public String name;

    @Column(name = "phone")
    public String phone;

    @Column(name = "address1")
    public String address1;

    @Column(name = "address2")
    public String address2;

    @Column(name = "city")
    public String city;

    @Column(name = "zip")
    public String zipCode;

    @Column(name = "state")
    public String state;

    @Column(name = "country")
    public String country;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    public Order order;

}
