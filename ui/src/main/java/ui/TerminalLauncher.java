package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TerminalLauncher extends Application {

    private static BlockingQueue<String> eventQueue;

    // TODO: Create a WindowContext that can be shared between threads to access terminal functions such as print, read,
    //       etc. This shouldn't cause any synchronization problems since the ui doesn't write data except when putting
    //       stuff to the eventQueue.
    public static BlockingQueue<String> launchWindow(String[] args) {
        eventQueue = new LinkedBlockingQueue<>();
        new Thread(() -> Application.launch(TerminalLauncher.class, args)).start();

        return eventQueue;
    }

    @Override
    public void start(Stage stage) {
        StackPane root = new StackPane();

        CustomTerminal terminal = new CustomTerminal(eventQueue);
        root.getChildren().add(terminal);

        // TEST PRINT
        terminal.println("hello, world!");

        Scene scene = new Scene(root, 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
}
