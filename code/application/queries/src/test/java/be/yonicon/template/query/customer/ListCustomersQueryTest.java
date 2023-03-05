package be.yonicon.template.query.customer;

import be.yonicon.template.domain.customer.Customer;
import be.yonicon.template.inmem.InMemoryCustomerRepository;
import be.yonicon.template.test.customer.CustomerMother;
import be.yonicon.template.vocabulary.CustomerItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Given a customers archive")
class ListCustomersQueryTest {

    private final Customer expectedAmazon = CustomerMother.amazonCustomer().build();
    private final Customer expectedAlibaba = CustomerMother.alibabaCustomer().build();
    private final InMemoryCustomerRepository archive = InMemoryCustomerRepository.create(expectedAmazon, expectedAlibaba);
    private final ListCustomersQuery query = new ListCustomersQuery(archive);

    @Nested
    @DisplayName("When querying for all customers and archive has customers.")
    class TestWhenFound {
        private List<CustomerItem> actuals;

        @BeforeEach
        void setup() {
            actuals = query.getAllCustomers();
        }

        @Test
        @DisplayName("Then it should find all customer details.")
        void itShouldGetCustomers() {
            assertThat(actuals)
                    .hasSize(2)
                    .anySatisfy(it -> {
                        assertThat(it.getId()).isEqualTo(expectedAmazon.takeSnapshot().id());
                        assertThat(it.getDto()).isEqualTo(expectedAmazon.takeSnapshot().dto());
                    })
                    .anySatisfy(it -> {
                        assertThat(it.getId()).isEqualTo(expectedAlibaba.takeSnapshot().id());
                        assertThat(it.getDto()).isEqualTo(expectedAlibaba.takeSnapshot().dto());
                    });
        }
    }

    @Nested
    @DisplayName("When querying for all customers and archive does not have customers.")
    class TestWhenNoneFound {
        private List<CustomerItem> actuals;

        @BeforeEach
        void setup() {
            archive.clear();
            actuals = query.getAllCustomers();
        }

        @Test
        @DisplayName("Then it should find no customer details.")
        void itShouldNotGetACustomer() {
            assertThat(actuals).isEmpty();
        }
    }
}