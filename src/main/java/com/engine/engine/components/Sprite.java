package com.engine.engine.components;

import org.joml.Vector2f;

import com.engine.engine.renderer.Texture;

public class Sprite {
    private float width;
    private float height;
    private Texture texture = null;
    private Vector2f[] texCoords = {
            new Vector2f(1, 1),
            new Vector2f(1, 0),
            new Vector2f(0, 0),
            new Vector2f(0, 1),
    };

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Texture getTexture() {
        return this.texture;
    }

    public void setTexCoords(Vector2f[] texCoords) {
        this.texCoords = texCoords;
    }

    public Vector2f[] getTexCoords() {
        return this.texCoords;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public int getTextureId() {
        return texture == null ? -1 : texture.getTextureId();
    }
}
