package com.engine.engine;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.joml.Vector3f;

import imgui.ImGui;

public abstract class Component {
    // transient because GameObject already has a list components
    public transient GameObject gameObject = null;

    public void update(float dt) {

    }

    public void start() {

    }

    public void imgui() {
        try {
            // get fields from subclass that is running this component
            Field[] fields = this.getClass().getDeclaredFields();
            for (Field f : fields) {
                boolean isPrivate = Modifier.isPrivate(f.getModifiers());
                // temporarily set to public
                if (isPrivate) {
                    f.setAccessible(true);
                }

                Class<?> type = f.getType();
                Object value = f.get(this); // get value contained in that field with reflection
                String name = f.getName();

                if (type == int.class) {
                    int castedValue = (int) value;
                    // because imgui expects ints as arrays
                    int[] imIntArray = { castedValue };
                    if (ImGui.dragInt(name + ": ", imIntArray)) {
                        // if dragInt value changes, field is updated
                        f.set(this, imIntArray[0]);
                    }
                } else if (type == float.class) {
                    float castedValue = (float) value;
                    float[] imFloatArray = { castedValue };
                    if (ImGui.dragFloat(name + ": ", imFloatArray)) {
                        f.set(this, imFloatArray[0]);
                    }
                } else if (type == boolean.class) {
                    boolean castedValue = (boolean) value;
                    if (ImGui.checkbox(name + ": ", castedValue)) {
                        f.set(this, !castedValue);
                    }
                } else if (type == Vector3f.class) {
                    Vector3f castedValue = (Vector3f) value;
                    float[] imFloatArray = { castedValue.x, castedValue.y, castedValue.z };
                    if (ImGui.dragFloat3(name + ": ", imFloatArray)) {
                        // don't need to set extra since reference type var
                        castedValue.set(imFloatArray[0], imFloatArray[1], imFloatArray[2]);
                    }
                }

                if (isPrivate) {
                    f.setAccessible(false);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
