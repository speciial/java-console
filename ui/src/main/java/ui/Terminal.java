package ui;

import java.util.concurrent.BlockingQueue;

public class Terminal {

    private static CustomTerminalComponent terminalComponent;

    private static BlockingQueue<TerminalEvent> eventQueue;

    public static TerminalInputStream in;
    public static TerminalOutputStream out;

    public static void setColor() {
        terminalComponent.setColor();
    }

    public static void setAutoCompleteString(String match) {
        terminalComponent.setAutoCompleteString(match);
    }

    public static TerminalEvent takeEvent() throws InterruptedException {
        return eventQueue.take();
    }

    public static void flushEventQueue() {
        eventQueue.clear();
    }

    public static void setIn(TerminalInputStream _in) {
        in = _in;
    }

    public static void setOut(TerminalOutputStream _out) {
        out = _out;
    }

    // =================================================================================================================
    // STUFF THAT WILL BE MOVED LATER ON

    protected static void commitEvent(TerminalEvent event) {
        eventQueue.add(event);
    }

    protected static void setEventQueue(BlockingQueue<TerminalEvent> _event) {
        eventQueue = _event;
    }

    protected static void setTerminalComponent(CustomTerminalComponent _terminalComponent) {
        terminalComponent = _terminalComponent;
        out.setOutputComponent(terminalComponent);
    }

    // =================================================================================================================
}
