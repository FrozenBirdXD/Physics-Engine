package com.engine;

import com.engine.engine.Window;

public class Main {
    public static void main(String[] args) {

        Window window = Window.get();

        window.setTitle("hi");
        window.setResizable(false);
        window.setScene(0);
        window.show();
    }
}
