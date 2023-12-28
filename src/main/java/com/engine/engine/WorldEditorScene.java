package com.engine.engine;

import org.joml.Vector2f;
import org.joml.Vector4f;

import com.engine.engine.components.RigidBody;
import com.engine.engine.components.Sprite;
import com.engine.engine.components.SpriteRenderer;
import com.engine.engine.components.Spritesheet;
import com.engine.engine.renderer.Texture;
import com.engine.engine.serialization.ComponentDeserializer;
import com.engine.engine.serialization.GameObjectDeserializer;
import com.engine.utils.AssetPool;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import imgui.ImGui;

public class WorldEditorScene extends Scene {

    private Spritesheet sprites;

    public WorldEditorScene() {

    }

    @Override
    public void init() {
        loadResources();
        this.camera = new Camera(new Vector2f(0, 0));

        if (loadedScene) {
            this.activeGameObject = gameObjects.get(0);
            return;
        }
        GameObject sandwich;

        // System.out.println("create object");

        sprites = AssetPool.getSpritesheet("src/main/assets/spritesheets/spritesheet_16x16.png");

        sandwich = new GameObject("sandwich", new Transform(new Vector2f(100, 100), new Vector2f(300, 300)), 2);
        SpriteRenderer spriteRenderer = new SpriteRenderer();
        RigidBody rigidBody = new RigidBody();
        spriteRenderer.setSprite(sprites.getSprite(58));
        sandwich.addComponent(spriteRenderer);
        sandwich.addComponent(rigidBody);
        // GameObject sandwich1 = new GameObject("sandwich1",
        // new Transform(new Vector2f(400, 100), new Vector2f(300, 300)), 1);
        // sandwich1.addComponent(
        // new SpriteRenderer(sprites.getSprite(85)));
        // GameObject ob = new GameObject("lol", new Transform(new Vector2f(500, 500),
        // new Vector2f(200, 200)), 3);
        // ob.addComponent(new SpriteRenderer(new Vector4f(0.4f, 1, 1, 1)));

        // GameObject sandwich2 = new GameObject("sandwich2",
        // new Transform(new Vector2f(700, 100), new Vector2f(300, 300)));
        // sandwich2.addComponent(
        // new SpriteRenderer(new Sprite(new
        // Texture("src/main/assets/images/sandwich.png"))));
        addGameObjectToScene(sandwich);
        // addGameObjectToScene(sandwich1);
        // addGameObjectToScene(ob);
        this.activeGameObject = sandwich;

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
    private float objectSpeed = 1.0f;

    @Override
    public void update(float dt) {
        // // switch between the textures
        // spriteFlipLeft -= dt * 0.5;
        // if (spriteFlipLeft <= 0.0) {
        // spriteFlipLeft = spriteFlipTime;
        // index++;
        // if (index > 200) {
        // index = 0;
        // }
        // sandwich.getComponent(SpriteRenderer.class).setSprite(sprites.getSprite(index));
        // }

        // // Bounce left and right
        // sandwich.transform.position.x += objectSpeed;

        // if (sandwich.transform.position.x > 700) {
        // objectSpeed *= -1;
        // } else if (sandwich.transform.position.x < 0) {
        // objectSpeed *= -1;
        // }

        for (GameObject object : this.gameObjects) {
            object.update(dt);
        }

        this.renderer.render();

    }

    @Override
    public void imgui() {
        ImGui.begin("Test window");
        ImGui.text("test window");
        ImGui.end();
        float[] imguiPositionX = new float[] { this.activeGameObject.transform.position.x };
        if (ImGui.sliderFloat("X Pos: ", imguiPositionX, 0, 1920)) {
            this.activeGameObject.transform
                    .setPosition(new Vector2f(imguiPositionX[0], this.activeGameObject.transform.position.y));
        }

        float[] imguiPositionY = new float[] { this.activeGameObject.transform.position.y };
        if (ImGui.sliderFloat("Y Pos: ", imguiPositionY, 0, 1080)) {
            this.activeGameObject.transform
                    .setPosition(new Vector2f(this.activeGameObject.transform.position.x, imguiPositionY[0]));
        }

    }
}
