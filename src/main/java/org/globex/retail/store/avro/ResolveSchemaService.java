package org.globex.retail.store.avro;

import io.apicurio.registry.resolver.DefaultSchemaResolver;
import io.apicurio.registry.resolver.SchemaLookupResult;
import io.apicurio.registry.resolver.SchemaResolver;
import io.apicurio.registry.resolver.strategy.ArtifactReference;
import io.apicurio.registry.serde.avro.AvroDatumProvider;
import io.apicurio.registry.serde.avro.DefaultAvroDatumProvider;
import org.apache.avro.Schema;

import java.util.Map;

public class ResolveSchemaService<T> {

    SchemaResolver<Schema, T> schemaResolver;

    AvroDatumProvider<T> avroDatumProvider;

    public ResolveSchemaService() {
        schemaResolver = new DefaultSchemaResolver<>();
    }

    public void configure(Map<String, Object> config) {
        avroDatumProvider = new DefaultAvroDatumProvider<>(false);
        SchemaParser<T> parser = new SchemaParser<>(avroDatumProvider);
        schemaResolver.configure(config, parser);
    }

    public SchemaLookupResult<Schema> lookupSchema(T data, String groupId, String artifactId) {
        return lookupSchema(data, groupId, artifactId, null);
    }

    public SchemaLookupResult<Schema> lookupSchema(T data, String groupId, String artifactId, String version) {
        SimpleRecord<T> record = new SimpleRecord<>(data, groupId, artifactId, version);
        return schemaResolver.resolveSchema(record);
    }

    public SchemaLookupResult<Schema> resolveSchemaByArtifactReference(ArtifactReference artifactReference) {
        return schemaResolver.resolveSchemaByArtifactReference(artifactReference);
    }

    public AvroDatumProvider<T> avroDatumProvider() {
        return avroDatumProvider;
    }

}
