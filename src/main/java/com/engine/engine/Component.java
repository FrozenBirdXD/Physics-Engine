package com.engine.engine;

public abstract class Component {
    // transient because GameObject already has a list components
    public transient GameObject gameObject = null;

    public void update(float dt) {

    }

    public void start() {

    }

    public void imgui() {

    }
}
