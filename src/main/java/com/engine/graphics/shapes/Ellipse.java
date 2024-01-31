package com.engine.graphics.shapes;

import org.joml.Vector2f;
import org.joml.Vector4f;

public class Ellipse extends Shape {
    private Vector2f center;
    private float radiusX;
    private float radiusY;
    private int segments;
    private Vector4f color;
    private int lifetime;
    private int lineWidth;

    public Ellipse(Vector2f center, float radiusX, float radiusY, int segments, Vector4f color, int lifetime,
            int lineWidth) {
        super("Ellipse", color, lineWidth);
        this.center = center;
        this.radiusX = radiusX;
        this.radiusY = radiusY;
        this.segments = segments;
        this.lifetime = lifetime;
    }

    public Ellipse(String name, Vector4f color) {
        super(name, color);
    }

    public Vector2f getCenter() {
        return center;
    }

    public float getRadiusX() {
        return radiusX;
    }

    public float getRadiusY() {
        return radiusY;
    }

    public int getSegments() {
        return segments;
    }

    public int getLifetime() {
        return lifetime;
    }
}
