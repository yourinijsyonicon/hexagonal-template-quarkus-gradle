package be.yonicon.template.customer;

import be.yonicon.template.BlackboxApplicationContext;
import be.yonicon.template.CreateCustomerTestPresenter;
import be.yonicon.template.CustomerTraits;
import be.yonicon.template.vocabulary.CustomerDTO;
import be.yonicon.template.vocabulary.CustomerItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateCustomerTest implements CustomerTraits {
    private static final String COMMERCIAL_NAME = "Commercial Name";
    private static final String LEGAL_NAME = "Legal Name";
    private static final String CONTACT_PERSON = "Contact Person";
    private static final String VAT_NUMBER = "The Vat Number";

    private final BlackboxApplicationContext context = new BlackboxApplicationContext();

    @Override
    public BlackboxApplicationContext getContext() {
        return context;
    }

    @Nested
    class TestWhen {
        private CreateCustomerTestPresenter presenter;

        @BeforeEach
        void setup() {
            presenter = createCustomer(createDto(COMMERCIAL_NAME, LEGAL_NAME, CONTACT_PERSON, VAT_NUMBER));
        }

        @Test
        void itShouldBeCreated() {
            Optional<CustomerItem> customer = findCustomerByVatNr(VAT_NUMBER);

            assertThat(customer)
                    .isPresent()
                    .hasValueSatisfying(it -> {
                        assertThat(it).isNotNull();
                        assertThat(it.getId()).isNotNull();
                        assertThat(it.getDto().getCommercialName()).isEqualTo(COMMERCIAL_NAME);
                        assertThat(it.getDto().getLegalName()).isEqualTo(LEGAL_NAME);
                        assertThat(it.getDto().getContactPerson()).isEqualTo(CONTACT_PERSON);
                        assertThat(it.getDto().getVatNr()).isEqualTo(VAT_NUMBER);
                    });
        }

        @Test
        void itWasSuccessful() {
            assertThat(presenter.isSuccess()).isTrue();
        }
    }

    @Nested
    class TestWhenTryingToCreateWithExistingVatNr {

        private CreateCustomerTestPresenter presenter;

        @BeforeEach
        void setup() {
            createCustomer(createDto(COMMERCIAL_NAME, LEGAL_NAME, CONTACT_PERSON, VAT_NUMBER));

            presenter = createCustomer(createDto("not relevant", "not relevant", "not relevant", VAT_NUMBER));
        }

        @Test
        void itShouldNotBeCreated() {
            assertThat(getContext().listCustomer.getAllCustomers()).hasSize(1);
        }

        @Test
        void vatNrAlreadyExistsPresented() {
            assertThat(presenter.isVatNrAlreadyExists()).isTrue();
        }
    }

    private CustomerDTO createDto(String commercialName, String legalName, String contactPerson, String vatNumber) {
        return CustomerDTO.newBuilder()
                .withCommercialName(commercialName)
                .withLegalName(legalName)
                .withContactPerson(contactPerson)
                .withVatNr(vatNumber)
                .build();
    }
}
