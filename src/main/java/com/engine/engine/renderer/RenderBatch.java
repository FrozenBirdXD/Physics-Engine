package com.engine.engine.renderer;

import static org.lwjgl.opengl.GL33.*;

import com.engine.engine.components.SpriteRenderer;

public class RenderBatch {
    // Vertex
    // =====
    // Pos Color
    // float, float, float, float, float, float

    private Shaders shader;
    private SpriteRenderer[] sprites;
    private int numSprites;
    private boolean hasRoom;
    private float[] vertices;

    private int vaoID, vboID;
    private int maxBatchSize;

    private final int POS_SIZE = 2;
    private final int COLOR_SIZE = 4;

    private final int POS_OFFSET = 0;
    private final int COLOR_OFFSET = POS_OFFSET + POS_SIZE * Float.BYTES;
    private final int VERTEX_SIZE = 6;
    private final int VERTEX_SIZE_BYTES = VERTEX_SIZE * Float.BYTES;

    // maxBatchSize is the max amount of sprites
    public RenderBatch(int maxBatchSize) {
        shader = new Shaders("assets/shaders/default.glsl");
        shader.compile();
        this.sprites = new SpriteRenderer[maxBatchSize];
        this.maxBatchSize = maxBatchSize;

        // 4 vertices per quad
        // all the floats for the vertices
        // each sprite has 4 vertices, each vertex has 6 floats (VERTEX_SIZE)
        vertices = new float[maxBatchSize * 4 * VERTEX_SIZE];

        this.numSprites = 0;
        this.hasRoom = true;
    }

    public void start() {
        // Generate and bind vertex array object
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        // Allocate space vertices
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertices.length * Float.BYTES, GL_DYNAMIC_DRAW);

        // Create and upload indices buffer
        int eboID = glGenBuffers();
        int[] indices = generateIndices();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

        // Enable Buffer Attribute pointers; how vertex is laid out
        glVertexAttribPointer(0, POS_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, POS_OFFSET);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, COLOR_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, COLOR_OFFSET);
        glEnableVertexAttribArray(1);
    }

    public void addSprite(SpriteRenderer spriteRenderer) {

    }

    private int[] generateIndices() {
        // 6 indices per quad (3 per triangle)
        return new int[0];
    }

    public void render() {

    }

    private void loadVertexProperties(int index) {

    }
}
