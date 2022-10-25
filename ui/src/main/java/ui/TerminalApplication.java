package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.concurrent.BlockingQueue;

public class TerminalApplication extends Application implements Runnable{

    public static void configure(BlockingQueue<TerminalEvent> event, TerminalInputStream in, TerminalOutputStream out) {
        Terminal.setEventQueue(event);
        Terminal.setIn(in);
        Terminal.setOut(out);
    }

    // It is not strictly necessary to use a Runnable here and start the JFX application through a new thread since the
    // application is starting a separate ui thread anyway. This is purely for consistency and clear separation between
    // os and terminal.
    @Override
    public void run() {
        System.out.println("Starting UI Thread...");
        Application.launch();
    }

    @Override
    public void stop() {
        Terminal.commitEvent(new TerminalEvent(TerminalEventType.WINDOW_CLOSE, null));
    }

    @Override
    public void start(Stage stage) {
        CustomTerminalComponent terminalComponent = new CustomTerminalComponent();
        Terminal.setTerminalComponent(terminalComponent);

        StackPane root = new StackPane();
        root.getChildren().add(terminalComponent);

        Scene scene = new Scene(root, 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
}
