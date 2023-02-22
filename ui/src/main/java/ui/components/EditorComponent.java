package ui.components;

import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import ui.ApplicationEvent;

import java.util.concurrent.BlockingQueue;

public class EditorComponent extends VBox {

    private static final String CONTROL_INSTRUCTIONS = "TODO: REPLACE!";

    private final BlockingQueue<ApplicationEvent> eventQueue;

    private TextArea editorArea;
    private StackPane instructionsRegion;
    private Text instructionsLabel;

    public EditorComponent(BlockingQueue<ApplicationEvent> eventQueue, double width, double height, Font font) {
        this.eventQueue = eventQueue;

        setUpFx(width, height, font);
        setUpKeyListener();
    }

    private void setUpFx(double width, double height, Font font) {
        setMaxWidth(width);
        setMaxHeight(height);

        instructionsLabel = new Text(CONTROL_INSTRUCTIONS);
        instructionsLabel.getStyleClass().add("text");
        instructionsLabel.setFont(font);
        double instructionsHeight = instructionsLabel.getLayoutBounds().getHeight();

        instructionsRegion = new StackPane(instructionsLabel);
        instructionsRegion.getStyleClass().add("stack-pane");
        instructionsRegion.setMinWidth(width);
        instructionsRegion.setMaxWidth(width);

        editorArea = new TextArea();
        editorArea.setWrapText(true);
        editorArea.setFont(font);
        editorArea.setMaxWidth(width);
        editorArea.setMinWidth(width);
        editorArea.setMinHeight(height - instructionsHeight);
        editorArea.setMaxHeight(height - instructionsHeight);

        getChildren().addAll(editorArea, instructionsRegion);
    }

    private void setUpKeyListener() {
    }
}
