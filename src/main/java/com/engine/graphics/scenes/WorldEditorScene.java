package com.engine.graphics.scenes;

import org.joml.Vector2f;
import org.joml.Vector4f;

import com.engine.graphics.Camera;
import com.engine.graphics.GameObject;
import com.engine.graphics.components.RigidBody;
import com.engine.graphics.components.Sprite;
import com.engine.graphics.components.SpriteRenderer;
import com.engine.graphics.components.Spritesheet;
import com.engine.graphics.listeners.MouseListener;
import com.engine.graphics.renderer.Texture;
import com.engine.graphics.serialization.ComponentDeserializer;
import com.engine.graphics.serialization.GameObjectDeserializer;
import com.engine.graphics.utils.AssetPool;
import com.engine.graphics.utils.MouseHelper;
import com.engine.graphics.utils.Prefabs;
import com.engine.graphics.utils.Transform;

import imgui.ImGui;
import imgui.ImVec2;

public class WorldEditorScene extends Scene {

    private Spritesheet sprites;
    private int textureIndex;
    private MouseHelper mouseHelper = new MouseHelper();

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

        sandwich = new GameObject("sandwich", new Transform(new Vector2f(0, 0), new Vector2f(300, 300)), 2);
        SpriteRenderer spriteRenderer = new SpriteRenderer();
        RigidBody rigidBody = new RigidBody();
        spriteRenderer.setSprite(sprites.getSprite(textureIndex));
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

        sprites = AssetPool.getSpritesheet("src/main/assets/spritesheets/spritesheet_16x16.png");
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
        // this.activeGameObject.getComponent(SpriteRenderer.class).setSprite(sprites.getSprite(index));
        // }

        // // Bounce left and right
        // this.activeGameObject.transform.position.x += objectSpeed;

        // if (this.activeGameObject.transform.position.x > 700) {
        // objectSpeed *= -1;
        // } else if (this.activeGameObject.transform.position.x < 0) {
        // objectSpeed *= -1;
        // }
        mouseHelper.update(dt);
        for (GameObject object : this.gameObjects) {
            object.update(dt);
        }

        this.renderer.render();

    }

    @Override
    public void imgui() {
        ImGui.begin("Test Icons");

        ImVec2 windowPos = new ImVec2();
        ImGui.getWindowPos(windowPos);
        ImVec2 windowSize = new ImVec2();
        ImGui.getWindowSize(windowSize);
        ImVec2 itemSpacing = new ImVec2();
        ImGui.getStyle().getItemSpacing(itemSpacing);

        float rightCornerOfWindow = windowPos.x + windowSize.x;
        for (int i = 0; i < sprites.getNumSprites(); i++) {
            Sprite sprite = sprites.getSprite(i);
            // goal width and height
            float spriteWidth = sprite.getWidth() * 4;
            float spriteHeight = sprite.getHeight() * 4;
            int id = sprite.getTextureId();

            Vector2f[] textureCoords = sprite.getTexCoords();
            ImGui.pushID(i);

            // ImGui uses Id system to determine if something is clicked or not
            if (ImGui.imageButton(id, spriteWidth, spriteHeight, textureCoords[0].x, textureCoords[0].y,
                    textureCoords[2].x, textureCoords[2].y)) {
                // this.activeGameObject.getComponent(SpriteRenderer.class).setSprite(sprite);
                GameObject object = Prefabs.createSpriteObjectFromTexture(sprite, spriteWidth, spriteHeight);
                mouseHelper.pickupObject(object);
            }

            ImGui.popID();

            ImVec2 lastButtonPosition = new ImVec2();
            ImGui.getItemRectMax(lastButtonPosition);
            float lastButtonRightCorner = lastButtonPosition.x;
            float nextButtonRightCorner = lastButtonRightCorner + itemSpacing.x + spriteWidth;
            if (i + 1 < sprites.getNumSprites() && nextButtonRightCorner < rightCornerOfWindow) {
                ImGui.sameLine();
            }
        }

        ImGui.end();
    }
}
