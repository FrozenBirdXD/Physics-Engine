package com.engine;

import com.engine.graphics.Window;
import com.engine.graphics.WorldEditorScene;
import com.engine.graphics.utils.Colors;

public class Main {
    public static void main(String[] args) {

        Window window = Window.get();

        window.setTitle("hi");
        window.setResizable(true);
        // window.setWindowOpacity(0.8f);
        // window.setWindowBackgroundColor(Colors.Red);
        // window.setWindowBackgroundColorOpacity(33);

        WorldEditorScene scene = new WorldEditorScene();

        window.setScene(scene);
        window.show();
    }
}
