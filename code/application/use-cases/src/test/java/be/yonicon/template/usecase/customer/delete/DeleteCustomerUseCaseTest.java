package be.yonicon.template.usecase.customer.delete;

import be.yonicon.template.applicationapi.customer.DeleteCustomerPresenter;
import be.yonicon.template.domain.customer.Customer;
import be.yonicon.template.domain.customer.event.CustomerDeletedEvent;
import be.yonicon.template.inmem.InMemoryCustomerRepository;
import be.yonicon.template.inmem.InMemoryDomainEventPublisher;
import be.yonicon.template.test.customer.CustomerMother;
import be.yonicon.template.vocabulary.CustomerId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("Given a template application containing 1 customer")
class DeleteCustomerUseCaseTest {
    private final Customer existingCustomer = CustomerMother.alibabaCustomer().build();
    private final InMemoryDomainEventPublisher publisher = InMemoryDomainEventPublisher.create();
    private final InMemoryCustomerRepository repository = InMemoryCustomerRepository.create(existingCustomer);
    private final DeleteCustomerUseCase useCase = new DeleteCustomerUseCase(repository, publisher);

    @Nested
    @DisplayName("When deleting an existing customer")
    class TestWhenDeletingCustomer implements FailingDeleteCustomerPresenter {
        private boolean isSuccess;

        @BeforeEach
        void setup() {
            useCase.delete(existingCustomer.takeSnapshot().id(), this);
        }

        @Test
        @DisplayName("Then success presented.")
        void isSuccessfullyDeleted() {
            assertThat(isSuccess).isTrue();
        }

        @Test
        @DisplayName("Then Customer is deleted")
        void isCustomerStored() {
            assertThat(repository.getAllCustomers())
                    .isEmpty();
        }

        @Test
        @DisplayName("Then CustomerDeletedEvent is published")
        void anCustomerDeletedEventIsPublished() {
            assertThat(publisher.getLastPublishedEvent(CustomerDeletedEvent.class))
                    .hasValueSatisfying(event -> {
                        assertThat(event.getEventId()).isNotNull();
                        assertThat(event.getId()).isNotNull();
                        assertThat(event.getCommercialName()).isEqualTo(existingCustomer.takeSnapshot().dto().getCommercialName());
                    });
        }

        @Override
        public void success() {
            isSuccess = true;
        }
    }

    @Nested
    @DisplayName("When deleting with a non existent customer id")
    class TestWhenDeletingCustomerIdDoesNotExist implements FailingDeleteCustomerPresenter {
        private boolean customerIdDoesNotExist;

        @BeforeEach
        void setup() {
            useCase.delete(CustomerId.newId(), this);
        }

        @Test
        @DisplayName("Then customer id does not exist presented.")
        void isCustomerIdDoesNotExistentPresented() {
            assertThat(customerIdDoesNotExist).isTrue();
        }

        @Test
        @DisplayName("Then Customer is not stored")
        void isCustomerNotStored() {
            assertThat(repository.getAllCustomers())
                    .hasSize(1)
                    .allMatch(s -> s.getDto().equals(existingCustomer.takeSnapshot().dto()));
        }

        @Test
        @DisplayName("Then CustomerDeletedEvent is not published")
        void noCustomerDeletedEventIsThrown() {
            assertThat(publisher.getLastPublishedEvent(CustomerDeletedEvent.class))
                    .isEmpty();
        }

        @Override
        public void customerIdDoesNotExist(CustomerId id) {
            customerIdDoesNotExist = true;
        }
    }

    public interface FailingDeleteCustomerPresenter extends DeleteCustomerPresenter {
        @Override
        default void success() {
            fail("success should not be called.");
        }

        @Override
        default void customerIdDoesNotExist(CustomerId id) {
            fail("customerIdDoesNotExist should not be called");
        }
    }
}