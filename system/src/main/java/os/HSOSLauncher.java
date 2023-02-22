package os;

import os.io.TerminalInputStream;
import os.io.TerminalOutputStream;
import ui.ApplicationEventQueue;
import ui.ApplicationWindow;

public class HSOSLauncher {

    public static void main(String[] args) {
        // ==============================================================
        // ==================== Initialize Libraries ====================
        // ==============================================================

        // UI
        ApplicationEventQueue eventQueue = new ApplicationEventQueue();
        TerminalInputStream inputStream = new TerminalInputStream();
        TerminalOutputStream outputStream = new TerminalOutputStream();
        ApplicationWindow.configureApplicationWindow(eventQueue, inputStream, outputStream);

        // TODO(christian): File System
        // TODO(christian): Settings System
        // TODO(christian): Network System

        // ==============================================================
        // ====================    Initialize OS     ====================
        // ==============================================================
        HSOS os = new HSOS();
        os.eventQueue = eventQueue;
        os.out = outputStream;
        os.in = inputStream;

        // ==============================================================
        // ====================      Launch OS       ====================
        // ==============================================================
        Thread uiThread = new Thread(new ApplicationWindow());
        uiThread.start();
        Thread mainThread = new Thread(os);
        mainThread.start();
    }
}
