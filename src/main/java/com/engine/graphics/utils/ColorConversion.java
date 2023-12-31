package com.engine.graphics.utils;

import org.joml.Vector3f;

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
}
