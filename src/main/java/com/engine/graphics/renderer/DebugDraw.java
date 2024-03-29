package com.engine.graphics.renderer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector4f;

import com.engine.graphics.Window;
import com.engine.graphics.shapes.Circle;
import com.engine.graphics.shapes.Ellipse;
import com.engine.graphics.shapes.Line;
import com.engine.graphics.shapes.Rectangle;
import com.engine.graphics.utils.AssetPool;
import com.engine.math.FrozenMath;

import static org.lwjgl.opengl.GL33.*;

public class DebugDraw {
    private static int MAX_LINES = 500;
    private static List<Line> lines = new ArrayList<>();
    // 6 floats per vertex, 2 vertices per line
    private static float[] vertexArray = new float[MAX_LINES * 6 * 2];
    private static Shaders shader = AssetPool.getShader("src/main/assets/shaders/debugLine2D.glsl");

    private static int vaoID;
    private static int vboID;
    private static boolean started = false;

    private static int lineWidth = 1;

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
        glLineWidth(2);
    }

    public static void beginFrame() {
        if (!started) {
            start();
            started = true;
        }

        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).beginFrame() < 0) {
                lines.remove(i);
                i--;
            }
        }
    }

    public static void draw() {
        if (lines.size() <= 0) {
            return;
        }

        int index = 0;
        for (Line line : lines) {
            for (int i = 0; i < 2; i++) {
                Vector2f position = i == 0 ? line.getFrom() : line.getTo();
                Vector4f color = line.getColor();

                // load position
                vertexArray[index] = position.x;
                vertexArray[index + 1] = position.y;
                vertexArray[index + 2] = -10.0f;

                // load the color
                vertexArray[index + 3] = color.x;
                vertexArray[index + 4] = color.y;
                vertexArray[index + 5] = color.z;
                index += 6;
            }
        }

        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferSubData(GL_ARRAY_BUFFER, 0, Arrays.copyOfRange(vertexArray, 0,
                lines.size() * 6 * 2));

        // use shader
        shader.use();
        shader.uploadMat4f("uProjection",
                Window.get().getScene().getCamera().getProjectionMatrix());
        shader.uploadMat4f("uView",
                Window.get().getScene().getCamera().getViewMatrix());

        // bind vao
        glBindVertexArray(vaoID);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        // draw the batch
        glDrawArrays(GL_LINES, 0, lines.size() * 6 * 2);

        // disable location
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);

        // unbind shader
        shader.detach();
    }

    public static void addLine(Line line) {
        if (lines.size() >= MAX_LINES) {
            return;
        }

        DebugDraw.lines.add(line);
    }

    public static void addRectangle(Rectangle rectangle) {
        // get center, subtract half of the size -> bottom left corner
        Vector2f min = new Vector2f(rectangle.getCenter()).sub(new Vector2f(rectangle.getDimensions()).div(2.0f));
        // top right corner
        Vector2f max = new Vector2f(rectangle.getCenter()).add(new Vector2f(rectangle.getDimensions()).div(2.0f));

        Vector2f[] vertices = {
                new Vector2f(min.x, min.y), // bottom left
                new Vector2f(min.x, max.y), // top left
                new Vector2f(max.x, max.y), // top right
                new Vector2f(max.x, min.y), // bottom right
        };

        if (rectangle.getRotation() != 0.0f) {
            for (Vector2f vec : vertices) {
                FrozenMath.rotate(vec, rectangle.getRotation(), rectangle.getCenter());
            }
        }

        Vector4f color = rectangle.getColor();
        int lifetime = rectangle.getLifetime();
        int lineWidth = rectangle.getLineWidth();

        addLine(new Line(vertices[0], vertices[1], color, lifetime, lineWidth));
        addLine(new Line(vertices[1], vertices[2], color, lifetime, lineWidth));
        addLine(new Line(vertices[2], vertices[3], color, lifetime, lineWidth));
        addLine(new Line(vertices[3], vertices[0], color, lifetime, lineWidth));
    }

    public static void addCircle(Circle circle) {
        List<Vector2f> circlePoints = calculateCirclePoints(circle.getCenter(), circle.getRadius(),
                circle.getSegments());

        for (int i = 0; i < circlePoints.size() - 1; i++) {
            addLine(new Line(circlePoints.get(i), circlePoints.get(i + 1), circle.getColor(), circle.getLifetime(),
                    circle.getLineWidth()));
        }
        // last line segment
        addLine(new Line(circlePoints.get(circlePoints.size() - 1), circlePoints.get(0), circle.getColor(),
                circle.getLifetime(), circle.getLineWidth()));
    }

    private static List<Vector2f> calculateCirclePoints(Vector2f center, float radius, int segments) {
        List<Vector2f> points = new ArrayList<>();
        float angleIncrement = 360.0f / segments;

        for (int i = 0; i < segments; i++) {
            float angle = (float) Math.toRadians(angleIncrement * i);
            float x = center.x + radius * (float) Math.cos(angle);
            float y = center.y + radius * (float) Math.sin(angle);
            points.add(new Vector2f(x, y));
        }

        return points;
    }

    public static void addEllipse(Ellipse ellipse) {
        List<Vector2f> ellipsePoints = calculateEllipsePoints(ellipse.getCenter(), ellipse.getRadiusX(),
                ellipse.getRadiusY(),
                ellipse.getSegments());

        for (int i = 0; i < ellipsePoints.size() - 1; i++) {
            addLine(new Line(ellipsePoints.get(i), ellipsePoints.get(i + 1), ellipse.getColor(), ellipse.getLifetime(),
                    ellipse.getLineWidth()));
        }
        // last line segment
        addLine(new Line(ellipsePoints.get(ellipsePoints.size() - 1), ellipsePoints.get(0), ellipse.getColor(),
                ellipse.getLifetime(), ellipse.getLineWidth()));
    }

    private static List<Vector2f> calculateEllipsePoints(Vector2f center, float radiusX, float radiusY, int segments) {
        List<Vector2f> points = new ArrayList<>();
        float angleIncrement = 360.0f / segments;

        for (int i = 0; i < segments; i++) {
            float angle = (float) Math.toRadians(angleIncrement * i);
            float x = center.x + radiusX * (float) Math.cos(angle);
            float y = center.y + radiusY * (float) Math.sin(angle);
            points.add(new Vector2f(x, y));
        }

        return points;
    }
}
