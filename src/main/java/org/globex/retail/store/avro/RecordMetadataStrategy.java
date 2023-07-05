package org.globex.retail.store.avro;

import io.apicurio.registry.resolver.ParsedSchema;
import io.apicurio.registry.resolver.data.Record;
import io.apicurio.registry.resolver.strategy.ArtifactReference;
import io.apicurio.registry.resolver.strategy.ArtifactReferenceResolverStrategy;
import org.apache.avro.Schema;

public class RecordMetadataStrategy implements ArtifactReferenceResolverStrategy<Schema, Object> {

    @Override
    public boolean loadSchema() {
        return false;
    }

    @Override
    public ArtifactReference artifactReference(Record<Object> data, ParsedSchema<Schema> parsedSchema) {
        return data.metadata().artifactReference();
    }
}
