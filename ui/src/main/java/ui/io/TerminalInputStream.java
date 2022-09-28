package ui.io;

import ui.CustomTerminalComponent;
import ui.TerminalContext;

import java.io.IOException;
import java.nio.CharBuffer;

public class TerminalInputStream implements Readable{

    private volatile StringBuilder sb = new StringBuilder();
    private volatile boolean waitingForInput = false;

    public TerminalInputStream() { }

    @Override
    public int read(CharBuffer cb) throws IOException {
        if (waitingForInput)
            return -1;

        cb.append(clearBuilder());
        return -1;
    }

    public void write(String str) {
        sb.append(str);
    }

    public String nextLine() {
        if (TerminalContext.isUIThread()) {
            throw new RuntimeException("You cannot ask for input within the FX Thread");
        }

        waitingForInput = true;
        while (sb.isEmpty()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        waitingForInput = false;
        return clearBuilder();
    }

    /**
     * Clears the string builder
     *
     * @return the previous leftover string
     */
    private String clearBuilder() {
        String old = sb.toString();
        this.sb = new StringBuilder();
        return old;
    }

}
