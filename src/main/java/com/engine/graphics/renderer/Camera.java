package com.engine.graphics.renderer;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Camera {
    private Matrix4f projectionMatrix, viewMatrix, inverseProjectionMatrix, inverseViewMatrix;
    private Vector2f projectionSize = new Vector2f(32.0f * 40.0f, 32.0f * 21.0f);
    private Vector2f position;

    public Camera(Vector2f position) {
        this.position = position;
        this.projectionMatrix = new Matrix4f();
        this.viewMatrix = new Matrix4f();
        this.inverseProjectionMatrix = new Matrix4f();
        this.inverseViewMatrix = new Matrix4f();
        adjustProjection();
    }

    public void adjustProjection() {
        projectionMatrix.identity();
        // (0, 1920, 0, 1080, 0.5, 100) as inputs, this means that the camera can "see"
        // any objects that are between x-coordinates 0 and 1920, and y-coordinates 0
        // and 1080, and z-coordinates 0.5-100

        // 32.0f * 40.0f is the width of the camera
        // tells how big desired size of screen in in pixels or world units
        projectionMatrix.ortho(0.0f, projectionSize.x, 0.0f, projectionSize.y, 0.0f, 100.0f);
        projectionMatrix.invert(inverseProjectionMatrix);
    }

    public Matrix4f getViewMatrix() {
        // Determines where the camera is and where it is looking at
        Vector3f cameraFront = new Vector3f(0.0f, 0.0f, -1.0f);
        Vector3f cameraUp = new Vector3f(0.0f, 1.0f, 0.0f);
        viewMatrix.identity();
        viewMatrix.lookAt(new Vector3f(position.x, position.y, 20.0f),
                cameraFront.add(position.x, position.y, 0.0f), cameraUp);
        viewMatrix.invert(inverseViewMatrix);
        return this.viewMatrix;
    }

    public Matrix4f getProjectionMatrix() {
        return this.projectionMatrix;
    }

    public Matrix4f getInverseViewMatrix() {
        return this.inverseViewMatrix;
    }

    public Matrix4f getInverseProjectionMatrix() {
        return this.inverseProjectionMatrix;
    }

    public Vector2f getPosition() {
        return this.position;
    }

    public Vector2f getProjectionSize() {
        return this.projectionSize;
    }
}
