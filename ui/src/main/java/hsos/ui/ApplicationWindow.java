package hsos.ui;

import hsos.ui.components.WindowComponent;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.concurrent.BlockingQueue;

public class ApplicationWindow extends Application implements Runnable {

    // Reference to the ui-wide event queue
    private static BlockingQueue<ApplicationEvent> eventQueue;

    // Input and output streams for the os
    private static ApplicationInputStream inputStream;
    private static ApplicationOutputStream outputStream;

    public static void configureApplicationWindow(ApplicationEventQueue _eventQueue,
                                                  ApplicationInputStream _inputStream,
                                                  ApplicationOutputStream _outputStream) {
        eventQueue = _eventQueue.getInternalQueue();
        inputStream = _inputStream;
        outputStream = _outputStream;
    }

    @Override
    public void run() {
        Application.launch();
    }

    @Override
    public void start(Stage stage) {
        WindowComponent windowComponent = new WindowComponent(stage, eventQueue, inputStream);
        ApplicationController.getInstance().setComponents(windowComponent);

        outputStream.setOutputComponent(windowComponent.getTerminalComponent());
        eventQueue.add(ApplicationEvent.UI_STARTUP);

        windowComponent.showWindow();
    }

    @Override
    public void stop() {
        eventQueue.add(ApplicationEvent.UI_SHUTDOWN);
    }

}
