package org.globex.retail.store.avro;

import io.apicurio.registry.serde.avro.AvroDatumProvider;
import io.apicurio.registry.serde.avro.AvroSchemaParser;

public class SchemaParser<T> extends AvroSchemaParser<T> {

    @SuppressWarnings({"rawtypes", "unchecked"})
    public SchemaParser(AvroDatumProvider avroDatumProvider) {
        super(avroDatumProvider);
    }

    @Override
    public boolean supportsExtractSchemaFromData() {
        return false;
    }
}
