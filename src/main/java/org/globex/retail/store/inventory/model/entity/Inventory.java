package org.globex.retail.store.inventory.model.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.panache.common.Parameters;
import jakarta.persistence.*;

import java.util.List;

@Entity(name = "Inventory")
@Table(name = "inventory")
@Cacheable
@NamedQueries({
        @NamedQuery(name = "Inventory.findByItemId", query = "from Inventory where itemId = :itemId")
})
public class Inventory extends PanacheEntityBase {

    @Id
    @Column(name = "id")
    public long id;

    @Column(name = "itemid")
    public String itemId;

    @Column(name = "location")
    public String location;

    @Column(name = "quantity")
    public int quantity;

    @Column(name = "link")
    public String link;

    public static List<Inventory> findByItemId(String itemId) {
        return find("#Inventory.findByItemId", Parameters.with("itemId", itemId)).list();
    }

}
