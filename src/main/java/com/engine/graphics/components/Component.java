package com.engine.graphics.components;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.joml.Vector3f;
import org.joml.Vector4f;

import com.engine.graphics.GameObject;

import imgui.ImGui;

public abstract class Component {
    private static int idCounter = 0; // "global" to this type

    private int uid = -1; // unique to each object
    // transient because GameObject already has a list components
    public transient GameObject gameObject = null;

    public void generateId() {
        if (uid == -1) {
            uid = idCounter++;
        }
    }

    public static void init(int maxId) {
        idCounter = maxId;
    }

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
                boolean isTransient = Modifier.isTransient(f.getModifiers());

                if (isTransient) {
                    continue;
                }
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
                } else if (type == Vector4f.class) {
                    Vector4f castedValue = (Vector4f) value;
                    float[] imFloatArray = { castedValue.x, castedValue.y, castedValue.z, castedValue.w };
                    if (ImGui.dragFloat4(name + ": ", imFloatArray)) {
                        // don't need to set extra since reference type var
                        castedValue.set(imFloatArray[0], imFloatArray[1], imFloatArray[2], imFloatArray[3]);
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

    public int getUid() {
        return this.uid;
    }
}
