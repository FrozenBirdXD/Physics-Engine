package com.engine.graphics;

import org.joml.Vector3f;
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import com.engine.graphics.listeners.DisplayListener;
import com.engine.graphics.listeners.KeyListener;
import com.engine.graphics.listeners.MouseListener;
import com.engine.graphics.scenes.Scene;
import com.engine.graphics.utils.ColorConversion;
import com.engine.graphics.utils.Colors;

import java.nio.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {
    private int width, height;
    private String title;
    private boolean resizable;
    private long glfwWindow;

    private ImGuiLayer imGuiLayer;

    public float r, g, b, a;
    private boolean fadeToBlack = false;

    private static Window window = null;
    // private static volatile Window window;
    // private static final ExecutorService executor =
    // Executors.newCachedThreadPool();
    private Scene currentScene;

    private Window() {
        this.width = 2560;
        this.width = 1920;
        this.height = 1440;
        this.height = 1080;
        this.title = "Physics Engine";
        this.resizable = true;
        r = 1;
        b = 1;
        g = 1;
        a = 1;

        init();
    }

    // singleton
    public static Window get() {
        if (Window.window == null) {
            Window.window = new Window();
        }

        return Window.window;
    }

    public void init() {
        // setup an error callback
        GLFWErrorCallback.createPrint(System.err).set();

        // initialize GLFW
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        // configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, resizable ? GLFW_TRUE : GLFW_FALSE); // resizeability
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_FALSE); // the window will be maximized

        // create the window with default values
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if (glfwWindow == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // callbacks after window is created
        // set mouse callbacks -> glfw calls this function when there is an event
        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback); // forward position function to the
                                                                               // 'mousePosCallback'
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);

        // set key callback
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);

        glfwSetFramebufferSizeCallback(glfwWindow, DisplayListener::framebufferSizeCallback);
        glfwSetWindowSizeCallback(glfwWindow, (win, newWidth, newHeight) -> {
            get().setWidth(newWidth);
            get().setHeigth(newHeight);
        });

        // make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow);
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        // enable alpha value blending
        glEnable(GL_BLEND);
        // blending Function with source and destination value
        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);

        // initialize imGui
        this.imGuiLayer = new ImGuiLayer(glfwWindow);
        this.imGuiLayer.initImGui();

        // specify OpenGL viewport size
        glViewport(0, 0, this.width, this.height);

        // enable v-sync
        glfwSwapInterval(1);
    }

    public void show() {
        System.out.println("Running LWJGL version " + Version.getVersion());

        // make the window visible
        glfwShowWindow(glfwWindow);

        loop();
        close();
    }

    public void close() {
        // free the window callbacks and destroy the window
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        // terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private boolean processCloseRequest() {
        boolean temp = true;
        if (glfwWindowShouldClose(glfwWindow)) {
            if (temp) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public void loop() {
        float beginTime = (float) glfwGetTime();
        float endTime;
        float dt = -1.0f;

        // render loop
        while (!processCloseRequest()) {
            // checks if any events are triggered
            glfwPollEvents();

            // set the clear color
            glClearColor(r, g, b, 0.5f);
            glClear(GL_COLOR_BUFFER_BIT);

            // update current scene
            if (dt >= 0) {
                currentScene.update(dt);
            }

            this.imGuiLayer.update(dt, currentScene);

            // swap the color buffers
            glfwSwapBuffers(glfwWindow);

            // time
            endTime = (float) glfwGetTime();
            dt = endTime - beginTime;
            beginTime = endTime;
        }

        // save scene to file
        currentScene.saveAndExit();
    }

    public void setScene(Scene scene) {
        if (scene == null) {
            return;
        }
        currentScene = scene;
        currentScene.load();
        currentScene.init();
        currentScene.start();
    }

    // public void setScene(int newScene) {
    // switch (newScene) {
    // case 0:
    // currentScene = new WorldEditorScene();
    // break;
    // case 1:
    // currentScene = new PlaySimulationScene();
    // break;
    // default:
    // assert false : "Unknown scene '" + newScene + "'";
    // break;
    // }

    // // load from save files
    // currentScene.load();
    // currentScene.init();
    // currentScene.start();
    // }

    public Scene getScene() {
        return get().currentScene;
    }

    public void setWindowBackgroundColor(Colors color) {
        Vector3f vec = ColorConversion.colorToRGB(color);
        this.r = vec.x;
        this.g = vec.y;
        this.b = vec.z;
    }

    public void setWindowBackgroundColorOpacity(float opacity) {
        // if (opacity < 0 || opacity > 1) {
        //     assert false : "Opacity '" + opacity + "' is not in range 0.0 to 1.0";
        // }
        this.a = opacity;
    }

    public void setTitle(String title) {
        this.title = title;
        glfwSetWindowTitle(glfwWindow, title);
    }

    public void setWindowOpacity(float opacity) {
        glfwSetWindowOpacity(glfwWindow, opacity);
    }

    private void setHeigth(int height) {
        this.height = height;
    }

    private void setWidth(int width) {
        this.width = width;
    }

    public void setWindowSize(int width, int height) {
        this.width = width;
        this.height = height;
        glfwSetWindowSize(glfwWindow, width, height);
    }

    public void setResizable(boolean resizable) {
        this.resizable = resizable;
        glfwSetWindowAttrib(glfwWindow, GLFW_RESIZABLE, resizable ? GLFW_TRUE : GLFW_FALSE);
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
