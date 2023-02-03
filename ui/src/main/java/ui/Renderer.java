package ui;

import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Renderer {

    /*
        TODO:
            [ ] bind the width and height property?
     */

    private GraphicsContext gc;
    private float width;
    private float height;

    public Renderer(Group fxContext, float windowWidth, float windowHeight) {
        Canvas fxCanvas = new Canvas(windowWidth, windowHeight);
        gc = fxCanvas.getGraphicsContext2D();
        width = windowWidth;
        height = windowHeight;

        fxContext.getChildren().add(fxCanvas);
    }

    public void clear(Color color) {
        gc.setFill(color);
        gc.fillRect(0, 0, width, height);
    }

    public void renderText(Font font, String line, float x, float y, Color color) {
        gc.setFill(color);
        gc.setFont(font);
        gc.setTextBaseline(VPos.BOTTOM);
        gc.fillText(line, x, y);
    }

    public void renderRect(float x, float y, float width, float height, Color bgColor,
                           Color outlineColor, float outlineWidth, float radius) {
        gc.setFill(bgColor);
        gc.setStroke(outlineColor);

        if (outlineWidth != 0) {
            gc.setLineWidth(outlineWidth);
            float halfOutlineWidth = outlineWidth / 2;
            float correctX = x + halfOutlineWidth;
            float correctY = y + halfOutlineWidth;
            float correctWidth = width - outlineWidth;
            float correctHeight = height - outlineWidth;
            gc.strokeRoundRect(correctX, correctY, correctWidth, correctHeight, radius, radius);
            gc.fillRoundRect(correctX, correctY, correctWidth, correctHeight, radius, radius);
        } else {
            gc.fillRoundRect(x, y, width, height, radius, radius);
        }
    }

}
