package com.engine.engine.renderer;

import com.engine.engine.components.SpriteRenderer;

public class RenderBatch {
    // Vertex
    // =====
    // Pos                      Color
    // float, float,            float, float, float, float

    private Shaders shader;
    private SpriteRenderer[] sprites;
    private int numSprites;
    private boolean hasRoom;
    private float[] vertices;

    private int vaoID, vboID;
    private int maxBatchSize;

    private final int POS_SIZE = 2;
    private final int COLORSIZE = 4;

    private final int POS_OFFSET = 0;
    private final int COLOR_OFFSET = POS_OFFSET + POS_SIZE * Float.BYTES;
    private final int VERTEX_SIZE = 6;
    private final int VERTEX_SIZE_BYTES = VERTEX_SIZE * Float.BYTES;

    public void start() {

    }

    public void addSprite(SpriteRenderer spriteRenderer) {

    }

    private int[] generateIndices() {
        return new int[0];
    }

    public void render() {

    }

    private void loadVertexProperties(int index) {

    }
}
