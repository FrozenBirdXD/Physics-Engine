package com.engine.engine.serialization;

import java.lang.reflect.Type;

import com.engine.engine.Component;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ComponentDeserializer implements JsonSerializer<Component>, JsonDeserializer<Component> {

    @Override
    public Component deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        throw new UnsupportedOperationException("Unimplemented method 'deserialize'");
    }

    @Override
    public JsonElement serialize(Component src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        // add type to prevent stack overflow
        result.add("type", new JsonPrimitive(src.getClass().getCanonicalName()));
        // serialize the rest
        result.add("properties", context.serialize(src, src.getClass()));
        return result;
    }

}
