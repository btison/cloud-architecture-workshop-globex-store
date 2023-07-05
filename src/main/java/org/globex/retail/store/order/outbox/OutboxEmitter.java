package org.globex.retail.store.order.outbox;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.globex.retail.store.order.model.entity.OutboxEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class OutboxEmitter {

    @ConfigProperty(name = "outbox.order.remove-after-insert")
    boolean deleteAfterInsert;

    @Transactional
    public void emit(OutboxEvent outboxEvent) {
        outboxEvent.persist();
        if (deleteAfterInsert) {
            outboxEvent.delete();
        }
    }

}
