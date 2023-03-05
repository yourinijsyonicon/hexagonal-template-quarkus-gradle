package be.yonicon.template.vocabulary;

import java.util.Objects;
import java.util.UUID;

public record CustomerId(String uuid) {
    public static CustomerId newId() {
        return new CustomerId(UUID.randomUUID().toString());
    }

    public static CustomerId from(String value) {
        return new CustomerId(value);
    }

    public String toString() {
        return uuid;
    }

    public String getId() {
        return uuid;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomerId that = (CustomerId) o;

        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return uuid != null ? uuid.hashCode() : 0;
    }
}
