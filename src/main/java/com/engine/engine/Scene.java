package com.engine.engine;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene {
    protected Camera camera;
    private boolean isRunning = false;
    protected List<GameObject> gameObjects = new ArrayList<>();

    public Scene() {
    }

    public abstract void update(float dt);

    public void init() {
    }

    public void start() {
        for (GameObject object : gameObjects) {
            object.start();
        }
        isRunning = true;
    }

    public void addGameObjectToScene(GameObject object) {
        if (!isRunning) {
            gameObjects.add(object);
        } else {
            gameObjects.add(object);
            object.start();
        }
    }

    public Camera getCamera() {
        return this.camera;
    }
}
