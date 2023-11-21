package com.engine.engine.components;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;

import com.engine.engine.renderer.Texture;

public class Spritesheet {
    private Texture texture;
    private List<Sprite> sprites;

    public Spritesheet(Texture texture, int spriteWidth, int spriteHeigth, int numSprites, int spacing) {
        this.sprites = new ArrayList<>();
        this.texture = texture;
        int currentX = 0;
        // top left sprite -> bottom left corner of it
        int currentY = texture.getHeight() - spriteHeigth;
        for (int i = 0; i < numSprites; i++) {
            float topY = (currentY + spriteHeigth) / (float) texture.getHeight();
            float rightX = (currentX + spriteWidth) / (float) texture.getWidth();
            float leftX = currentX / (float) texture.getWidth();
            float bottomY = currentY / (float) texture.getHeight();

            Vector2f[] texCoords = {
                    new Vector2f(rightX, topY),
                    new Vector2f(rightX, bottomY),
                    new Vector2f(leftX, bottomY),
                    new Vector2f(leftX, topY),
            };
            Sprite sprite = new Sprite(this.texture, texCoords);
            this.sprites.add(sprite);

            // increment current x and y
            currentX += spriteWidth + spacing;
            if (currentX >= texture.getWidth()) {
                currentX = 0;
                currentY -= spriteHeigth + spacing;
            }
        }
    }

    public Sprite getSprite(int index) {
        return sprites.get(index);
    }
}