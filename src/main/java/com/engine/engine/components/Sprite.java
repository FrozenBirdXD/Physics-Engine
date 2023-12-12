package com.engine.engine.components;

import org.joml.Vector2f;

import com.engine.engine.renderer.Texture;

public class Sprite {
    private Texture texture = null;
    private Vector2f[] texCoords = {
            new Vector2f(1, 1),
            new Vector2f(1, 0),
            new Vector2f(0, 0),
            new Vector2f(0, 1),
    };

    // public Sprite(Texture texture) {
    // this.texture = texture;
    // // Assume texture is the whole image
    // Vector2f[] texCoords =
    // this.texCoords = texCoords;
    // }

    // public Sprite(Texture texture, Vector2f[] texCoords) {
    // this.texture = texture;
    // this.texCoords = texCoords;
    // }

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
}
