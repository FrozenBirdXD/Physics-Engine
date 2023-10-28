package com.engine.engine;

import java.util.ArrayList;
import java.util.List;

import com.engine.engine.renderer.Renderer;

public abstract class Scene {
    protected Renderer renderer;
    protected Camera camera;
    private boolean isRunning = false;
    protected List<GameObject> gameObjects = new ArrayList<>();

    public Scene() {
        this.renderer = new Renderer();
    }

    public abstract void update(float dt);

    public void init() {
    }

    // When scene start for first time
    public void start() {
        // Starts each object and adds them to renderer
        for (GameObject object : gameObjects) {
            object.start();
            renderer.add(object);
        }
        isRunning = true;
    }

    public void addGameObjectToScene(GameObject object) {
        if (!isRunning) {
            gameObjects.add(object);
        } else {
            gameObjects.add(object);
            object.start();
            renderer.add(object);
        }
    }

    public Camera getCamera() {
        return this.camera;
    }
}
