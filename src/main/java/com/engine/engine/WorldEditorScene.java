package com.engine.engine;

import org.joml.Vector2f;
import org.joml.Vector4f;

import com.engine.engine.components.SpriteRenderer;

public class WorldEditorScene extends Scene {
    private boolean firstTime;

    public WorldEditorScene() {

    }

    @Override
    public void init() {
        // System.out.println("create object");
        this.camera = new Camera(new Vector2f(0, 0));

        int xOffset = 10;
        int yOffset = 10;

        float totalWidth = (float) (600 - xOffset * 2);
        float totalHeigth = (float) (300 - yOffset * 2);

        float sizeX = totalWidth / 100.0f;
        float sizeY = totalHeigth / 100.0f;

        for (int x = 0; x < 100; x++) {
            for (int y = 0; y < 100; y++) {
                float xPos = xOffset + (x * sizeX);
                float yPos = yOffset + (y * sizeY);

                GameObject object = new GameObject("Object" + x + "" + y,
                        new Transform(new Vector2f(xPos, yPos), new Vector2f(sizeX, sizeY)));
                object.addComponent(new SpriteRenderer(new Vector4f(xPos / totalWidth, yPos / totalHeigth, 1, 1)));
                this.addGameObjectToScene(object);
            }
        }
    }

    @Override
    public void update(float dt) {
        System.out.println("" + (1.0f / dt) + " FPS");

        // if (!firstTime) {
        // System.out.println("create object");
        // GameObject object = new GameObject("game test");
        // firstTime = true;
        // }

        for (GameObject object : this.gameObjects) {
            object.update(dt);
        }

        this.renderer.render();
    }
}
