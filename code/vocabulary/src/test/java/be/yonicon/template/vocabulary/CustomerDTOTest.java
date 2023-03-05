package be.yonicon.template.vocabulary;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerDTOTest {

    @Test
    void testEquality() {
        CustomerDTO source = createDto();
        CustomerDTO clone = createDto();

        assertThat(source)
                .isEqualTo(clone)
                .hasSameHashCodeAs(clone)
                .hasToString(clone.toString());
    }

    private CustomerDTO createDto() {
        return CustomerDTO.newBuilder()
                .withCommercialName("CommercialName")
                .withLegalName("LegalName")
                .withContactPerson("Contact Person")
                .withVatNr("Vat")
                .build();
    }
}