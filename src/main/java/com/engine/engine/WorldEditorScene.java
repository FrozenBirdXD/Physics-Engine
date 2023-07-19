package com.engine.engine;

import static org.lwjgl.opengl.GL33.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

import com.engine.engine.renderer.Shaders;
import com.engine.utils.Time;

public class WorldEditorScene extends Scene {
    private int vaoID, vboID, eboID;

    private Shaders defaultShader;

    private float[] vertexArray = {
            // pos // color
            100.5f, 0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, // Bottom right
            0.5f, 100.5f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, // Top left
            0.5f, 0.5f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, // Bottom left
            100.5f, 100.5f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, // Top right
    };

    // Must be in counter-clockwise order
    private int[] elementArray = {
            0, 3, 1, // Top right triangle
            0, 1, 2 // Bottom left triangle
    };

    public WorldEditorScene() {

    }

    @Override
    public void init() {
        this.camera = new Camera(new Vector2f());
        defaultShader = new Shaders("src/main/assets/shaders/default.glsl");
        defaultShader.compile();

        // Create VAO, VBO, and EBO buffer objects and sent to GPU
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        // Create a float buffer of vertices
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
        vertexBuffer.put(vertexArray).flip();

        // Create VBO upload the vertex buffer
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        // Create the indicies and upload
        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
        elementBuffer.put(elementArray).flip();

        eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

        // Add the vertex attribute pointers
        int positionsSize = 3;
        int colorsSize = 4;
        int floatSizeInByte = 4;
        int vertexSizeInBytes = (positionsSize + colorsSize) * floatSizeInByte;

        glVertexAttribPointer(0, positionsSize, GL_FLOAT, false, vertexSizeInBytes, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, colorsSize, GL_FLOAT, false, vertexSizeInBytes, positionsSize * floatSizeInByte);
        glEnableVertexAttribArray(1);
    }

    @Override
    public void update(float dt) {
        camera.position.x -= dt * 50.0f;
        camera.position.y -= dt * 50.0f;
        // System.out.println("" + (1.0f / dt) + " FPS");
        defaultShader.use();
                defaultShader.uploadMat4f("uProjectionMatrix", camera.getProjectionMatrix());
        defaultShader.uploadMat4f("uViewMatrix", camera.getViewMatrix());
        defaultShader.uploadFloat("uTime", Time.getTime());

        // Bind the VAO
        glBindVertexArray(vaoID);

        // Enable the vertex attribute pointers
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);

        // Unbind everything
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        glBindVertexArray(0);
        defaultShader.detach();
    }
}
