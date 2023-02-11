package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ApplicationWindow extends Application implements Runnable {

    private static final String WINDOW_TITLE = "hsOS - Version 1.0";

    private static final double WINDOW_WIDTH = 860;
    private static final double WINDOW_HEIGHT = 645;

    private static final double WINDOW_CONTENT_WIDTH = 602;
    private static final double WINDOW_CONTENT_HEIGHT = 451.5;

    // Container Components
    private Scene windowScene;
    private StackPane rootContainer;

    // Display Components
    private TextComponent textComponent;

    @Override
    public void run() {
        Application.launch();
    }

    @Override
    public void stop() {
    }

    @Override
    public void start(Stage stage) {
        textComponent = new TextComponent(WINDOW_CONTENT_WIDTH, WINDOW_CONTENT_HEIGHT);
        rootContainer = new StackPane(textComponent);
        windowScene = new Scene(rootContainer, WINDOW_WIDTH, WINDOW_HEIGHT);

        applyDefaultStyles();

        stage.setTitle(WINDOW_TITLE);
        stage.setScene(windowScene);
        stage.setResizable(false);
        stage.show();
    }

    private void applyDefaultStyles() {
        // TODO(christian): apparently you can define variables in the css yourself. this is useful if you want to
        //                  later change these through code because there is no way to use selectors with inline css.

        rootContainer.setStyle(
                "-fx-background-color: rgba(255, 0, 255, 1)"
        );

        textComponent.setStyle(
                "-fx-background-color: rgba(0, 0, 0, 0)"
        );
    }

}
