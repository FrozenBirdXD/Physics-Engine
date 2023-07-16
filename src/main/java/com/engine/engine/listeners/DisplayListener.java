package com.engine.engine.listeners;

import static org.lwjgl.opengl.GL33.*;

public class DisplayListener {
    private static DisplayListener instance;
    
    public DisplayListener() {
    }

    // singleton
    public static DisplayListener get() {
        if (DisplayListener.instance == null) {
            DisplayListener.instance = new DisplayListener();
        }

        return DisplayListener.instance;
    }

    public static void framebufferSizeCallback(long window, int width, int height) {
        glViewport(0, 0, width, height);
    }
}
