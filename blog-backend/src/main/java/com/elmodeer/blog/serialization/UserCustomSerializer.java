package com.elmodeer.blog.serialization;

import com.elmodeer.blog.models.User;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class UserCustomSerializer extends StdSerializer<User> {

    protected UserCustomSerializer() {
        this(null);
    }

    protected UserCustomSerializer(Class<User> t) {
        super(t);
    }

    @Override
    public void serialize(User value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeFieldName("id");
        gen.writeNumber(value.getId());
//        gen.writeFieldName("address");
//        gen.writeString(value.getAddress());
//        gen.writeFieldName("city");
//        gen.writeString(value.getCity());
//        gen.writeFieldName("zipCode");
//        gen.writeNumber(value.getZipCode());
//        gen.writeFieldName("user");
//        gen.writeNumber(value.getUser().getId());
        gen.writeEndObject();
    }
}
