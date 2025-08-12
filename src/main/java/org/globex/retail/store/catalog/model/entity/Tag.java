package org.globex.retail.store.catalog.model.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;


@Entity(name = "Tag")
@Table(name = "tag")
@Cacheable
public class Tag extends PanacheEntityBase {

    @Id
    @Column(name = "tag_id")
    public long id;

    @Column(name = "tag")
    public String tag;

}
