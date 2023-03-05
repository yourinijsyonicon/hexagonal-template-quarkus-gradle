package be.yonicon.template.vocabulary;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerIdTest {
    @Test
    void testEquality() {
        CustomerId source = CustomerId.newId();
        CustomerId clone = CustomerId.from(source.getId());

        assertThat(source)
                .isEqualTo(clone)
                .hasSameHashCodeAs(clone)
                .hasToString(clone.toString());
    }

}