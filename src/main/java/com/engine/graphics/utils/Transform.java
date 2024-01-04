package com.engine.graphics.utils;

import org.joml.Vector2f;

public class Transform {
    private Vector2f position;
    private Vector2f scale;

    public Transform() {
        init(new Vector2f(), new Vector2f());
    }

    public Transform(Vector2f position) {
        init(position, new Vector2f());
    }

    public Transform(Vector2f position, Vector2f scale) {
        init(position, scale);
    }

    public void init(Vector2f position, Vector2f scale) {
        this.position = position;
        this.scale = scale;
    }

    public Transform copy() {
        return new Transform(new Vector2f(this.position), new Vector2f(this.scale));
    }

    public void copy(Transform to) {
        to.position.set(this.position);
        to.scale.set(this.scale);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof Transform)) {
            return false;
        }

        Transform transform = (Transform) o;
        return transform.position.equals(this.position) && transform.scale.equals(this.scale);
    }

    public void setPosition(float x, float y) {
        this.position.x = x;
        this.position.y = y;
    }

    public float getPositionX() {
        return this.position.x;
    }

    public float getPositionY() {
        return this.position.y;
    }

    public float getScaleX() {
        return this.scale.x;
    }

    public float getScaleY() {
        return this.scale.y;
    }
}
