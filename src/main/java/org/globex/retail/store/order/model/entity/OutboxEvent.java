package org.globex.retail.store.order.model.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders_outbox")
public class OutboxEvent extends PanacheEntityBase {

    @Id
    @GeneratedValue
    public UUID id;

    @Column(name = "aggregateid")
    public String aggregateId;

    @Column(name = "aggregatetype")
    public String aggregateType;

    @Column(name = "type")
    public String eventType;

    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    @Column(name = "payload")
    public byte[] payload;

    OutboxEvent() {
    }

    public OutboxEvent(String aggregateId, String aggregateType, String eventType, byte[] payload) {
        this.aggregateId = aggregateId;
        this.aggregateType = aggregateType;
        this.eventType = eventType;
        this.payload = payload;
    }

    public static List<OutboxEvent> findByAggregateId(String aggregateId) {
        return OutboxEvent.find("aggregateId = ?1", aggregateId).list();
    }
}
