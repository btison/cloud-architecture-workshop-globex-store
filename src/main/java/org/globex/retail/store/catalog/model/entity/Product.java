package org.globex.retail.store.catalog.model.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Parameters;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "Product")
@Table(name = "catalog")
@Cacheable
@NamedQueries({
        @NamedQuery(name = "Product.findByItemIdList", query = "from Product where itemId in :itemIdList"),
        @NamedQuery(name = "Product.findByCategoryList", query = "SELECT p from Product p JOIN FETCH p.category c where c.category in :categoryList "),
        @NamedQuery(name = "Product.findByTagList", query = "SELECT p from Product p JOIN FETCH p.tags t where t.tag in :tagList ")
})
public class Product extends PanacheEntityBase {

    @Id
    @Column(name = "item_id")
    public String itemId;

    @Column(name = "name")
    public String name;

    @Column(name = "description")
    public String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category")
    public Category category;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "catalog_tag",
            joinColumns = { @JoinColumn(name = "item_id") },
            inverseJoinColumns = { @JoinColumn(name = "tag_id") })
    public Set<Tag> tags = new HashSet<>();

    @Column(name = "price")
    public Double price;

    public static List<Product> findByItemIdList(List<String> ids) {
        return find("#Product.findByItemIdList", Parameters.with("itemIdList", ids)).list();
    }

    public static List<Product> findProducts(int pageIndex, int pageSize) {
        return findAll().page(Page.of(pageIndex, pageSize)).list();
    }

    public static List<Product> findByCategoryList(List<String> categories) {
        return find("#Product.findByCategoryList", Parameters.with("categoryList", categories)).list();
    }

    public static List<Product> findByTagList(List<String> tags) {
        return find("#Product.findByTagList", Parameters.with("tagList", tags)).list();
    }
}
