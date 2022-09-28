package ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import ui.event.EventType;
import ui.event.TerminalEvent;

public class TerminalApplication extends Application {

    public static void showTerminal() {
        TerminalContext.init();
        new Thread(() -> Application.launch(TerminalApplication.class)).start();
    }

    public static void destroyWindow() {
        Platform.exit();
    }

    @Override
    public void start(Stage stage) {
        CustomTerminalComponent terminal = new CustomTerminalComponent();
        TerminalContext.configureIO(terminal);

        StackPane root = new StackPane();
        root.getChildren().add(terminal);

        Scene scene = new Scene(root, 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        TerminalContext.eventQueue.put(new TerminalEvent(EventType.WINDOW_CLOSE, null));
    }
}
