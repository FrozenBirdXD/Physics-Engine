package com.engine.engine;

import java.awt.event.KeyEvent;

public class WorldEditorScene extends Scene {
    private boolean changingScene = false;
    private float timeToChangeScene = 2.0f;

    public WorldEditorScene() {
        System.out.println("World Editor Scene");
        Window.get().r = 1;
        Window.get().g = 1;
        Window.get().b = 1;
    }

    @Override
    public void update(float dt) {
        // System.out.println("" + (1.0f / dt) + " FPS");

        if (!changingScene && KeyListener.isKeyPressed(KeyEvent.VK_SPACE)) {
            changingScene = true;
        }

        if (changingScene && timeToChangeScene > 0) {
            timeToChangeScene -= dt;
            Window.get().r -= dt * 5.0f;
            Window.get().g -= dt * 5.0f;
            Window.get().b -= dt * 5.0f;
        } else if (changingScene) {
            Window.changeScene(1);
        }
    }
}
