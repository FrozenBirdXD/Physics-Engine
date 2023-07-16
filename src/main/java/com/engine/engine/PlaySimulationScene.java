package com.engine.engine;

import java.awt.event.KeyEvent;

import com.engine.engine.listeners.KeyListener;

public class PlaySimulationScene extends Scene {
    private boolean changingScene = false;
    private float timeToChangeScene = 2.0f;

    public PlaySimulationScene() {
        System.out.println("Play Simulation Scene");
        Window.get().r = 1;
        Window.get().g = 1;
        Window.get().b = 1;
    }

    @Override
    public void update(float dt) {
        if (!changingScene && KeyListener.isKeyPressed(KeyEvent.VK_SPACE)) {
            changingScene = true;
        }

        if (changingScene && timeToChangeScene > 0) {
            timeToChangeScene -= dt;
            Window.get().r -= dt * 5.0f;
            Window.get().g -= dt * 5.0f;
            Window.get().b -= dt * 5.0f;
        } else if (changingScene) {
            Window.changeScene(0);
        }
    }
}
