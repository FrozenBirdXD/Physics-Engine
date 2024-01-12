package com.engine.graphics.renderer;

import java.util.ArrayList;
import java.util.List;

import com.engine.graphics.shapes.Line2D;
import com.engine.graphics.utils.AssetPool;

import static org.lwjgl.opengl.GL33.*;

public class DebugDraw {
    private static int MAX_LINES = 500;
    private static List<Line2D> lines = new ArrayList<>();
    // 6 floats per vertex, 2 vertices per line
    private static float[] vertexArray = new float[MAX_LINES * 6 * 2];
    private static Shaders shader = AssetPool.getShader("src/main/assets/shaders/debugLine2D.glsl");

    private static int vaoID;
    private static int vboID;
    private static boolean started = false;

    public static void start() {
        // generate vao
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        // create vbo and buffer some memory
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertexArray.length * Float.BYTES, GL_DYNAMIC_DRAW);

        // Enable the vertex attributes
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 6 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, 3, GL_FLOAT, false, 6 * Float.BYTES, 3 * Float.BYTES);
        glEnableVertexAttribArray(1);

        // set line width
    }

    public static void beginFrame() {
        if (!started) {
            start();
            started = true;
        }
    }

    public static void draw() {

    }
}
