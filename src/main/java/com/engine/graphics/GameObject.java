package com.engine.graphics;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;

import com.engine.graphics.components.Component;
import com.engine.graphics.utils.Transform;

import imgui.ImGui;

public class GameObject {
    private String name;
    private List<Component> components;
    private int zIndex;
    public Transform transform;

    public GameObject(String name) {
        init(name, new Transform(), 0);
    }

    public GameObject(String name, Transform transform, int zIndex) {
        init(name, transform, zIndex);
    }

    public void init(String name, Transform transform, int zIndex) {
        this.name = name;
        this.components = new ArrayList<>();
        this.transform = transform;
        this.zIndex = zIndex;
    }

    public <T extends Component> T getComponent(Class<T> componentClass) {
        for (Component c : components) {
            if (componentClass.isAssignableFrom(c.getClass())) {
                try {
                    return componentClass.cast(c);
                } catch (ClassCastException exception) {
                    exception.printStackTrace();
                    assert false : "Error: Casting component.";
                }
            }
        }

        return null;
    }

    public <T extends Component> void removeComponent(Class<T> componentClass) {
        for (int i = 0; i < components.size(); i++) {
            Component c = components.get(i);
            if (componentClass.isAssignableFrom(c.getClass())) {
                components.remove(i);
                return;
            }
        }
    }

    public void addComponent(Component c) {
        this.components.add(c);
        c.gameObject = this;
    }

    public void update(float dt) {
        for (int i = 0; i < components.size(); i++) {
            components.get(i).update(dt);
        }
    }

    public void start() {
        for (int i = 0; i < components.size(); i++) {
            components.get(i).start();
        }
    }

    public void imgui() {
        for (Component c : components) {
            c.imgui();
        }
        float[] imguiPositionX = new float[] { this.transform.position.x };
        if (ImGui.sliderFloat("X Pos: ", imguiPositionX, 0, 1920)) {
            this.transform.setPosition(new Vector2f(imguiPositionX[0], this.transform.position.y));
        }

        float[] imguiPositionY = new float[] { this.transform.position.y };
        if (ImGui.sliderFloat("Y Pos: ", imguiPositionY, 0, 1080)) {
            this.transform.setPosition(new Vector2f(this.transform.position.x, imguiPositionY[0]));
        }
    }

    public String getName() {
        return this.name;
    }

    public int getZIndex() {
        return this.zIndex;
    }
}
