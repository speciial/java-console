package os.io;

import ui.WindowContext;

public class TerminalOutputStream {

    public void print(String line) {
        WindowContext.print(line);
    }

    public void println(String line) {
        WindowContext.print(line + "\n");
    }

    public void printf(String format, Object... args) {
        WindowContext.print(String.format(format, args));
    }

}
