package be.yonicon.template.domain.event;

public abstract class AbstractEvent implements DomainEvent {
    private final EventId eventId;

    protected AbstractEvent() {
        this.eventId = EventId.newId();
    }

    public EventId getEventId() {
        return eventId;
    }

    @Override
    public String toString() {
        return "AbstractEvent{" +
                "eventId=" + eventId +
                '}';
    }
}