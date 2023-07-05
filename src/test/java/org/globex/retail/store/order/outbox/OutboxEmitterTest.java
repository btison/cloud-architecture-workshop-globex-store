package org.globex.retail.store.order.outbox;

import io.quarkus.test.junit.QuarkusTest;
import org.globex.retail.store.order.model.entity.OutboxEvent;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@QuarkusTest
public class OutboxEmitterTest {

    @Inject
    OutboxEmitter outboxEmitter;

    @Test
    void testEmitter() {
        OutboxEvent event = new OutboxEvent("123", "order", "OrderCreatedEvent", "{\"id\":123}".getBytes(StandardCharsets.UTF_8));
        outboxEmitter.emit(event);
        assertThat(event.id, notNullValue());
        OutboxEvent stored = OutboxEvent.findById(event.id);
        assertThat(stored, notNullValue());
        assertThat(stored.aggregateId, equalTo(event.aggregateId));
        assertThat(stored.aggregateType, equalTo(event.aggregateType));
        assertThat(stored.eventType, equalTo(event.eventType));
        assertThat(stored.payload, equalTo(event.payload));
    }
}
