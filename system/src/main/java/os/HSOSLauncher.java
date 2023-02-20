package os;

import os.io.TerminalOutputStream;
import ui.ApplicationWindow;
import ui.WindowContext;

import java.util.concurrent.ArrayBlockingQueue;

public class HSOSLauncher {

    public static void main(String[] args) {
        WindowContext.init(new ArrayBlockingQueue<>(1024));

        HSOS os = new HSOS();
        os.out = new TerminalOutputStream();

        Thread uiThread = new Thread(new ApplicationWindow());
        uiThread.start();

        Thread mainThread = new Thread(os);
        mainThread.start();
    }
}
