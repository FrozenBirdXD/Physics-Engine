package com.engine.graphics;

import java.util.ArrayList;
import java.util.List;

import com.engine.graphics.components.Component;
import com.engine.graphics.utils.Transform;

import imgui.ImGui;

public class GameObject {
    private static int idCounter = 0;

    private int uid = -1;
    protected String name;
    private List<Component> components;
    private int zIndex;
    private Transform transform;

    public GameObject(String name) {
        init(name, new Transform(), 0);
    }

    public GameObject(String name, Transform transform, int zIndex) {
        init(name, transform, zIndex);
    }

    public static void init(int maxId) {
        idCounter = maxId;
    }

    public void init(String name, Transform transform, int zIndex) {
        this.name = name;
        this.components = new ArrayList<>();
        this.transform = transform;
        this.zIndex = zIndex;

        this.uid = idCounter++;
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
        c.generateId();
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
        float[] imguiPositionX = new float[] { this.transform.getPositionX() };
        if (ImGui.sliderFloat("X Pos: ", imguiPositionX, 0, 1920)) {
            this.transform.setPosition(imguiPositionX[0], this.transform.getPositionY());
        }

        float[] imguiPositionY = new float[] { this.transform.getPositionY() };
        if (ImGui.sliderFloat("Y Pos: ", imguiPositionY, 0, 1080)) {
            this.transform.setPosition(this.transform.getPositionX(), imguiPositionY[0]);
        }
    }

    public List<Component> getAllComponents() {
        return this.components;
    }

    public int getUid() {
        return this.uid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getZIndex() {
        return this.zIndex;
    }

    public void setZIndex(int zIndex) {
        this.zIndex = zIndex;
    }

    public Transform getTransform() {
        return this.transform;
    }

    public void setTransform(Transform transform) {
        this.transform = transform;
    }
}
