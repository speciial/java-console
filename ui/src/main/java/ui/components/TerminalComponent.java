package ui.components;

import javafx.scene.control.TextArea;
import javafx.scene.text.Font;

public class TerminalComponent extends TextArea {

    public TerminalComponent(double width, double height, Font font) {
        setFont(font);

        setMaxWidth(width);
        setMaxHeight(height);

        setWrapText(true);
    }

    public void appendToTextBuffer(String content) {
        int currentContentLength = getContent().length();

        getContent().insert(currentContentLength, content, true);
    }

}
