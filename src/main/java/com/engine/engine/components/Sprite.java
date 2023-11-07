package com.engine.engine.components;

import org.joml.Vector2f;

import com.engine.engine.renderer.Texture;

public class Sprite {
    private Texture texture;
    private Vector2f[] texCoords;
    // (0,1) br
    // (0,0) bl
    // (1,1) tr
    // (1,0) tl

    public Sprite(Texture texture) {
        this.texture = texture;
        // Assume texture is the whole image
        Vector2f[] texCoords = {
            new Vector2f(1, 1),
            new Vector2f(1, 0),
            new Vector2f(0, 0),
            new Vector2f(0, 1),
        };
        this.texCoords = texCoords;
    }
    
    public Sprite(Texture texture, Vector2f[] texCoords) {
        this.texture = texture;
        this.texCoords = texCoords;
    }

    public Texture getTexture() {
        return this.texture;
    }

    public Vector2f[] getTexCoords() {
        return this.texCoords;
    }
}
