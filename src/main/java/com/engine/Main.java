package com.engine;

import com.engine.engine.Window;

public class Main {
    public static void main(String[] args) {
        Window window = Window.get();
        window.setTitle("Physics Engine");
        window.run();
    }
}
