package ui.event;

public class TerminalEvent {

    private final EventType eventType;

    // TODO: Should this be a generic member?
    private final String content;

    public TerminalEvent(EventType eventType, String content) {
        this.eventType = eventType;
        this.content = content;
    }

    public EventType getEventType() {
        return eventType;
    }

    public String getContent() {
        return content;
    }

}
