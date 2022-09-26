package ui;

import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

public class CustomTerminal extends TextArea {

    private final BlockingQueue<String> eventQueue;

    private int lastValidCaretPosition = 0;

    // TODO: JUST FOR TESTING
    private final ArrayList<String> inputBuffer = new ArrayList<>();
    private int bufferPosition = 0;

    public CustomTerminal(BlockingQueue<String> eventQueue) {
        this.eventQueue = eventQueue;

        newPromptLine();

        this.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()) {
                case ENTER -> {
                    // HANDLE INPUT
                    String currentLine = this.getContent().get(lastValidCaretPosition, this.getLength());
                    addLineToBuffer(currentLine);

                    // TEST OUTPUT
                    System.out.println(Thread.currentThread().getName() + " > " + currentLine);

                    try {
                        // TODO: CLEANUP!!
                        this.eventQueue.put(currentLine);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // NEW LINE
                    newPromptLine();
                    event.consume();
                }
                case BACK_SPACE -> {
                    // TODO: if the entire line is selected back space does not work anymore since the event is consumed
                    if (this.getCaretPosition() <= lastValidCaretPosition) {
                        event.consume();
                    }
                }
                case TAB -> {
                    // check for auto-complete
                }
                case UP -> {
                    // cycle up in the command buffer
                    this.getContent().delete(lastValidCaretPosition, this.getLength(), true);
                    this.getContent().insert(lastValidCaretPosition, bufferUp(), true);

                    event.consume();
                }
                case DOWN -> {
                    // cycle down in the command buffer
                    this.getContent().delete(lastValidCaretPosition, this.getLength(), true);
                    this.getContent().insert(lastValidCaretPosition, bufferDown(), true);

                    event.consume();
                }
            }
        });

        this.caretPositionProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() < lastValidCaretPosition) {
                this.positionCaret(lastValidCaretPosition);
            }
        });
    }

    public void println(String line) {
        this.getContent().insert(getLength(), "\n" + line, true);
        newPromptLine();
    }

    private void newPromptLine() {
        String directoryPrompt = "c:/dev";
        String newLinePrompt = '\n' + directoryPrompt + " >";
        int currentLength = getContent().length();
        this.getContent().insert(currentLength, newLinePrompt, true);

        int newCaretPosition = currentLength + newLinePrompt.length();
        this.selectRange(newCaretPosition, newCaretPosition);

        lastValidCaretPosition = newCaretPosition;
    }

    private void addLineToBuffer(String line) {
        inputBuffer.add(line);
        bufferPosition = inputBuffer.size() - 1;
    }

    private String bufferUp() {
        String result = "";
        if (bufferPosition >= 0) {
            result = inputBuffer.get(bufferPosition);
            bufferPosition--;
        }
        return result;
    }

    private String bufferDown() {
        String result = "";
        if (bufferPosition < (inputBuffer.size() - 1)) {
            bufferPosition++;
            result = inputBuffer.get(bufferPosition);
        }
        return result;
    }
}
