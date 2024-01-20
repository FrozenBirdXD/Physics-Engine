package com.engine.graphics.components;

import org.joml.Vector2f;

import com.engine.graphics.Window;
import com.engine.graphics.renderer.DebugDraw;
import com.engine.graphics.utils.ColorConversion;
import com.engine.graphics.utils.Colors;
import com.engine.graphics.utils.Settings;

public class GridLines extends Component {
    @Override
    public void update(float dt) {
        Vector2f cameraPos = Window.get().getScene().getCamera().getPosition();
        Vector2f projectionSize = Window.get().getScene().getCamera().getProjectionSize();

        int height = (int) projectionSize.y + Settings.GRID_HEIGHT * 2;
        int width = (int) projectionSize.x + Settings.GRID_WIDTH * 2;

        int firstX = (int) ((cameraPos.x / Settings.GRID_WIDTH) - 1) * Settings.GRID_HEIGHT;
        int firstY = (int) ((cameraPos.y / Settings.GRID_HEIGHT) - 1) * Settings.GRID_WIDTH;
        int numLinesVertical = (int) (projectionSize.x / Settings.GRID_WIDTH) + 2;
        int numLinesHorizontal = (int) (projectionSize.y / Settings.GRID_HEIGHT) + 2;

        int maxLines = Math.max(numLinesHorizontal, numLinesVertical);
        for (int i = 0; i < maxLines; i++) {
            int x = firstX + Settings.GRID_WIDTH * i;
            int y = firstY + Settings.GRID_HEIGHT * i;

            if (i < numLinesVertical) {
                DebugDraw.addLine(new Vector2f(x, firstY), new Vector2f(x, firstY + height),
                        ColorConversion.colorToRGB(Colors.Black));
            }

            if (i < numLinesHorizontal) {
                DebugDraw.addLine(new Vector2f(firstX, y), new Vector2f(firstX + width, y),
                        ColorConversion.colorToRGB(Colors.Black));
            }
        }

    }
}
