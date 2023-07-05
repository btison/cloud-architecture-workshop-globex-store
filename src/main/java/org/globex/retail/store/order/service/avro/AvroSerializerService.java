package org.globex.retail.store.order.service.avro;

import com.fasterxml.jackson.dataformat.avro.AvroMapper;
import com.fasterxml.jackson.dataformat.avro.AvroSchema;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.apicurio.registry.resolver.SchemaLookupResult;
import io.apicurio.registry.resolver.strategy.ArtifactReference;
import org.apache.avro.Schema;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.globex.retail.store.avro.ResolveSchemaService;
import org.globex.retail.store.order.model.outbox.OutboxEvent;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@ApplicationScoped
public class AvroSerializerService {

    public static final byte MAGIC_BYTE = 0x0;

    private ResolveSchemaService<OutboxEvent> resolveSchemaService;

    @PostConstruct
    public void configure() {
        Map<String, Object> config = getConfig();
        resolveSchemaService = new ResolveSchemaService<>();
        resolveSchemaService.configure(config);
    }

    public byte[] serialize(OutboxEvent event, String groupId, String artifactId) {
        return serialize(event, groupId, artifactId, null);
    }

    public byte[] serialize(OutboxEvent event, String groupId, String artifactId, String version) {
        try {
            SchemaLookupResult<Schema> schema = resolveSchemaService.lookupSchema(event, groupId, artifactId, version);
            Long globalId = schema.toArtifactReference().getGlobalId();

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            out.write(MAGIC_BYTE);
            out.write(ByteBuffer.allocate(8).putLong(globalId).array());
            AvroSchema avroSchema = new AvroSchema(schema.getParsedSchema().getParsedSchema());
            byte[] avro = new AvroMapper().registerModule(new JavaTimeModule()).writerFor(event.type()).with(avroSchema).writeValueAsBytes(event);
            out.write(avro);
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, Object> getConfig() {
        Config c = ConfigProvider.getConfig();
        return StreamSupport.stream(c.getPropertyNames().spliterator(), false).filter(s -> s.startsWith("apicurio."))
                .collect(Collectors.toMap(s -> s, s -> c.getValue(s, String.class)));
    }

    public Schema resolveSchemaByArtifactReference(ArtifactReference artifactReference) {
        return resolveSchemaService.resolveSchemaByArtifactReference(artifactReference).getParsedSchema().getParsedSchema();
    }

}
