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
import com.engine.graphics.renderer.DebugDraw;
import com.engine.graphics.renderer.Texture;
import com.engine.graphics.serialization.ComponentDeserializer;
import com.engine.graphics.serialization.GameObjectDeserializer;
import com.engine.graphics.shapes.Square;
import com.engine.graphics.utils.AssetPool;
import com.engine.graphics.utils.ColorConversion;
import com.engine.graphics.utils.Colors;
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
        DebugDraw.addLine2D(new Vector2f(0, 0), new Vector2f(800, 800), ColorConversion.colorToRGB(Colors.Red), 320);

        if (loadedScene) {
            this.activeGameObject = gameObjects.get(0);
            return;
        }
        GameObject sandwich;
        GameObject sandwich1;

        sandwich = new GameObject("sandwich", new Transform(new Vector2f(0, 0), new Vector2f(300, 300)), 2);
        SpriteRenderer spriteRenderer = new SpriteRenderer();
        spriteRenderer.setColor(new Vector4f(222, 222, 22, 1));
        RigidBody rigidBody = new RigidBody();
        // spriteRenderer.setSprite(sprites.getSprite(textureIndex));

        sandwich1 = new GameObject("sandwich1", new Transform(new Vector2f(200, 200), new Vector2f(300, 300)), 2);
        SpriteRenderer spriteRenderer1 = new SpriteRenderer();
        RigidBody rigidBody1 = new RigidBody();
        spriteRenderer1.setSprite(sprites.getSprite(textureIndex + 1));

        sandwich.addComponent(spriteRenderer);
        sandwich.addComponent(rigidBody);

        sandwich1.addComponent(spriteRenderer1);
        sandwich1.addComponent(rigidBody1);

        addGameObjectToScene(sandwich);
        addGameObjectToScene(sandwich1);

        Square square = new Square("square", new Vector4f(244f, 11f, 2f, 255f));
        square.setTransform(new Transform(new Vector2f(600, 300), new Vector2f(100, 100)));
        addGameObjectToScene(square);

        this.activeGameObject = square;
    }

    private void loadResources() {
        AssetPool.getShader("src/main/assets/shaders/default.glsl");

        AssetPool.addSpritesheet("src/main/assets/spritesheets/spritesheet_16x16.png",
                new Spritesheet(AssetPool.getTexture(
                        "src/main/assets/spritesheets/spritesheet_16x16.png"), 16, 16, 352,
                        0));

        sprites = AssetPool.getSpritesheet("src/main/assets/spritesheets/spritesheet_16x16.png");
    }

    @Override
    public void update(float dt) {
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
            if (ImGui.imageButton(id, spriteWidth, spriteHeight, textureCoords[2].x, textureCoords[0].y,
                    textureCoords[0].x, textureCoords[2].y)) {
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
