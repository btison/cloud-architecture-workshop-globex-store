package org.globex.retail.store.customer.model.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity(name = "Customer")
@Table(name = "customer")
@SequenceGenerator(name="CustomerIdSeq", sequenceName="customer_id_seq", allocationSize = 1)
public class Customer extends PanacheEntityBase {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator="CustomerIdSeq")
    public long id;

    @Column(name = "user_id")
    public String userId;

    @Column(name = "first_name")
    public String firstName;

    @Column(name = "last_name")
    public String lastName;

    @Column(name = "email")
    public String email;

    @Column(name = "phone")
    public String phone;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    public Address address;

    public static Customer findByUserId(String userId) {
        return find("userId", userId).firstResult();
    }

    public static Customer findByEmail(String email) {
        return find("email", email).firstResult();
    }
}
