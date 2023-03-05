package be.yonicon.template.vocabulary;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerItemTest {
    @Test
    void testEquality() {
        CustomerId id = CustomerId.newId();

        CustomerItem source = createItem(id);
        CustomerItem clone = createItem(id);

        assertThat(source)
                .isEqualTo(clone)
                .hasSameHashCodeAs(clone)
                .hasToString(clone.toString());
    }

    private CustomerItem createItem(CustomerId id) {
        return CustomerItem
                .newBuilder()
                .withId(id)
                .withDto(CustomerDTO.newBuilder()
                        .withContactPerson("Contact")
                        .withCommercialName("Commercial")
                        .withVatNr("VAT")
                        .build())
                .build();
    }

}