package com.engine.graphics.utils;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

import com.engine.graphics.GameObject;
import com.engine.graphics.Window;
import com.engine.graphics.components.Component;
import com.engine.graphics.listeners.MouseListener;

public class MouseHelper extends Component {
    GameObject holdingGameObject = null;

    public void pickupObject(GameObject object) {
        holdingGameObject = object;
        Window.get().getScene().addGameObjectToScene(object);
    }

    private void placeObject() {
        holdingGameObject = null;
    }

    @Override
    public void update(float dt) {
        if (holdingGameObject != null) {
            holdingGameObject.getTransform().setPosition(MouseListener.getOrthoX(), MouseListener.getOrthoY());
            holdingGameObject.getTransform().setPosition(
                    (int) holdingGameObject.getTransform().getPositionX() / Settings.GRID_WIDTH * Settings.GRID_WIDTH,
                    (int) holdingGameObject.getTransform().getPositionY() / Settings.GRID_HEIGHT * Settings.GRID_HEIGHT);

            if (MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)) {
                placeObject();
            }
        }
    }
}
