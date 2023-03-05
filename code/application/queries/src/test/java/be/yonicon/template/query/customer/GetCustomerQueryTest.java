package be.yonicon.template.query.customer;

import be.yonicon.template.domain.customer.Customer;
import be.yonicon.template.inmem.InMemoryCustomerRepository;
import be.yonicon.template.test.customer.CustomerMother;
import be.yonicon.template.vocabulary.CustomerId;
import be.yonicon.template.vocabulary.CustomerItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Given a customers archive containing data")
class GetCustomerQueryTest {

    private final Customer expected = CustomerMother.amazonCustomer().build();
    private final InMemoryCustomerRepository archive = InMemoryCustomerRepository.create(expected);
    private final GetCustomerQuery query = new GetCustomerQuery(archive);

    @Nested
    @DisplayName("When querying for an existing customer")
    class TestWhenFound {
        private CustomerItem actual;

        @BeforeEach
        void setup() {
            actual = query.getCustomer(expected.takeSnapshot().id());
        }

        @Test
        @DisplayName("Then it should find the customer details.")
        void itShouldGetCustomer() {
            assertThat(actual).isNotNull();
            assertThat(actual.getId()).isEqualTo(expected.takeSnapshot().id());
            assertThat(actual.getDto()).isEqualTo(expected.takeSnapshot().dto());
        }
    }

    @Nested
    @DisplayName("When querying for a non existing customer")
    class TestWhenNotFound {
        private CustomerItem actual;

        @BeforeEach
        void setup() {
            actual = query.getCustomer(CustomerId.newId());
        }

        @Test
        @DisplayName("Then it should find no customer details.")
        void itShouldNotGetACustomer() {
            assertThat(actual).isNull();
        }
    }
}