package org.globex.retail.store.order.model.entity;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.transaction.Transactional;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
public class OutboxEventTest {

    final String UUID_STRING = "[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}";

    @Test
    @Transactional
    void testPersistOutboxEvent() {
        OutboxEvent event = new OutboxEvent("123", "order", "OrderCreatedEvent", "{\"id\":123}".getBytes(StandardCharsets.UTF_8));
        event.persist();
        assertThat(event.id, notNullValue());
        assertThat(event.id.toString(), matchesPattern(UUID_STRING));
    }

}
