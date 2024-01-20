package com.engine.graphics.utils;

import org.joml.Vector3f;
import org.joml.Vector4f;

public class ColorConversion {
    public static Vector3f colorToRGB(Colors color) {
        Vector3f result = new Vector3f();

        switch (color) {
            case Red:
                result.set(255, 0, 0);
                break;
            case Black:
                result.set(0, 0, 0);
                break;
            case White:
                result.set(255, 255, 255);
                break;
            default:
                result.set(0, 0, 0);
                break;
        }

        return result;
    }

    public static Vector4f colorToRGBA(Colors color) {
        Vector4f result = new Vector4f();

        switch (color) {
            case Red:
                result.set(255, 0, 0, 1);
                break;
            case Black:
                result.set(0, 0, 0, 1);
                break;
            case White:
                result.set(255, 255, 255, 1);
                break;
            default:
                result.set(0, 0, 0, 1);
                break;
        }

        return result;
    }
}
