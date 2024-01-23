package com.engine.graphics.shapes;

import org.joml.Vector4f;

import com.engine.graphics.GameObject;
import com.engine.graphics.components.Sprite;
import com.engine.graphics.components.SpriteRenderer;
import com.engine.graphics.components.Spritesheet;
import com.engine.graphics.renderer.Texture;
import com.engine.graphics.utils.AssetPool;

public abstract class Shape extends GameObject {
    protected Vector4f color;
    protected int lineWidth = 1;
    private SpriteRenderer spriteRenderer;

    public Shape(String name, Vector4f color, int lineWidth) {
        this(name, color);
        this.lineWidth = lineWidth;
    }

    public Shape(Vector4f color) {
        this("Shape", color);
    }

    public Shape() {
        this("Shape", new Vector4f(1, 1, 1, 0));
    }

    public Shape(String name) {
        this(name, new Vector4f(1, 1, 1, 0));
    }

    public Shape(String name, Vector4f color) {
        super(name);
        this.color = color;

        spriteRenderer = new SpriteRenderer();
        spriteRenderer.setColor(color);
        // Sprite sprite = new Sprite();
        // Texture emptyTexture = new Texture();

        // AssetPool.addSpritesheet("src/main/assets/spritesheets/spritesheet_16x16.png",
        // new
        // Spritesheet(AssetPool.getTexture("src/main/assets/spritesheets/spritesheet_16x16.png"),
        // 16, 16, 352,
        // 0));

        // Spritesheet sprites =
        // AssetPool.getSpritesheet("src/main/assets/spritesheets/spritesheet_16x16.png");

        // spriteRenderer.setSprite(sprites.getSprite(351));
        addComponent(spriteRenderer);
        setZIndex(2);
    }

    public void setColor(Vector4f color) {
        this.color = color;
        spriteRenderer.setColor(color);
    }

    public Vector4f getColor() {
        return this.color;
    }

    public int getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
    }

}
