package hsos.ui;

import hsos.ui.components.TerminalComponent;

public abstract class ApplicationOutputStream {

    protected TerminalComponent outputDevice;

    public abstract void print(String line);

    public abstract void println();

    public abstract void println(String line);

    public abstract void printf(String format, Object... args);

    protected void setOutputComponent(TerminalComponent component) {
        this.outputDevice = component;
    }
}
