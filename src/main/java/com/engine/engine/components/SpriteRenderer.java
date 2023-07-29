package com.engine.engine.components;

import com.engine.engine.Component;

public class SpriteRenderer extends Component {
    private boolean firstTime = false;

    @Override
    public void update(float dt) {
        if (!firstTime) {
            System.out.println("I'm updating");
            firstTime = true;
        }
    }

    @Override
    public void start() {
        System.out.println("I'm starting");
    }
}
