package com.engine.engine.renderer;

import java.util.ArrayList;
import java.util.List;

import com.engine.engine.GameObject;
import com.engine.engine.components.SpriteRenderer;

public class Renderer {
    private final int MAX_BATCH_SIZE = 10000;
    private List<RenderBatch> batches;

    public Renderer() {
        this.batches = new ArrayList<>();
    }

    public void add(GameObject object) {
        SpriteRenderer sprite = object.getComponent(SpriteRenderer.class);
        if (sprite != null) {
            add(sprite);
        }
    }

    private void add(SpriteRenderer sprite) {
        boolean added = false;
        for (RenderBatch batch : batches) {
            // fill until batch is full
            if (batch.hasRoom()) {
                Texture texture = sprite.getTexture();
                // if texture is null or texture already in batch or batch has room
                if (texture == null || batch.hasTexture(texture) || batch.hasTextureRoom()) {
                    batch.addSprite(sprite);
                    added = true;
                    // add to first available batch
                    break;
                }
            }
        }

        if (!added) {
            RenderBatch newBatch = new RenderBatch(MAX_BATCH_SIZE);
            newBatch.start();
            batches.add(newBatch);
            newBatch.addSprite(sprite);
        }
    }

    public void render() {
        for (RenderBatch batch : batches) {
            batch.render();
        }
    }

}
