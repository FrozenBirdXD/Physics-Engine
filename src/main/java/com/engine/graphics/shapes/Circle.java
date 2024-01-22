package com.engine.graphics.shapes;

import org.joml.Vector2f;
import org.joml.Vector4f;

import com.engine.graphics.utils.ColorConversion;
import com.engine.graphics.utils.Colors;

public class Circle extends Shape {
    private Vector2f center;
    private float radius;
    private int lifetime;
    private int segments;

    public Circle(String name, Vector2f center, float radius, int segments, Vector4f color, int lifetime) {
        super(name, color);
        this.center = center;
        this.radius = radius;
        this.lifetime = lifetime;
        this.segments = segments;
    }

    public Circle(Vector2f center, float radius, int segments, Vector4f color, int lifetime) {
        this("Circle", center, radius, segments, color, lifetime);
    }

    public Circle(Vector2f center, float radius, int segments, Vector4f color) {
        this(center, radius, segments, color, 1);
    }

    public Circle(Vector2f center, float radius, int segments, int lifetime) {
        this(center, radius, segments, ColorConversion.colorToRGBA(Colors.Black), lifetime);
    }

    public Circle(Vector2f center, float radius, int segments) {
        this(center, radius, segments, ColorConversion.colorToRGBA(Colors.Black), 1);
    }

    public Circle(Vector2f center, float radius) {
        this(center, radius, 64);
    }

    public Vector2f getCenter() {
        return center;
    }

    public void setCenter(Vector2f center) {
        this.center = center;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public int getLifetime() {
        return lifetime;
    }

    public void setLifetime(int lifetime) {
        this.lifetime = lifetime;
    }

    public int getSegments() {
        return segments;
    }

    public void setSegments(int segments) {
        this.segments = segments;
    }
}
