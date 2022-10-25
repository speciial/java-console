package os;

import ui.*;

import java.util.concurrent.LinkedBlockingQueue;

public class OS implements Runnable {

    String[] completableStrings = new String[]{
            "hello",
            "help",
            "c:/dev/code"
    };

    private String findAutoCompleteMatch(String toComplete) {
        String result = null;
        for (String item : completableStrings) {
            if (item.startsWith(toComplete)) {
                result = item;
                break;
            }
        }
        return result;
    }

    @Override
    public void run() {
        System.out.println("Starting Main Thread...");

        boolean running = true;
        do {
            try {
                TerminalEvent event = Terminal.takeEvent();
                switch (event.type) {
                    case NEW_LINE -> {
                        System.out.println("Waiting for input...");
                        String line = Terminal.in.readLine();

                        if (line.equals("hello")) {
                            String newLine = Terminal.in.readLine();
                            Terminal.out.println("fk u");
                            Terminal.out.println(newLine);
                        }

                        System.out.println("Output: " + line);
                        Terminal.out.println(line);

                        // This is a fix to clear all newly generated new_line events during the life cycle of a
                        // command. In the future we may want to only clear the new_lines because commands might
                        // generate new events.
                        Terminal.flushEventQueue();
                    }
                    case KEY_TAB -> {
                        String match = findAutoCompleteMatch(event.content);
                        if (match != null) {
                            Terminal.setAutoCompleteString(match);
                        }
                    }
                    case WINDOW_CLOSE, KEY_ESC -> running = false;
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (running);
    }

    public static void main(String[] args) {
        TerminalApplication.configure(new LinkedBlockingQueue<>(), new TerminalInputStream(), new TerminalOutputStream());

        Thread uiThread = new Thread(new TerminalApplication());
        uiThread.start();

        Thread mainThread = new Thread(new OS());
        mainThread.start();
    }

}
