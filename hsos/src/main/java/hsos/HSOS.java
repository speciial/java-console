package hsos;

import hsos.commands.*;
import hsos.files.FileSystem;
import hsos.io.TerminalInputStream;
import hsos.io.TerminalOutputStream;
import hsos.network.NetworkSystem;
import hsos.settings.SettingsSystem;
import hsos.ui.ApplicationController;
import hsos.ui.ApplicationEvent;
import hsos.ui.ApplicationEventQueue;
import hsos.user.UserSystem;

import java.io.IOException;
import java.util.ArrayList;

public class HSOS implements Runnable {

    // TODO(christian): The only thing missing from this now is something for controlling the UI state, like changing
    //                  to the editor view or altering the active input line
    // TODO(christian): Build a *tiny* game engine to have the pong game run as a program!
    // TODO(christian): Move Pair and other data structures into a package (what to call the package?)
    // TODO(christian): The code that has been written in terminal-rewrite has to many interdependencies and needs to
    //                  be simplified before we can add it to this codebase!

    private static ApplicationEventQueue eventQueue;

    public static TerminalOutputStream out;
    public static TerminalInputStream in;

    private static final String FS_PATH = "data/filesystem.json";
    public static FileSystem fileSystem;

    private static final String US_PATH = "data/usersystem.json";
    public static UserSystem userSystem;

    private static final String NS_PATH = "data/networksystem.json";
    public static NetworkSystem networkSystem;

    public static SettingsSystem settingsSystem;

    private final CommandHistory commandHistory;
    private final CommandList commandList;

    public HSOS() {
        commandHistory = new CommandHistory(1024);
        commandList = CommandList.getInstance();

        commandList.registerCommand("hsedit", HSEditCommand.class);
        commandList.registerCommand("adduser", AddUserCommand.class);
        commandList.registerCommand("cd", CDCommand.class);
        commandList.registerCommand("cwd", CWDCommand.class);
        commandList.registerCommand("exit", ExitCommand.class);
        commandList.registerCommand("help", HelpCommand.class);
        commandList.registerCommand("listuser", ListUserCommand.class);
        commandList.registerCommand("ls", LSCommand.class);
        commandList.registerCommand("mkdir", MakeDirectoryCommand.class);
        commandList.registerCommand("mkfile", MakeFileCommand.class);
        commandList.registerCommand("more", MoreCommand.class);
        commandList.registerCommand("rm", RMCommand.class);
        commandList.registerCommand("settings", SettingsCommand.class);
        commandList.registerCommand("switchuser", SwitchUserCommand.class);
    }

    @Override
    public void run() {
        boolean running = true;

        while (running) {
            try {
                ApplicationEvent event = eventQueue.getNextEvent();
                switch (event) {
                    case UI_STARTUP -> out.print(fileSystem.getCwd() + ">");
                    case UI_SHUTDOWN -> {
                        shutdown();
                        running = false;
                    }
                    case TERMINAL_KEY_UP -> {
                        String prevCommand = commandHistory.getPrev();
                        ApplicationController.getInstance().setTerminalActiveLine(prevCommand);

                        // TODO(christian): implement editing of the active line!
                    }
                    case TERMINAL_KEY_DOWN -> {
                        String nextCommand = commandHistory.getNext();
                        ApplicationController.getInstance().setTerminalActiveLine(nextCommand);

                        // TODO(christian): implement editing of the active line!
                    }
                    case TERMINAL_KEY_TAB -> {
                        String currentActiveLine = ApplicationController.getInstance().getTerminalActiveLine();
                        int caretIndex = ApplicationController.getInstance().getTerminalCaretLineIndex();

                        // TODO(christian): implement auto-complete
                    }
                    case TERMINAL_KEY_ENTER -> {
                        String line = in.readLine();
                        commandHistory.commitCommand(line);

                        ParsedCommand command = parseCommandLine(line);
                        commandList.execute(command.command, command.args);

                        out.print(fileSystem.getCwd() + ">");
                    }
                }

                eventQueue.flushEventQueue();
            } catch (InterruptedException e) {
                System.out.println("An error during system initialization occurred!");
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public static void setEventQueue(ApplicationEventQueue _eventQueue) {
        eventQueue = _eventQueue;
    }

    public static void boot() throws IOException {
        fileSystem = FileSystem.loadFromFile(FS_PATH);
        userSystem = UserSystem.loadFromFile(US_PATH);
        settingsSystem = new SettingsSystem();

        // networkSystem = NetworkSystem.loadFromFile("");
    }

    public static void shutdown() {
        try {
            FileSystem.saveToFile(FS_PATH, fileSystem);
            UserSystem.saveToFile(US_PATH, userSystem);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Methode zum Auswerten des eingegebenen Befehls.
     *
     * @param commandLine Die Eingabe des Nutzers.
     * @return Liste mit Befehl und Argumenten.
     */
    private ParsedCommand parseCommandLine(String commandLine) {
        ParsedCommand result = new ParsedCommand();

        // Remove unnecessary white spaces and convert to lower case
        char[] chars = commandLine.toLowerCase()
                .replace('\n', ' ')
                .replace('\t', ' ')
                .trim()
                .toCharArray();

        ArrayList<String> tokenList = new ArrayList<>();
        StringBuilder currentToken = new StringBuilder();
        for (int charIndex = 0; charIndex < chars.length; charIndex++) {
            switch (chars[charIndex]) {
                case ' ' -> {
                    tokenList.add(currentToken.toString());
                    currentToken = new StringBuilder();
                }
                case '\"' -> {
                    // Find the end of the String
                    int stringWithWhitespaceIndex;
                    for (stringWithWhitespaceIndex = charIndex + 1;
                         stringWithWhitespaceIndex < chars.length;
                         stringWithWhitespaceIndex++) {
                        if (chars[stringWithWhitespaceIndex] == '\"') {
                            break;
                        }
                    }

                    // Insert as full token
                    int stringLength = (stringWithWhitespaceIndex) - (charIndex + 1);
                    currentToken.insert(0, chars, (charIndex + 1), stringLength);
                    tokenList.add(currentToken.toString());
                    currentToken = new StringBuilder();

                    // Skip all added characters
                    charIndex = (stringWithWhitespaceIndex + 1);
                }
                default -> currentToken.append(chars[charIndex]);
            }
        }
        // Add whatever is left in currentToken to tokenList
        if (!currentToken.isEmpty()) {
            tokenList.add(currentToken.toString());
        }

        System.out.println(tokenList);

        result.command = tokenList.get(0);
        result.args = new String[tokenList.size() - 1];
        for (int index = 0; index < (tokenList.size() - 1); index++) {
            result.args[index] = tokenList.get(index + 1);
        }

        return result;
    }

    /**
     * Klasse zum Halten von Befehl und Argumenten.
     */
    private static class ParsedCommand {
        String command;
        String[] args;
    }

}
