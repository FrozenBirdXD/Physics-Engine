package com.engine.engine.components;

import org.joml.Vector4f;

import com.engine.engine.Component;

public class SpriteRenderer extends Component {
    private Vector4f color;

    public SpriteRenderer(Vector4f color) {
        this.color = color;
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
}
