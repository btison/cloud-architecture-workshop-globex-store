package org.globex.retail.store.order.model.outbox;

import java.time.Instant;

public abstract class OutboxEvent<T> {

    String id;

    String messageType;

    String invokingService;

    Instant timestamp;

    T payload;

    public abstract Class<?> type();

}
