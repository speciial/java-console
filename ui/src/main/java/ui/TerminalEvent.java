package ui;

public class TerminalEvent {

    public TerminalEventType type;

    // TODO: should this be generic?
    public String content;

    public TerminalEvent(TerminalEventType type, String content) {
        this.type = type;
        this.content = content;
    }

}
