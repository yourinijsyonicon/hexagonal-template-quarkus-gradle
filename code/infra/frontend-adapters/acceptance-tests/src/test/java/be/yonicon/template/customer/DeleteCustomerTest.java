package be.yonicon.template.customer;

import be.yonicon.template.BlackboxApplicationContext;
import be.yonicon.template.CustomerTraits;
import be.yonicon.template.DeleteCustomerTestPresenter;
import be.yonicon.template.vocabulary.CustomerDTO;
import be.yonicon.template.vocabulary.CustomerId;
import be.yonicon.template.vocabulary.CustomerItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DeleteCustomerTest implements CustomerTraits {
    private static final String VAT_NUMBER = "The Vat Number";

    private final BlackboxApplicationContext context = new BlackboxApplicationContext();

    @Override
    public BlackboxApplicationContext getContext() {
        return context;
    }

    @Nested
    class TestWhen {
        private DeleteCustomerTestPresenter presenter;

        @BeforeEach
        void setup() {
            createCustomer(createDto("not relevant", "not relevant", "not relevant", VAT_NUMBER));

            CustomerId existingCustomerId = findCustomerByVatNr(VAT_NUMBER)
                    .map(CustomerItem::getId)
                    .orElseThrow();

            presenter = deleteCustomer(existingCustomerId);
        }

        @Test
        void itShouldBeDeleted() {
            assertThat(getAllCustomers()).isEmpty();
        }

        @Test
        void itWasSuccessful() {
            assertThat(presenter.isSuccess()).isTrue();
        }
    }

    @Nested
    class TestWhenTryingToUpdateWithNoExistingCustomerId {

        private DeleteCustomerTestPresenter presenter;

        @BeforeEach
        void setup() {
            presenter = deleteCustomer(CustomerId.newId());
        }

        @Test
        void isCustomerIdNotExistent() {
            assertThat(presenter.isCustomerIdNotExistent()).isTrue();
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
