package be.yonicon.template.domain.event;

import java.util.Objects;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

public final class EventId {
    public final String uuid;

    EventId(String value) {
        this.uuid = UUID.fromString(requireNonNull(value)).toString();
    }

    public static EventId newId() {
        return new EventId(UUID.randomUUID().toString());
    }

    @Override
    public String toString() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EventId eventId = (EventId) o;
        return Objects.equals(uuid, eventId.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
