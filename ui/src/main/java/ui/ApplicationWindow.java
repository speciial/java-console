package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import ui.components.EditorComponent;
import ui.components.TerminalComponent;

import java.util.Objects;

public class ApplicationWindow extends Application implements Runnable {

    private static final String WINDOW_TITLE = "hsOS - Version 1.0";

    private static final double WINDOW_WIDTH = 860;
    private static final double WINDOW_HEIGHT = 645;

    private static final double WINDOW_CONTENT_WIDTH = 602;
    private static final double WINDOW_CONTENT_HEIGHT = 451.5;

    private static Font WINDOW_FONT;

    // Container Components
    private Scene windowScene;
    private StackPane rootContainer;

    // Display Components
    private TerminalComponent terminalComponent;
    private EditorComponent editorComponent;

    @Override
    public void run() {
        Application.launch();
    }

    @Override
    public void init() {
        loadFontFiles();
    }

    @Override
    public void start(Stage stage) {
        // Component initialization
        terminalComponent = new TerminalComponent(WINDOW_CONTENT_WIDTH, WINDOW_CONTENT_HEIGHT, WINDOW_FONT);
        editorComponent = new EditorComponent(WINDOW_CONTENT_WIDTH, WINDOW_CONTENT_HEIGHT, WINDOW_FONT);

        rootContainer = new StackPane(terminalComponent);
        windowScene = new Scene(rootContainer, WINDOW_WIDTH, WINDOW_HEIGHT);

        applyDefaultStyles();

        // Initialization complete
        WindowContext.setTerminalComponent(terminalComponent);
        WindowContext.setEditorComponent(editorComponent);
        WindowContext.addToEventQueue(WindowEvent.UI_STARTUP);

        stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()) {
                case TAB -> WindowContext.addToEventQueue(WindowEvent.UI_KEY_TAB);
                case ESCAPE -> WindowContext.addToEventQueue(WindowEvent.UI_KEY_ESC);
                case ENTER -> System.out.println("DOES ENTER GET CONSUMED?");
            }
        });

        stage.setTitle(WINDOW_TITLE);
        stage.setScene(windowScene);
        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void stop() {
        WindowContext.addToEventQueue(WindowEvent.UI_SHUTDOWN);
    }

    private void loadFontFiles() {
        // We might want to load the other font file, too.
        Font.loadFont(Objects.requireNonNull(getClass().getResource("/fonts/LiberationMono-Regular.ttf")).toExternalForm(), 12);
        Font.loadFont(Objects.requireNonNull(getClass().getResource("/fonts/LiberationMono-Bold.ttf")).toExternalForm(), 12);

        WINDOW_FONT = Font.font("Liberation Mono Bold", 14);
    }

    private void applyDefaultStyles() {
        // Outside its individual styling, the root container also defines the lookup colors for other ui elements.
        // With this, we can set colors of child components and especially of sub-structures through code at runtime.
        // Inline CSS in code with a call to setStyles does not allow the modification of sub-structures but with the
        // root container, we can update the defined lookup colors like so:
        //
        //    rootContainer.setStyle("-fx-hs-content-background: linear-gradient(to bottom right, red, black)");
        //
        // The currently supported lookup colors are:
        //
        //    -fx-hs-container-background: rgba(80, 80, 80, 1);
        //    -fx-hs-content-background: rgba(160, 160, 160, 1);
        //    -fx-hs-font-color: rgba(30, 30, 30, 1);
        rootContainer.getStyleClass().add("root");
        terminalComponent.getStyleClass().add("terminal-component");
        editorComponent.getStyleClass().add("editor-component");

        windowScene.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("/css/default-styles.css")).toExternalForm());
    }

}
