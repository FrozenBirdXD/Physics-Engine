package com.engine.engine;

import org.joml.Vector2f;
import org.joml.Vector4f;

import com.engine.engine.components.Sprite;
import com.engine.engine.components.SpriteRenderer;
import com.engine.engine.components.Spritesheet;
import com.engine.utils.AssetPool;

public class WorldEditorScene extends Scene {

    private GameObject sandwich;
    private Spritesheet sprites;

    public WorldEditorScene() {

    }

    @Override
    public void init() {
        loadResources();

        // System.out.println("create object");
        this.camera = new Camera(new Vector2f(0, 0));

        sprites = AssetPool.getSpritesheet("src/main/assets/spritesheets/spritesheet_16x16.png");

        sandwich = new GameObject("sandwich", new Transform(new Vector2f(100, 100), new Vector2f(300, 300)));
        sandwich.addComponent(
                new SpriteRenderer(sprites.getSprite(58)));
        // GameObject sandwich1 = new GameObject("sandwich1",
        // new Transform(new Vector2f(400, 100), new Vector2f(300, 300)));
        // sandwich1.addComponent(
        // new SpriteRenderer(sprites.getSprite(85)));
        // GameObject sandwich2 = new GameObject("sandwich2",
        // new Transform(new Vector2f(700, 100), new Vector2f(300, 300)));
        // sandwich2.addComponent(
        // new SpriteRenderer(sprites.getSprite(66)));
        addGameObjectToScene(sandwich);
        // addGameObjectToScene(sandwich1);
        // addGameObjectToScene(sandwich2);
    }

    private void loadResources() {
        AssetPool.getShader("src/main/assets/shaders/default.glsl");

        AssetPool.addSpritesheet("src/main/assets/spritesheets/spritesheet_16x16.png",
                new Spritesheet(AssetPool.getTexture(
                        "src/main/assets/spritesheets/spritesheet_16x16.png"), 16, 16, 352,
                        0));
    }

    private int index = 0;
    private float spriteFlipTime = 0.2f;
    private float spriteFlipLeft = 0.0f;

    @Override
    public void update(float dt) {
        spriteFlipLeft -= dt * 0.5;
        if (spriteFlipLeft <= 0.0) {
            spriteFlipLeft = spriteFlipTime;
            index++;
            if (index > 200) {
                index = 0;
            }
            sandwich.getComponent(SpriteRenderer.class).setSprite(sprites.getSprite(index));
        }
        // System.out.println("" + (1.0f / dt) + " FPS");

        if (sandwich.transform.position.x + 1 > 700) {
            sandwich.transform.position.x -= 700;
        }
        sandwich.transform.position.x += 1;

        for (GameObject object : this.gameObjects) {
            object.update(dt);
        }

        this.renderer.render();

    }
}
