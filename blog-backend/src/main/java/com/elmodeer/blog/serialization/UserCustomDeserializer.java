package com.elmodeer.blog.serialization;

import com.elmodeer.blog.models.User;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class UserCustomDeserializer extends StdDeserializer<User> {

    public UserCustomDeserializer() {
        this(null);
    }

    protected UserCustomDeserializer(JavaType valueType) {
        super(valueType);
    }

    @Override
    public User deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);
        Integer id = node.get("id").asText() == "null" ? null : node.get("id").asInt();
        if (id == null) throw new NullPointerException("Article does not have a valid user id");

        User user = new User(node.get("username").asText(),
                             node.get("email").asText(),
                             node.get("firstName").asText(),
                             node.get("lastName").asText(),
                             node.get("aboutMe").asText(), null);

        user.setId(new Long(id));
        return user;
    }
}
