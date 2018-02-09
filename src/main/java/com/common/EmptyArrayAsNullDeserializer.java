package com.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public abstract class EmptyArrayAsNullDeserializer<T> extends JsonDeserializer<T> {

    private final Class<T> clazz;

    protected EmptyArrayAsNullDeserializer(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);
        if (node.isArray() && !node.elements().hasNext()) {
            return null;
        }
        return oc.treeToValue(node, clazz);
    }
}
