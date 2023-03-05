package be.yonicon.template.domain.event;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class EventIdTest {
    @Test
    void testEquality() {
        EventId source = EventId.newId();
        EventId clone = new EventId(source.uuid);

        assertThat(source)
                .isEqualTo(clone)
                .hasSameHashCodeAs(clone)
                .hasToString(clone.toString());
    }
}