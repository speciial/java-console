package os;

import os.io.TerminalOutputStream;
import ui.WindowContext;
import ui.WindowEvent;

public class HSOS implements Runnable {

    public TerminalOutputStream out;

    @Override
    public void run() {
        boolean running = true;

        while (running) {
            try {
                WindowEvent event = WindowContext.getEventQueue().take();
                switch (event) {
                    case UI_STARTUP -> out.print("c:/dev" + " >");
                    case UI_SHUTDOWN -> running = false;
                    case UI_KEY_ESC -> out.println("ESCAPE has been pressed");
                    case UI_KEY_TAB -> out.println("TAB has been pressed");
                }

            } catch (InterruptedException e) {
                System.out.println("An error during system initialization occurred!");
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

}
