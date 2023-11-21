package com.engine.engine;

import org.joml.Vector2f;
import org.joml.Vector4f;

import com.engine.engine.components.Sprite;
import com.engine.engine.components.SpriteRenderer;
import com.engine.engine.components.Spritesheet;
import com.engine.utils.AssetPool;

public class WorldEditorScene extends Scene {
    public WorldEditorScene() {

    }

    @Override
    public void init() {
        loadResources();

        // System.out.println("create object");
        this.camera = new Camera(new Vector2f(0, 0));

        Spritesheet sprites = AssetPool.getSpritesheet("src/main/assets/spritesheets/spritesheet_16x16.png");

        GameObject sandwich = new GameObject("sandwich", new Transform(new Vector2f(100, 100), new Vector2f(300, 300)));
        sandwich.addComponent(
                new SpriteRenderer(sprites.getSprite(58)));
        GameObject sandwich1 = new GameObject("sandwich1",
                new Transform(new Vector2f(400, 100), new Vector2f(300, 300)));
        sandwich1.addComponent(
                new SpriteRenderer(sprites.getSprite(85)));
        GameObject sandwich2 = new GameObject("sandwich2",
                new Transform(new Vector2f(700, 100), new Vector2f(300, 300)));
        sandwich2.addComponent(
                new SpriteRenderer(sprites.getSprite(66)));
        addGameObjectToScene(sandwich);
        addGameObjectToScene(sandwich1);
        addGameObjectToScene(sandwich2);
    }

    private void loadResources() {
        AssetPool.getShader("src/main/assets/shaders/default.glsl");

        AssetPool.addSpritesheet("src/main/assets/spritesheets/spritesheet_16x16.png",
                new Spritesheet(AssetPool.getTexture("src/main/assets/spritesheets/spritesheet_16x16.png"), 16, 16, 352,
                        0));
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
