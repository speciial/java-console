package ui;

import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

public class CustomTerminalComponent extends TextArea {

    private int lastValidCaretPosition = 0;

    // JUST FOR TESTING
    private final ArrayList<String> inputBuffer = new ArrayList<>();
    private int bufferPosition = 0;

    public CustomTerminalComponent() {
        this.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()) {
                case ENTER -> {
                    // HANDLE INPUT
                    String currentLine = getCurrentLineContent();
                    addLineToBuffer(currentLine);

                    // DO THE WORK HERE!!
                    Terminal.commitEvent(new TerminalEvent(TerminalEventType.NEW_LINE, null));
                    Terminal.in.writeLine(currentLine);

                    // TODO: this should use the output stream as well since that is going to be synchronized!
                    // TODO: disable horizontal scrolling (enable line wrap)

                    // NEW LINE
                    event.consume();
                }
                case BACK_SPACE -> {
                    if (this.getCaretPosition() <= lastValidCaretPosition) {
                        event.consume();
                    }
                }
                case TAB -> {
                    // get the part of the string which the cursor is on
                    String currentLine = getCurrentLineContent();
                    int caretIndex = getCaretPosition() - lastValidCaretPosition;
                    int currentWordStartIndex = findFirstIndexOfCurrentWord(currentLine.toCharArray(), caretIndex);
                    String toComplete = currentLine.substring(currentWordStartIndex, caretIndex);

                    Terminal.commitEvent(new TerminalEvent(TerminalEventType.KEY_TAB, toComplete));

                    event.consume();
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

    // TODO: add something like "applyTheme" with different parameters so people can style their terminal the way they
    //       want.
    public void setColor() {
        this.setStyle("-fx-background-color: #ff00ff; -fx-control-inner-background: #ff00ff");
    }

    public void setAutoCompleteString(String match) {
        String currentLine = getCurrentLineContent();

        // 1. find the last white space in front of the cursor position
        int caretIndex = getCaretPosition() - lastValidCaretPosition;
        int whiteSpaceIndex = findFirstIndexOfCurrentWord(currentLine.toCharArray(), caretIndex);
        whiteSpaceIndex += lastValidCaretPosition;

        // NOTE: the caret needs to be moved in front of the substring or a null pointer is thrown
        positionCaret(whiteSpaceIndex);

        // 2. delete substring
        this.getContent().delete(whiteSpaceIndex, this.getContent().length(), true);

        // 3. insert auto-complete match
        this.getContent().insert(whiteSpaceIndex, match, true);

        // 4. position caret at the end of the line
        positionCaret(this.getContent().length());
    }

    public void println(String line) {
        String lineWithNewLine = "\n" + line + "\n";
        int currentContentLength = getContent().length();

        this.getContent().insert(currentContentLength, lineWithNewLine, true);

        int newCaretPosition = currentContentLength + lineWithNewLine.length();
        this.selectRange(newCaretPosition, newCaretPosition);

        lastValidCaretPosition = newCaretPosition;
    }

    private String getCurrentLineContent() {
        return this.getContent().get(lastValidCaretPosition, this.getLength());
    }

    /**
     * This method finds the start index of a word (string of characters without separating white space) in a given char
     * array. The caret index must to be passed relative to the start of the char array where 0 means the first index.
     *
     * @param chars Array containing an arbitrary char sequence
     * @param currentCaretIndex Caret index relative to the start of the array
     * @return Start index of the word caret is currently on
     */
    private int findFirstIndexOfCurrentWord(char[] chars, int currentCaretIndex) {
        int whiteSpaceIndex = 0;

        // TODO: make this iterate backwards through the array
        for (int charIndex = 0; charIndex < chars.length; charIndex++) {
            if(chars[charIndex] == ' ' && charIndex < currentCaretIndex) {
                whiteSpaceIndex = (charIndex + 1);
            }
        }

        return whiteSpaceIndex;
    }

    // ================================================================================================================
    // JUST FOR TESTING
    // ================================================================================================================
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
