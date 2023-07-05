package org.globex.retail.store.order.outbox;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.QuarkusTestProfile;
import io.quarkus.test.junit.TestProfile;
import org.globex.retail.store.order.model.entity.OutboxEvent;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@QuarkusTest
@TestProfile(OutboxEmitterWithConfigPropertyOverrideTest.ConfigPropertyChangeValueProfile.class)
public class OutboxEmitterWithConfigPropertyOverrideTest {

    @Inject
    OutboxEmitter outboxEmitter;

    @Test
    void testEmitterWhenDeleteAfterInsertIsTrue() {
        outboxEmitter.deleteAfterInsert = true;
        OutboxEvent event = new OutboxEvent("123", "order", "OrderCreatedEvent", "{\"id\":123}".getBytes(StandardCharsets.UTF_8));
        outboxEmitter.emit(event);
        assertThat(event.id, notNullValue());
        OutboxEvent stored = OutboxEvent.findById(event.id);
        assertThat(stored, nullValue());
    }

    public static class ConfigPropertyChangeValueProfile implements QuarkusTestProfile {
        @Override
        public Map<String, String> getConfigOverrides() {
            return Map.of("outbox.order.remove-after-insert", "true");
        }
    }

}
