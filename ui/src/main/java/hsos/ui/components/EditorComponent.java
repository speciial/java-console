package hsos.ui.components;

import hsos.ui.ApplicationEditorCallBack;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ListChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EditorComponent extends VBox {
    // TODO(christian): Since this is a more or less static component which does not change, we may want to turn this
    //                  into a fxml file and load it. It would also reduce the amount of code in this file.
    // TODO(christian): Change the css class names for info-line and info-line-text to something more general since the
    //                  properties are shared between info line and instruction line.
    // TODO(christian): implement a way to capture the caret position in terms of rows and columns.

    private TextArea editorArea;
    private InfoLine infoLine;
    private InstructionLine instructionLine;

    private final DateTimeFormatter dtfDate = DateTimeFormatter.ofPattern("HH:mm");
    private Timeline clock;

    private String filename;

    private ApplicationEditorCallBack callBack;

    public EditorComponent(double width, double height, Font font) {
        setUpFx(width, height, font);
        setUpKeyListener();
    }

    public void setFilename(String filename) {
        this.filename = filename;
        infoLine.updateFilename(this.filename);
    }

    public void setCallBack(ApplicationEditorCallBack callBack) {
        this.callBack = callBack;
    }

    public void enable() {
        infoLine.updateTime(dtfDate.format(LocalDateTime.now()));
        clock.play();
    }

    public void disable() {
        clock.stop();
    }

    public void setFont(Font font) {
        infoLine.setFont(font);
        instructionLine.setFont(font);

        editorArea.setFont(font);
    }

    private void setUpFx(double width, double height, Font font) {
        setMinWidth(width);
        setMaxWidth(width);
        setMinHeight(height);
        setMaxHeight(height);

        double extraLineHeight = 20;

        String filename = "unnamed";
        int lineCount = 0;
        int currentLineIndex = 0;
        int currentColumnIndex = 0;

        infoLine = new InfoLine(width, extraLineHeight, filename, lineCount, currentLineIndex, currentColumnIndex);
        infoLine.setFont(font);

        instructionLine = new InstructionLine(width, extraLineHeight);
        instructionLine.setFont(font);

        editorArea = new TextArea();
        editorArea.setWrapText(true);
        editorArea.setFont(font);
        editorArea.setMaxWidth(width);
        editorArea.setMinWidth(width);
        editorArea.setMinHeight(height - (extraLineHeight * 2));
        editorArea.setMaxHeight(height - (extraLineHeight * 2));

        editorArea.getParagraphs().addListener((ListChangeListener<? super CharSequence>) c ->
                infoLine.updateLineCount(editorArea.getParagraphs().size()));

        clock = new Timeline(new KeyFrame(Duration.seconds(1), e ->
                infoLine.updateTime(dtfDate.format(LocalDateTime.now()))));
        clock.setCycleCount(Animation.INDEFINITE);

        getChildren().addAll(infoLine, editorArea, instructionLine);
    }

    private void setUpKeyListener() {
        editorArea.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (callBack != null) {
                switch (event.getCode()) {
                    case Q -> {
                        if (event.isControlDown()) {

                            callBack.quit();
                        }
                    }
                    case S -> {
                        if (event.isControlDown()) {
                            callBack.save(this.filename, editorArea.getText());
                            if (event.isShiftDown()) {
                                callBack.quit();
                            }
                        }
                    }
                }
            }
        });
    }

    private static class InstructionLine extends HBox {
        private final Text exit;
        private final Text save;
        private final Text saveAndExit;

        public InstructionLine(double width, double height) {
            setMinWidth(width);
            setMaxWidth(width);
            setMinHeight(height);
            setMaxHeight(height);
            getStyleClass().add("info-line");
            setAlignment(Pos.CENTER);

            this.exit = new Text("EXIT - CTRL+Q");
            this.exit.getStyleClass().add("info-line-text");
            this.save = new Text("|SAVE - CTRL+S");
            this.save.getStyleClass().add("info-line-text");
            this.saveAndExit = new Text("|EXIT&SAVE - CTRL+SHIFT+S");
            this.saveAndExit.getStyleClass().add("info-line-text");

            getChildren().addAll(this.exit, this.createSpacer(),
                    this.save, createSpacer(),
                    this.saveAndExit);
        }

        public void setFont(Font font) {
            this.exit.setFont(font);
            this.save.setFont(font);
            this.saveAndExit.setFont(font);
        }

        private Node createSpacer() {
            Region region = new Region();
            HBox.setHgrow(region, Priority.ALWAYS);
            return region;
        }
    }

    private static class InfoLine extends HBox {
        private final Text filename;
        private final Text lineCount;
        private final Text cursorPosition;
        private final Text time;

        public InfoLine(double width, double height, String filename, int lineCount, int row, int col) {
            setMinWidth(width);
            setMaxWidth(width);
            setMinHeight(height);
            setMaxHeight(height);
            getStyleClass().add("info-line");
            setAlignment(Pos.CENTER);

            this.filename = new Text(filename);
            this.filename.getStyleClass().add("info-line-text");
            this.lineCount = new Text(lineCount + " Ln");
            this.lineCount.getStyleClass().add("info-line-text");
            this.cursorPosition = new Text(row + ":" + col);
            this.cursorPosition.getStyleClass().add("info-line-text");

            this.time = new Text();
            this.time.getStyleClass().add("info-line-text");

            getChildren().addAll(this.filename, createSpacer(),
                    this.lineCount, createSpacer(),
                    this.cursorPosition, createSpacer(),
                    this.time);
        }

        public void setFont(Font font) {
            filename.setFont(font);
            lineCount.setFont(font);
            cursorPosition.setFont(font);
            time.setFont(font);
        }

        public void updateFilename(String filename) {
            this.filename.setText(filename);
        }

        public void updateLineCount(int lineCount) {
            this.lineCount.setText(lineCount + " Ln");
        }

        public void updateTime(String time) {
            this.time.setText(time);
        }

        private Node createSpacer() {
            Region region = new Region();
            HBox.setHgrow(region, Priority.ALWAYS);
            return region;
        }
    }

}
