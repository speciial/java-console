package hsos.ui.components;

import hsos.ui.ApplicationEvent;
import hsos.ui.ApplicationInputStream;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;

import java.util.concurrent.BlockingQueue;

public class TerminalComponent extends TextArea {

    private final BlockingQueue<ApplicationEvent> eventQueue;
    private final ApplicationInputStream inputStream;

    private int lastValidWritePos;

    private String inputLine = "";

    public TerminalComponent(BlockingQueue<ApplicationEvent> eventQueue,
                             ApplicationInputStream inputStream,
                             double width, double height, Font font) {
        this.inputStream = inputStream;
        this.eventQueue = eventQueue;
        this.lastValidWritePos = 0;

        setUpFx(width, height, font);
        setUpKeyListener();
    }

    public void appendToTextBuffer(String content) {
        int currentContentLength = getContent().length();

        getContent().insert(currentContentLength, content, true);

        lastValidWritePos = getContent().length();
        selectRange(lastValidWritePos, lastValidWritePos);
    }

    private void setUpFx(double width, double height, Font font) {
        setFont(font);

        setMinWidth(width);
        setMaxWidth(width);
        setMinHeight(height);
        setMaxHeight(height);

        setWrapText(true);
    }

    private void setUpKeyListener() {
        addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()) {
                case ENTER -> {
                    // TODO(christian): on key presses, "record" all the relevant UI state (current line, caret
                    //                  position) in a structure

                    inputLine = getContent().get(lastValidWritePos, getContent().length());
                    inputStream.writeToBuffer(inputLine);
                    getContent().insert(getContent().length(), "\n", true);

                    eventQueue.add(ApplicationEvent.TERMINAL_KEY_ENTER);
                    event.consume();
                }
                case TAB -> {
                    inputLine = getContent().get(lastValidWritePos, getContent().length());

                    eventQueue.add(ApplicationEvent.TERMINAL_KEY_TAB);
                    event.consume();
                }
                case UP -> {
                    inputLine = getContent().get(lastValidWritePos, getContent().length());

                    eventQueue.add(ApplicationEvent.TERMINAL_KEY_UP);
                    event.consume();
                }
                case DOWN -> {
                    inputLine = getContent().get(lastValidWritePos, getContent().length());

                    eventQueue.add(ApplicationEvent.TERMINAL_KEY_DOWN);
                    event.consume();
                }
            }
        });
    }

}
