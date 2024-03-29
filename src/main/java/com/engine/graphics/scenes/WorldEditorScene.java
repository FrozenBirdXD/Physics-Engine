package com.engine.graphics.scenes;

import javax.naming.directory.InvalidAttributeValueException;

import org.joml.Vector2f;
import org.joml.Vector4f;

import com.engine.graphics.GameObject;
import com.engine.graphics.components.GridLines;
import com.engine.graphics.components.MouseHelper;
import com.engine.graphics.components.RigidBody;
import com.engine.graphics.components.Sprite;
import com.engine.graphics.components.SpriteRenderer;
import com.engine.graphics.components.Spritesheet;
import com.engine.graphics.listeners.MouseListener;
import com.engine.graphics.renderer.Camera;
import com.engine.graphics.renderer.DebugDraw;
import com.engine.graphics.renderer.Texture;
import com.engine.graphics.serialization.ComponentDeserializer;
import com.engine.graphics.serialization.GameObjectDeserializer;
import com.engine.graphics.shapes.Circle;
import com.engine.graphics.shapes.Ellipse;
import com.engine.graphics.shapes.Line;
import com.engine.graphics.shapes.Rectangle;
import com.engine.graphics.shapes.Square;
import com.engine.graphics.utils.AssetPool;
import com.engine.graphics.utils.ColorConversion;
import com.engine.graphics.utils.Colors;
import com.engine.graphics.utils.Prefabs;
import com.engine.graphics.utils.Transform;

import imgui.ImGui;
import imgui.ImVec2;

public class WorldEditorScene extends Scene {

    private Spritesheet sprites;
    private int textureIndex;
    private GameObject testing = new GameObject("helper for Scene", new Transform(new Vector2f()), 0);

    public WorldEditorScene() {

    }

    @Override
    public void init() {
        testing.addComponent(new MouseHelper());
        testing.addComponent(new GridLines());

        loadResources();
        this.camera = new Camera(new Vector2f(0, 0));

        if (loadedScene) {
            if (gameObjects.size() > 0) {
                this.activeGameObject = gameObjects.get(0);
            }
            return;
        }
        GameObject sandwich;
        GameObject sandwich1;

        Square square = new Square("square", ColorConversion.colorToRGBA(Colors.Black));
        square.setTransform(new Transform(new Vector2f(600, 300), new Vector2f(100, 100)));
        addGameObjectToScene(square);

        Circle circle = new Circle(new Vector2f(300, 300), 50);
        addGameObjectToScene(circle);

        this.activeGameObject = circle;
    }

    private void loadResources() {
        AssetPool.getShader("src/main/assets/shaders/default.glsl");

        AssetPool.addSpritesheet("src/main/assets/spritesheets/spritesheet_16x16.png",
                new Spritesheet(AssetPool.getTexture(
                        "src/main/assets/spritesheets/spritesheet_16x16.png"), 16, 16, 352,
                        0));

        sprites = AssetPool.getSpritesheet("src/main/assets/spritesheets/spritesheet_16x16.png");

        for (GameObject o : gameObjects) {
            if (o.getComponent(SpriteRenderer.class) != null) {
                SpriteRenderer spriteRenderer = o.getComponent(SpriteRenderer.class);
                if (spriteRenderer.getTexture() != null) {
                    spriteRenderer.setTexture(AssetPool.getTexture(spriteRenderer.getTexture().getFilePath()));
                }
            }
        }
    }

    @Override
    public void update(float dt) {
        testing.update(dt);
        // DebugDraw.addLine(
        // new Line(new Vector2f(300, 300), new Vector2f(330, 30),
        // ColorConversion.colorToRGBA(Colors.Red), 1,
        // 10));
        // Rectangle rec = new Rectangle(new Vector2f(500, 500), new Vector2f(100,
        // 100));
        // rec.setLineWidth(5);
        // rec.setColor(ColorConversion.colorToRGBA(Colors.Red));
        // DebugDraw.addRectangle(rec);
        // Circle circle;
        // circle = new Circle(new Vector2f(400, 400), 300, 14);
        // circle.setLineWidth(1);
        // circle.setColor(ColorConversion.colorToRGBA(Colors.Red));
        // DebugDraw.addCircle(circle);
        Ellipse ellipse = new Ellipse(new Vector2f(600, 300), 150, 100, 64, ColorConversion.colorToRGBA(Colors.Red), 1,
                5);
        DebugDraw.addEllipse(ellipse);

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
                GameObject object = Prefabs.createSpriteObjectFromTexture(sprite, 32, 32);
                testing.getComponent(MouseHelper.class).pickupObject(object);
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
