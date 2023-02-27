package os;

import os.io.TerminalInputStream;
import os.io.TerminalOutputStream;
import os.programs.HSEdit;
import ui.ApplicationEvent;
import ui.ApplicationEventQueue;

public class HSOS implements Runnable {

    // TODO(christian): The only thing missing from this now is something for controlling the UI state, like changing
    //                  to the editor view or altering the active input line

    public ApplicationEventQueue eventQueue;

    public static TerminalOutputStream out;
    public static TerminalInputStream in;

    @Override
    public void run() {
        boolean running = true;

        while (running) {
            try {
                ApplicationEvent event = eventQueue.getNextEvent();
                switch (event) {
                    case UI_STARTUP -> out.print("c:/dev" + ">");
                    case UI_SHUTDOWN -> running = false;
                    case UI_KEY_ESC -> {
                    }
                    case TERMINAL_KEY_TAB -> out.println("TAB has been pressed");
                    case TERMINAL_KEY_ENTER -> {
                        String line = in.readLine();
                        if (line.equals("hsedit")) {
                            HSEdit edit = new HSEdit();
                            edit.execute(null);
                        }

                        out.print("c:/dev" + ">");
                    }
                }


                eventQueue.flushEventQueue();
            } catch (InterruptedException e) {
                System.out.println("An error during system initialization occurred!");
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

}
