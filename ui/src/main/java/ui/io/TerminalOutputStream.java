package ui.io;

import ui.CustomTerminalComponent;

public class TerminalOutputStream {

    private final CustomTerminalComponent terminal;

    public TerminalOutputStream(CustomTerminalComponent terminal) {
        this.terminal = terminal;
    }

    public void println(String str) {
        // TODO: Remove directory handling from UI. The output of a line should be constructed elsewhere
        terminal.println(str);
    }

    public void print(String str) {
        // TODO: implement in UI
    }

}
