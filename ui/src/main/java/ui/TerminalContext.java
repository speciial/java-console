package ui;

import javafx.application.Platform;
import ui.event.TerminalEvent;
import ui.io.TerminalInputStream;
import ui.io.TerminalOutputStream;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public final class TerminalContext {

    public static TerminalInputStream in;
    public static TerminalOutputStream out;

    public static BlockingQueue<TerminalEvent> eventQueue;

    private TerminalContext() {
    }

    static void init() {
        eventQueue = new LinkedBlockingQueue<>();
    }

    static void configureIO(CustomTerminalComponent terminalComponent) {
        in = new TerminalInputStream();
        out = new TerminalOutputStream(terminalComponent);
    }

    public static boolean isUIThread() {
        return Platform.isFxApplicationThread();
    }

}
