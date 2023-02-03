package ui;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class TerminalApplication extends Application implements Runnable {

    /*
        Features to implement:

        [ ] drawing multiple lines
            [ ] line wrapping
        [ ] drawing the active line
        [ ] drawing the cursor
        [ ] scrolling
        [ ] editing:
            [ ] moving the cursor with the arrow keys
            [ ] highlighting (selecting) text
            [ ] ctrl+x, ctrl+c, ctrl+v
     */

    private final Queue<KeyEvent> keyInput = new ArrayDeque<>();
    private final ArrayList<String> committedLines = new ArrayList<>();
    private StringBuilder currentLineBuffer = new StringBuilder();

    private float nextTop = 0;

    // It is not strictly necessary to use a Runnable here and start the JFX application through a new thread since the
    // application is starting a separate ui thread anyway. This is purely for consistency and clear separation between
    // os and terminal.
    @Override
    public void run() {
        Application.launch();
    }

    @Override
    public void stop() {
    }

    private void handleInput() {
        while (!keyInput.isEmpty()) {
            KeyEvent event = keyInput.poll();

            if (!event.getCode().equals(KeyCode.ENTER) && (
                    event.getCode().isDigitKey() || event.getCode().isLetterKey() || event.getCode().isWhitespaceKey())) {
                currentLineBuffer.append(event.getText());
            }

            if (event.getCode().equals(KeyCode.ENTER)) {
                String newLine = currentLineBuffer.toString();
                committedLines.add(newLine);

                currentLineBuffer = new StringBuilder();
            }
        }

    }

    private void drawLine(Renderer renderer, String text, float x, float y) {
        Text textMetric = new Text(text);
        // textMetric.setFont(Font.font(20));
        Font textFont = textMetric.getFont();
        float textWidth = (float) textMetric.getLayoutBounds().getWidth();
        float textHeight = (float) textMetric.getLayoutBounds().getHeight();

        //nextTop += textHeight;

        renderer.renderRect(x, y, textWidth, textHeight, Color.RED, null, 0, 0);
        renderer.renderText(textFont, text, x, y + textHeight, Color.WHITE);
    }

    @Override
    public void start(Stage stage) {
        Group root = new Group();
        Renderer renderer = new Renderer(root, 800, 600);

        // Damn, this is pretty close to what I need... xD
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                handleInput();

                renderer.clear(Color.BLACK);
                for (String line : committedLines) {
                    drawLine(renderer, line, 10, 0);
                }

                drawLine(renderer, "THIS IS JUST A TEST", 100, 100);
            }
        };
        timer.start();

        Scene scene = new Scene(root);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, (event) -> {
            // System.out.println(event.toString());
            keyInput.add(event);
        });

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
}
