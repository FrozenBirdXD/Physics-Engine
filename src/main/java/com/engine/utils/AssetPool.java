package com.engine.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.Templates;

import com.engine.engine.renderer.Shaders;
import com.engine.engine.renderer.Texture;

public class AssetPool {
    private static Map<String, Shaders> shaders = new HashMap<>();
    private static Map<String, Texture> textures = new HashMap<>();

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
            Texture texture = new Texture(filePath);
            AssetPool.textures.put(file.getAbsolutePath(), texture);
            return texture;
        }
    }
}
