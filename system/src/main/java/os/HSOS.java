package os;

import os.io.TerminalInputStream;
import os.io.TerminalOutputStream;
import ui.ApplicationEvent;
import ui.ApplicationEventQueue;
import ui.ApplicationSettings;

public class HSOS implements Runnable {

    // TODO(christian): The only thing missing from this now is something for controlling the UI state, like changing
    //                  to the editor view or altering the active input line

    public ApplicationEventQueue eventQueue;

    public TerminalOutputStream out;
    public TerminalInputStream in;

    @Override
    public void run() {
        boolean running = true;

        int fontSize = 14;

        while (running) {
            System.out.println("Running Loop");

            try {
                ApplicationEvent event = eventQueue.getNextEvent();
                switch (event) {
                    case UI_STARTUP -> out.print("c:/dev" + ">");
                    case UI_SHUTDOWN -> running = false;
                    case UI_KEY_ESC -> {
                        ApplicationSettings.getInstance().setWindowFont("Liberation Mono Bold", fontSize);

                        fontSize++;
                        if (fontSize > 20) {
                            fontSize = 12;
                        }
                    }
                    case TERMINAL_KEY_TAB -> out.println("TAB has been pressed");
                    case TERMINAL_KEY_ENTER -> {
                        out.println("ENTER has been pressed");

                        String line = in.readLine();
                        out.println("The following has been typed: " + line);

                        out.print("Enter some text here: ");
                        String newLine = in.readLine();
                        out.println("Thank you for typing the following: " + newLine);

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
