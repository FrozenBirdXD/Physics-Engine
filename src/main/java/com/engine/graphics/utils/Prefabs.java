package com.engine.graphics.utils;

import org.joml.Vector2f;

import com.engine.graphics.GameObject;
import com.engine.graphics.components.Sprite;
import com.engine.graphics.components.SpriteRenderer;

public class Prefabs {
    public static GameObject createSpriteObjectFromTexture(Sprite sprite, float spriteWidth, float spriteHeight) {
        GameObject object = new GameObject("Gen Sprite GameObject",
                new Transform(new Vector2f(0, 0), new Vector2f(spriteWidth, spriteHeight)), 4);

        SpriteRenderer renderer = new SpriteRenderer();
        renderer.setSprite(sprite);
        object.addComponent(renderer);
        return object;
    }
}
