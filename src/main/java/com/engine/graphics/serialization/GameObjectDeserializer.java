package com.engine.graphics.serialization;

import java.lang.reflect.Type;

import com.engine.graphics.GameObject;
import com.engine.graphics.components.Component;
import com.engine.graphics.utils.Transform;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class GameObjectDeserializer implements JsonDeserializer<GameObject> {

    @Override
    public GameObject deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String name = jsonObject.get("name").getAsString();
        JsonArray components = jsonObject.getAsJsonArray("components");
        int zIndex = jsonObject.get("zIndex").getAsInt();
        Transform transform = context.deserialize(jsonObject.get("transform"), Transform.class);

        GameObject gameObject = new GameObject(name, transform, zIndex);

        for (JsonElement element : components) {
            // component is deserialized using the custom deserializer
            Component component = context.deserialize(element, Component.class);
            gameObject.addComponent(component);
        }
        return gameObject;
    }
}
