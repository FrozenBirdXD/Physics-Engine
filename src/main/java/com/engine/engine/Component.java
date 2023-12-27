package com.engine.engine;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

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
