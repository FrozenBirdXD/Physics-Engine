package com.engine.engine.components;

import org.joml.Vector2f;
import org.joml.Vector4f;

import com.engine.engine.Component;
import com.engine.engine.renderer.Texture;

public class SpriteRenderer extends Component {
    private Vector4f color;
    private Texture texture;
    private Vector2f[] texCoords;
    // (0,1) br
    // (0,0) bl
    // (1,1) tr
    // (1,0) tl

    public SpriteRenderer(Vector4f color) {
        this.texture = null;
        this.color = color;
    }

    public SpriteRenderer(Texture texture) {
        this.texture = texture;
        this.color = new Vector4f(1, 1, 1, 1);
    }

    public SpriteRenderer() {
    }

    @Override
    public void update(float dt) {
    }

    @Override
    public void start() {
    }

    public Vector4f getColor() {
        return this.color;
    }

    public Texture getTexture() {
        return this.texture;
    }

    public Vector2f[] getTexCoords() {
        Vector2f[] texCoords = {
            new Vector2f(1, 1),
            new Vector2f(1, 0),
            new Vector2f(0, 0),
            new Vector2f(0, 1),
        };
        return texCoords;
    }
}
