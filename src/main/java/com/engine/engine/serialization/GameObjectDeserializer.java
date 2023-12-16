package com.engine.engine.serialization;

import java.lang.reflect.Type;

import com.engine.engine.GameObject;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class GameObjectDeserializer implements JsonDeserializer<GameObject>, JsonSerializer<GameObject> {
    
    @Override
    public JsonElement serialize(GameObject src, Type typeOfSrc, JsonSerializationContext context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'serialize'");
    }

    @Override
    public GameObject deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deserialize'");
    }
    
}
