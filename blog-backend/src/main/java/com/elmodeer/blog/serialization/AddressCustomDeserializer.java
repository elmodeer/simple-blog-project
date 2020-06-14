package com.elmodeer.blog.serialization;

import com.elmodeer.blog.models.Address;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class AddressCustomDeserializer extends StdDeserializer<Address> {

    public AddressCustomDeserializer() {
        this(null);
    }

    protected AddressCustomDeserializer(JavaType valueType) {
        super(valueType);
    }

    @Override
    public Address deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);

        Integer id = node.get("id").asText() == "null" ? null : node.get("id").asInt();
        Address address = new Address(node.get("address").asText(),
                           node.get("city").asText(),
                           node.get("zipCode").asInt());
        if (id != null)
            address.setId(new Long(id));

        return address;
    }
}
