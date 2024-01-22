package com.engine.graphics.shapes;

import org.joml.Vector2f;
import org.joml.Vector4f;

import com.engine.graphics.utils.ColorConversion;
import com.engine.graphics.utils.Colors;

public class Rectangle extends Shape {
    private Vector2f center;
    private Vector2f dimensions;
    private float rotation;
    private int lifetime;

    public Rectangle(String name, Vector2f center, Vector2f dimensions, float rotation, Vector4f color, int lifetime) {
        this.name = name;
        this.center = center;
        this.dimensions = dimensions;
        this.rotation = rotation;
        this.color = color;
        this.lifetime = lifetime;
    }

    public Rectangle(Vector2f center, Vector2f dimensions) {
        this(center, dimensions, 0.0f, ColorConversion.colorToRGBA(Colors.Black), 1);
    }

    public Rectangle(Vector2f center, Vector2f dimensions, float rotation) {
        this(center, dimensions, rotation, ColorConversion.colorToRGBA(Colors.Black), 1);
    }

    public Rectangle(Vector2f center, Vector2f dimensions, float rotation, Vector4f color) {
        this(center, dimensions, rotation, color, 1);
    }

    public Rectangle(Vector2f center, Vector2f dimensions, float rotation, Vector4f color, int lifetime) {
        this("Rectangle", center, dimensions, rotation, color, lifetime);
    }

    public Vector2f getCenter() {
        return center;
    }

    public void setCenter(Vector2f center) {
        this.center = center;
    }

    public Vector2f getDimensions() {
        return dimensions;
    }

    public void setDimensions(Vector2f dimensions) {
        this.dimensions = dimensions;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public int getLifetime() {
        return lifetime;
    }

    public void setLifetime(int lifetime) {
        this.lifetime = lifetime;
    }
}
