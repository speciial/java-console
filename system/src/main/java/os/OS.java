package os;

import ui.TerminalLauncher;

import java.util.concurrent.BlockingQueue;

public class OS {

    /*
        TODO:
            - [x] handle events from window
            - [ ] send output back to window
            - [ ] input dialog
     */

    public static void main(String[] args) {
        BlockingQueue<String> eventQueue = TerminalLauncher.launchWindow(args);

        try {
            // TODO: CLEANUP!!
            while (true) {
                String line = eventQueue.take();

                System.out.println(Thread.currentThread().getName() + " > " + line);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
