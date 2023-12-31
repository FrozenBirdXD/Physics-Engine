package com.engine.graphics.listeners;

import static org.lwjgl.glfw.GLFW.*;

import org.joml.Vector4f;

import com.engine.graphics.Window;

public class MouseListener {
    private static MouseListener instance;
    private double scrollX, scrollY;
    private double xPos, yPos, lastY, lastX;
    private boolean mouseButtonPressed[] = new boolean[3];
    private boolean isDragging;

    private MouseListener() {
        this.scrollX = 0.0;
        this.scrollY = 0.0;
        this.xPos = 0.0;
        this.yPos = 0.0;
        this.lastX = 0.0;
        this.lastY = 0.0;
    }

    // singleton
    public static MouseListener get() {
        if (MouseListener.instance == null) {
            MouseListener.instance = new MouseListener();
        }

        return MouseListener.instance;
    }

    public static void mousePosCallback(long window, double xpos, double ypos) {
        get().lastX = get().xPos;
        get().lastY = get().yPos;
        get().xPos = xpos;
        get().yPos = ypos;

        // If the a mousebutton is pressed and the mouse has just moved -> dragging
        get().isDragging = get().mouseButtonPressed[0] || get().mouseButtonPressed[1] || get().mouseButtonPressed[2];
    }

    public static void mouseButtonCallback(long window, int button, int action, int mods) {
        if (action == GLFW_PRESS) {
            if (button < get().mouseButtonPressed.length) {
                get().mouseButtonPressed[button] = true;
            }
        } else if (action == GLFW_RELEASE) {
            if (button < get().mouseButtonPressed.length) {
                get().mouseButtonPressed[button] = false;
                // no longer dragging
                get().isDragging = false;
            }
        }
    }

    public static void mouseScrollCallback(long window, double xOffset, double yOffset) {
        get().scrollX = xOffset;
        get().scrollY = yOffset;
    }

    public static void endFrame() {
        get().scrollX = 0;
        get().scrollY = 0;
        get().lastX = get().xPos;
        get().lastY = get().yPos;
    }

    public static float getX() {
        return (float) get().xPos;
    }

    public static float getY() {
        return (float) get().yPos;
    }

    // world coordinate x
    public static float getOrthoX() {
        float currentX = getX();
        // convert to normalized coordinates (-1 to 1)
        currentX = (currentX / (float) Window.get().getWidth()) * 2 - 1;
        Vector4f temp = new Vector4f(currentX, 0, 0, 1);
        // convert to world coordinates
        temp.mul(Window.get().getScene().getCamera().getInverseProjectionMatrix())
                .mul(Window.get().getScene().getCamera().getInverseViewMatrix());
        currentX = temp.x;
        System.out.println(currentX);
        return currentX;
    }

    // world coordinate Y
    public static float getOrthoY() {
        float currentY = getY();
        // convert to normalized coordinates (-1 to 1)
        currentY = (currentY / (float) Window.get().getHeight()) * 2 - 1;
        Vector4f temp = new Vector4f(0, currentY, 0, 1);
        // convert to world coordinates
        temp.mul(Window.get().getScene().getCamera().getInverseProjectionMatrix())
                .mul(Window.get().getScene().getCamera().getInverseViewMatrix());
        currentY = temp.y;
        System.out.println(currentY);
        return currentY;
    }

    public static float getDx() {
        return (float) (get().lastX - get().xPos);
    }

    public static float getDy() {
        return (float) (get().lastY - get().yPos);
    }

    public static float getScrollX() {
        return (float) get().scrollX;
    }

    public static float getScrollY() {
        return (float) get().scrollY;
    }

    public static boolean isDragging() {
        return get().isDragging;
    }

    public static boolean mouseButtonDown(int button) {
        if (button < get().mouseButtonPressed.length) {
            return get().mouseButtonPressed[button];
        } else {
            return false;
        }
    }

}
