package hsos.ui.components;

import hsos.ui.ApplicationEvent;
import hsos.ui.ApplicationInputStream;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;

public class WindowComponent {

    private static final String WINDOW_TITLE = "hsOS - Version 1.0";

    private static final double WINDOW_WIDTH = 860;
    private static final double WINDOW_HEIGHT = 645;

    private static final double WINDOW_CONTENT_WIDTH = 602;
    private static final double WINDOW_CONTENT_HEIGHT = 451.5;

    private static Font WINDOW_FONT;

    private final Stage window;
    private final BlockingQueue<ApplicationEvent> eventQueue;
    private final ApplicationInputStream inputStream;

    private Scene windowScene;
    private StackPane rootContainer;

    private TerminalComponent terminalComponent;
    private EditorComponent editorComponent;

    public WindowComponent(Stage window,
                           BlockingQueue<ApplicationEvent> eventQueue,
                           ApplicationInputStream inputStream) {
        this.window = window;
        this.eventQueue = eventQueue;
        this.inputStream = inputStream;

        setUpFx();
        setUpKeyListener();

        window.setTitle(WINDOW_TITLE);
        window.setScene(windowScene);
        window.setResizable(false);
    }

    public void showWindow() {
        window.show();
    }

    public void setFont(Font font) {
        terminalComponent.setFont(font);
        editorComponent.setFont(font);
    }

    public void setTerminalActive() {
        rootContainer.getChildren().clear();

        editorComponent.disable();
        rootContainer.getChildren().add(terminalComponent);
    }

    public void setEditorActive() {
        rootContainer.getChildren().clear();

        editorComponent.enable();
        rootContainer.getChildren().add(editorComponent);
    }

    public TerminalComponent getTerminalComponent() {
        return terminalComponent;
    }

    public EditorComponent getEditorComponent() {
        return editorComponent;
    }

    private void setUpKeyListener() {
        window.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()) {
                case ESCAPE -> eventQueue.add(ApplicationEvent.UI_KEY_ESC);
                case ENTER -> System.out.println("DOES ENTER GET CONSUMED?");
            }
        });
    }

    private void setUpFx() {
        // Load fonts
        loadFontFiles();

        // Component initialization
        terminalComponent = new TerminalComponent(eventQueue, inputStream, WINDOW_CONTENT_WIDTH, WINDOW_CONTENT_HEIGHT, WINDOW_FONT);
        editorComponent = new EditorComponent(WINDOW_CONTENT_WIDTH, WINDOW_CONTENT_HEIGHT, WINDOW_FONT);

        rootContainer = new StackPane(terminalComponent);
        windowScene = new Scene(rootContainer, WINDOW_WIDTH, WINDOW_HEIGHT);

        applyDefaultStyles();
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
