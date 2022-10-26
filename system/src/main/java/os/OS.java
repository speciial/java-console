package os;

import ui.*;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class OS implements Runnable {

    public static final FileSystem FS = new FileSystem();

    @Override
    public void run() {
        System.out.println("Starting Main Thread...");

        boolean running = true;
        do {
            try {
                TerminalEvent event = Terminal.takeEvent();
                switch (event.type) {
                    case STARTUP -> {
                        Terminal.flushEventQueue();
                        Terminal.out.print(FS.getDirectoryString() + " >");
                    }
                    case NEW_LINE -> {
                        String line = Terminal.in.readLine();
                        String[] tokens = line.split("\\s+|\\t+");

                        if (tokens.length > 0) {
                            switch (tokens[0].toLowerCase()) {
                                case "cd" -> {
                                    Terminal.out.println("CD COMMAND");
                                    if(tokens.length > 1) {
                                        CommandHandler.handleCD(tokens[1]);
                                    }
                                }
                                case "ls" -> {
                                    Terminal.out.println("LS COMMAND");
                                    CommandHandler.handleLS();
                                }
                                case "convo" -> {
                                    Terminal.out.println("CONVO COMMAND");
                                    CommandHandler.handleCONVO();
                                }
                                case "exit" -> {
                                    Terminal.out.println("EXIT COMMAND");
                                    TerminalApplication.terminate();
                                    running = false;
                                }
                            }
                        }

                        // This is a fix to clear all newly generated new_line events during the life cycle of a
                        // command. In the future we may want to only clear the new_lines because commands might
                        // generate new events.
                        Terminal.flushEventQueue();
                        Terminal.out.print("\n" + FS.getDirectoryString() + " >");
                    }
                    case KEY_TAB -> {
                        List<String> match = FS.findAutoCompleteMatch(event.content);
                        if (!match.isEmpty()) {
                            Terminal.setAutoCompleteString(match.get(0));
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
