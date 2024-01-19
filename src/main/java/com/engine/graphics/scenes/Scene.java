package com.engine.graphics.scenes;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.engine.graphics.GameObject;
import com.engine.graphics.components.Component;
import com.engine.graphics.renderer.Camera;
import com.engine.graphics.renderer.Renderer;
import com.engine.graphics.serialization.ComponentDeserializer;
import com.engine.graphics.serialization.GameObjectDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import imgui.ImGui;

public abstract class Scene {
    protected Renderer renderer;
    protected Camera camera;
    private boolean isRunning = false;
    protected List<GameObject> gameObjects = new ArrayList<>();
    // Gameobject that we spectate = has the current focus
    protected GameObject activeGameObject = null;

    protected boolean loadedScene = false;

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

    public void sceneImgui() {
        if (activeGameObject != null) {
            ImGui.begin("Inspector");
            activeGameObject.imgui();
            ImGui.end();
        }

        imgui();
    }

    // To create custom scene integreated Imguis
    public void imgui() {

    }

    public void saveAndExit() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Component.class, new ComponentDeserializer())
                .registerTypeAdapter(GameObject.class, new GameObjectDeserializer())
                .create();
        try {
            FileWriter writer = new FileWriter("Scene.txt");
            writer.write(gson.toJson(this.gameObjects));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Component.class, new ComponentDeserializer())
                .registerTypeAdapter(GameObject.class, new GameObjectDeserializer())
                .create();
        String inFile = "";

        try {
            inFile = new String(Files.readAllBytes(Paths.get("Scene.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!inFile.equals("")) {
            int maxGameObjectId = -1;
            int maxComponentId = -1;
            GameObject[] objects = gson.fromJson(inFile, GameObject[].class);
            for (int i = 0; i < objects.length; i++) {
                addGameObjectToScene(objects[i]);

                for (Component c : objects[i].getAllComponents()) {
                    if (c.getUid() > maxComponentId) {
                        maxComponentId = c.getUid();
                    }
                }
                if (objects[i].getUid() > maxGameObjectId) {
                    maxGameObjectId = objects[i].getUid();
                }
            }

            maxComponentId++;
            maxComponentId++;
            GameObject.init(maxGameObjectId);
            Component.init(maxComponentId);
            this.loadedScene = true;
        }
    }
}
