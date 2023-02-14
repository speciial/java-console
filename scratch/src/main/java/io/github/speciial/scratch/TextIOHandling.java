package io.github.speciial.scratch;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TextIOHandling extends Application {

    private static class TextBuffer {
        private final StringBuffer buffer;
        private int caretIndex;

        public TextBuffer(int initialCapacity) {
            buffer = new StringBuffer(initialCapacity);
            caretIndex = 0;
        }

        public void append(String s) {
            buffer.append(s);
        }

        public void writeAtCaret(char c) {
            buffer.insert(caretIndex, c);
            moveCaretForward();
        }

        public void deleteAtCaret() {
            if (caretIndex > 0) {
                buffer.delete((caretIndex - 1), caretIndex);
                moveCaretBackward();
            }
        }

        public void moveCaretForward() {
            if (caretIndex < buffer.length()) {
                caretIndex++;
            }
        }

        public void moveCaretBackward() {
            if (caretIndex >= 0) {
                caretIndex--;
            }
        }

        @Override
        public String toString() {
            return buffer.toString();
        }
    }

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 400, 300);

        TextBuffer buffer = new TextBuffer(1024);

        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            String currentChar = key.getText();

            switch (key.getCode()) {
                case ENTER -> {
                    System.out.println(buffer);
                }
                case BACK_SPACE -> {
                    buffer.deleteAtCaret();
                }
                case LEFT -> {
                    buffer.moveCaretBackward();
                }
                case RIGHT -> {
                    buffer.moveCaretForward();
                }
                default -> {
                    if (currentChar != null && !currentChar.isEmpty()) {
                        System.out.println(currentChar);
                        buffer.writeAtCaret(currentChar.charAt(0));
                    }
                }
            }
        });

        primaryStage.setScene(scene);
        primaryStage.setTitle("Scratch - TextIOHandling");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
