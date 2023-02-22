package os.io;

import ui.ApplicationOutputStream;

public class TerminalOutputStream extends ApplicationOutputStream {

    @Override
    public void print(String line) {
        outputDevice.appendToTextBuffer(line);
    }

    @Override
    public void println(String line) {
        outputDevice.appendToTextBuffer(line + "\n");
    }

    @Override
    public void printf(String format, Object... args) {
        outputDevice.appendToTextBuffer(String.format(format, args));
    }

}
