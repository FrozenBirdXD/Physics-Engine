package com.engine.graphics.components;

import org.joml.Vector3f;
import org.joml.Vector4f;

import com.engine.graphics.Component;

public class RigidBody extends Component {
    private int colliderType = 0;
    private float friction = 0.8f;
    private boolean isFalling = false;
    public Vector3f velocity = new Vector3f(0, 0.5f, 0);
    public transient Vector4f temporary = new Vector4f(0, 0, 0, 0);
}
