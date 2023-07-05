package org.globex.retail.store.avro;

import io.apicurio.registry.resolver.data.Metadata;
import io.apicurio.registry.resolver.data.Record;
import io.apicurio.registry.resolver.strategy.ArtifactReference;

public class SimpleRecord<T> implements Record<T> {

    T payload;

    ArtifactReference artifactReference;

    public SimpleRecord(T payload) {
        this.payload = payload;
    }

    public SimpleRecord(T payload, String groupId, String artifactId) {
        this(payload, groupId, artifactId, null);
    }

    public SimpleRecord(T payload, String groupId, String artifactId, String version) {
        this.payload = payload;
        artifactReference = ArtifactReference.builder().groupId(groupId).artifactId(artifactId).version(version).build();
    }


    @Override
    public Metadata metadata() {
        return () -> artifactReference;
    }

    @Override
    public T payload() {
        return payload;
    }
}
