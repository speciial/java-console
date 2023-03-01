package hsos.io;

import hsos.ui.ApplicationOutputStream;

public class TerminalOutputStream extends ApplicationOutputStream {

    @Override
    public void print(String line) {
        outputDevice.appendToTextBuffer(line);
    }

    @Override
    public void println() {
        outputDevice.appendToTextBuffer("\n");
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
