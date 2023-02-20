package ui;

import ui.components.EditorComponent;
import ui.components.TerminalComponent;

import java.util.concurrent.BlockingQueue;

public class WindowContext {

    private static TerminalComponent terminalComponent;
    private static EditorComponent editorComponent;

    private static BlockingQueue<WindowEvent> eventQueue;

    private WindowContext() {
    }

    public static void init(BlockingQueue<WindowEvent> queue) {
        eventQueue = queue;
    }

    public static BlockingQueue<WindowEvent> getEventQueue() {
        return eventQueue;
    }

    public static void print(String text) {
        terminalComponent.appendToTextBuffer(text);
    }

    // ============================================= INTERNAL SETUP =============================================
    protected static void addToEventQueue(WindowEvent event) {
        eventQueue.add(event);
    }

    protected static void setTerminalComponent(TerminalComponent _terminalComponent) {
        terminalComponent = _terminalComponent;
    }

    protected static void setEditorComponent(EditorComponent _editorEditorComponent) {
        editorComponent = _editorEditorComponent;
    }
    // ==========================================================================================================
}
