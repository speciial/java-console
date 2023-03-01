package hsos;

import hsos.io.TerminalInputStream;
import hsos.io.TerminalOutputStream;
import hsos.ui.ApplicationEventQueue;
import hsos.ui.ApplicationWindow;

import java.io.IOException;

public class HSOSLauncher {

    public static void main(String[] args) throws IOException {
        // ==============================================================
        // ==================== Initialize Libraries ====================
        // ==============================================================

        // UI
        ApplicationEventQueue eventQueue = new ApplicationEventQueue();
        TerminalInputStream inputStream = new TerminalInputStream();
        TerminalOutputStream outputStream = new TerminalOutputStream();
        ApplicationWindow.configureApplicationWindow(eventQueue, inputStream, outputStream);

        // ==============================================================
        // ====================    Initialize OS     ====================
        // ==============================================================
        HSOS os = new HSOS();
        HSOS.out = outputStream;
        HSOS.in = inputStream;
        HSOS.setEventQueue(eventQueue);
        HSOS.boot();

        // ==============================================================
        // ====================      Launch OS       ====================
        // ==============================================================
        Thread uiThread = new Thread(new ApplicationWindow());
        uiThread.start();
        Thread mainThread = new Thread(os);
        mainThread.start();
    }
}
