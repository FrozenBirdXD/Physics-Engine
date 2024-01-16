package com.engine.graphics.utils;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

import com.engine.graphics.GameObject;
import com.engine.graphics.Window;
import com.engine.graphics.listeners.MouseListener;

public class MouseHelper {
    GameObject holdingGameObject = null;

    public void pickupObject(GameObject object) {
        holdingGameObject = object;
        Window.get().getScene().addGameObjectToScene(object);
    }

    private void placeObject() {
        holdingGameObject = null;
    }

    public void update(float dt) {
        if (holdingGameObject != null) {
            holdingGameObject.getTransform().setPosition(MouseListener.getOrthoX() - 16,
                    MouseListener.getOrthoY() - 16);

            if (MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)) {
                placeObject();
            }
        }
    }
}
