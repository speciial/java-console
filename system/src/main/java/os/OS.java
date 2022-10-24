package os;

import ui.*;

import java.util.concurrent.LinkedBlockingQueue;

public class OS implements Runnable{

    private static Thread uiThread;
    private static Thread mainThread;

    String[] completableStrings = new String[]{
            "hello",
            "help",
            "c:/dev/code"
    };

    private String findAutoCompleteMatch(String toComplete) {
        String result = null;
        for (String item: completableStrings) {
            if(item.startsWith(toComplete)) {
                result = item;
                break;
            }
        }
        return result;
    }

    // How do we react to window events such as closing the window or any kind of keyboard event?
    //
    // In a best case scenario the application would stop waiting for input and process the incoming event.
    @Override
    public void run() {
        System.out.println("Starting Main Thread...");

        boolean running = true;
        do {
            try {
                TerminalEvent event = Terminal.takeEvent();
                switch (event.type) {
                    // TODO: should we rename this to ENTER_COMMAND?
                    case NEW_LINE -> {
                        // TODO: command handling
                        System.out.println("Waiting for input...");
                        String line = Terminal.in.readLine();

                        if(line.equals("hello")) {
                            String newLine = Terminal.in.readLine();
                            Terminal.out.println("fk u");
                            Terminal.out.println(newLine);
                        }

                        System.out.println("Output: " + line);
                        Terminal.out.println(line);

                        // This is a fix to clear all newly generated new_line events during the life cycle of a command. In
                        // the future we may want to only clear the new_lines because commands might generate new events.
                        Terminal.flushEventQueue();
                    }
                    case KEY_TAB -> {
                        // TODO: add auto-complete
                        String match = findAutoCompleteMatch(event.content);
                        if(match != null) {
                            Terminal.setAutoCompleteString(match);
                        }
                    }
                    case WINDOW_CLOSE, KEY_ESC -> {
                        // this exits the loop in the main thread
                        System.out.println("EXIT");

                        running = false;

                        uiThread.join();
                        mainThread.join();
                    }

                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (running);
    }

    public static void main(String[] args) {
        TerminalApplication.configure(new LinkedBlockingQueue<>(), new TerminalInputStream(), new TerminalOutputStream());

        // TODO: make sure the application is fully running before the OS is booting up and trying to print things
        // TODO: stop all threads properly so the application closes
        uiThread = new Thread(new TerminalApplication());
        uiThread.start();

        mainThread = new Thread(new OS());
        mainThread.start();
    }

}
