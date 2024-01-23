package com.engine.graphics.shapes;

import org.joml.Vector2f;
import org.joml.Vector4f;

import com.engine.graphics.utils.ColorConversion;
import com.engine.graphics.utils.Colors;

public class Line extends Shape {
    private Vector2f from;
    private Vector2f to;
    private int lifetime;

    public Line(Vector2f from, Vector2f to) {
        this(from, to, ColorConversion.colorToRGBA(Colors.Black), 1);
    }

    public Line(Vector2f from, Vector2f to, Vector4f color) {
        this(from, to, color, 1);
    }

    public Line(Vector2f from, Vector2f to, Vector4f color, int lifetime) {
        this("Line", from, to, color, lifetime);
    }

    public Line(Vector2f from, Vector2f to, Vector4f color, int lifetime, int lineWidth) {
        this("Line", from, to, color, lifetime, lineWidth);
    }

    public Line(String name, Vector2f from, Vector2f to) {
        this(name, from, to, ColorConversion.colorToRGBA(Colors.Black), 1);
    }

    public Line(String name, Vector2f from, Vector2f to, Vector4f color) {
        this(name, from, to, color, 1, 1);
    }

    public Line(String name, Vector2f from, Vector2f to, Vector4f color, int lifetime) {
        this(name, from, to, color, lifetime, 1);
    }

    public Line(String name, Vector2f from, Vector2f to, Vector4f color, int lifetime, int lineWidth) {
        this.name = name;
        this.from = from;
        this.to = to;
        this.color = color;
        this.lifetime = lifetime;
        this.lineWidth = lineWidth;
    }

    public int beginFrame() {
        lifetime--;
        return lifetime;
    }

    public Vector2f getFrom() {
        return this.from;
    }

    public void setFrom(Vector2f from) {
        this.from = from;
    }

    public Vector2f getTo() {
        return this.to;
    }

    public void setTo(Vector2f to) {
        this.to = to;
    }
}
