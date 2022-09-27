package os;

import ui.TerminalLauncher;

import java.util.concurrent.BlockingQueue;

public class OS {

    /*
        TODO:
            - [x] handle events from window
            - [ ] send output back to window
            - [ ] input dialog
            - [ ] handle shut down
     */

    public static void main(String[] args) {
        BlockingQueue<String> eventQueue = TerminalLauncher.launchWindow(args);

        boolean running = true;

        try {
            // TODO: CLEANUP!!
            while (running) {
                String line = eventQueue.take();

                System.out.println(Thread.currentThread().getName() + " > " + line);

                if(line.equals("kill")) {
                    running = false;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        TerminalLauncher.destroyWindow();
    }

}
