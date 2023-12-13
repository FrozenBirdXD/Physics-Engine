package com.engine.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.Templates;

import com.engine.engine.components.Spritesheet;
import com.engine.engine.renderer.Shaders;
import com.engine.engine.renderer.Texture;

public class AssetPool {
    private static Map<String, Shaders> shaders = new HashMap<>();
    private static Map<String, Texture> textures = new HashMap<>();
    private static Map<String, Spritesheet> spritesheets = new HashMap<>();

    public static Shaders getShader(String filePath) {
        File file = new File(filePath);
        if (AssetPool.shaders.containsKey(file.getAbsolutePath())) {
            return AssetPool.shaders.get(file.getAbsolutePath());
        } else {
            Shaders shader = new Shaders(filePath);
            shader.compile();
            // Shader is just one byte since it stores a reference
            AssetPool.shaders.put(file.getAbsolutePath(), shader);
            return shader;
        }
    }

    public static Texture getTexture(String filePath) {
        File file = new File(filePath);
        if (AssetPool.textures.containsKey(file.getAbsolutePath())) {
            return AssetPool.textures.get(file.getAbsolutePath());
        } else {
            Texture texture = new Texture();
            texture.init(filePath);
            AssetPool.textures.put(file.getAbsolutePath(), texture);
            return texture;
        }
    }

    public static void addSpritesheet(String filePath, Spritesheet spritesheet) {
        File file = new File(filePath);
        if (!AssetPool.spritesheets.containsKey(file.getAbsolutePath())) {
            AssetPool.spritesheets.put(file.getAbsolutePath(), spritesheet);
        }
    }

    public static Spritesheet getSpritesheet(String filePath) {
        File file = new File(filePath);
        if (!AssetPool.spritesheets.containsKey(file.getAbsolutePath())) {
            assert false : "Error: Tried to access spritesheet '" + filePath + "' and it's not in the AssetPool";
        }
        // default spritesheet
        return AssetPool.spritesheets.getOrDefault(file.getAbsolutePath(), null);
    }
}
