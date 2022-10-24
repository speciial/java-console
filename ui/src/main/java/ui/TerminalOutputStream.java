package ui;

public class TerminalOutputStream {

    private CustomTerminalComponent terminalComponent;

    // I'm not super sure about this but I think that I should synchronize the output stream as well as the input
    // stream. I suspect that the new line handling might get weird if the ui thread gets to put stuff directly into
    // the terminal.
    public void println(String line) {
        terminalComponent.println(line);
    }

    protected void setOutputComponent(CustomTerminalComponent terminalComponent) {
        this.terminalComponent = terminalComponent;
    }

}
