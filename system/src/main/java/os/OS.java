package os;

import ui.TerminalApplication;
import ui.TerminalContext;
import ui.event.EventType;
import ui.event.TerminalEvent;

import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

public class OS {

    /*
        TODO:
            - [x] handle events from window
            - [ ] send output back to window
            - [ ] input dialog
            - [ ] handle shut down
     */

    public static void main(String[] args) {
        // BOOT THE OS

        // LOAD THE WINDOW
        TerminalApplication.showTerminal();

        boolean running = true;

        // RUN THE APPLICATION
        try {
            while (running) {
                // WAIT FOR A NEW EVENT
                TerminalEvent event = TerminalContext.eventQueue.take();

                // PROCESS THE EVENT
                System.out.println(Thread.currentThread().getName() + " > " + event.getContent());

                if(event.getEventType().equals(EventType.NEW_LINE)){
                    TerminalContext.out.println(event.getContent());

                    String someNewLine = TerminalContext.in.nextLine();
                    TerminalContext.out.println(someNewLine);
                }
                if(event.getEventType().equals(EventType.WINDOW_CLOSE)) {
                    running = false;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // END THE APPLICATION AND DESTROY THE WINDOW
        TerminalApplication.destroyWindow();
    }

}
