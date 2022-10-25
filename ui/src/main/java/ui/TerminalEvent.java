package ui;

public class TerminalEvent {

    public TerminalEventType type;

    public String content;

    public TerminalEvent(TerminalEventType type, String content) {
        this.type = type;
        this.content = content;
    }

}
