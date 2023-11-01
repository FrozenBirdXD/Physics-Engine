package com.engine.engine;

import org.joml.Vector2f;
import org.joml.Vector4f;

import com.engine.engine.components.SpriteRenderer;
import com.engine.utils.AssetPool;

public class WorldEditorScene extends Scene {
    public WorldEditorScene() {

    }

    @Override
    public void init() {
        // System.out.println("create object");
        this.camera = new Camera(new Vector2f(0, 0));

        GameObject sandwich = new GameObject("sandwich", new Transform(new Vector2f(100, 100), new Vector2f(512, 512)));
        sandwich.addComponent(new SpriteRenderer(AssetPool.getTexture("src/main/assets/images/sandwich.png")));
        addGameObjectToScene(sandwich);

        loadResources();
    }

    private void loadResources() {
        AssetPool.getShader("src/main/assets/shaders/default.glsl");
    }

    @Override
    public void update(float dt) {
        // System.out.println("" + (1.0f / dt) + " FPS");

        for (GameObject object : this.gameObjects) {
            object.update(dt);
        }

        this.renderer.render();
    }
}
