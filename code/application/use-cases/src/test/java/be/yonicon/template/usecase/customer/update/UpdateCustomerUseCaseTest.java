package be.yonicon.template.usecase.customer.update;

import be.yonicon.template.applicationapi.customer.UpdateCustomerPresenter;
import be.yonicon.template.domain.customer.Customer;
import be.yonicon.template.domain.customer.event.CustomerUpdatedEvent;
import be.yonicon.template.inmem.InMemoryCustomerRepository;
import be.yonicon.template.inmem.InMemoryDomainEventPublisher;
import be.yonicon.template.test.customer.CustomerMother;
import be.yonicon.template.vocabulary.CustomerDTO;
import be.yonicon.template.vocabulary.CustomerId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("Given a template application containing 1 customer")
class UpdateCustomerUseCaseTest {
    private final CustomerDTO updateData = CustomerDTO.newBuilder()
            .withContactPerson("New Contact Person")
            .withLegalName("New Legal Name")
            .withCommercialName("New Commercial Name")
            .withVatNr("0123123123")
            .build();

    private final Customer existingCustomer = CustomerMother.alibabaCustomer().build();
    private final InMemoryDomainEventPublisher publisher = InMemoryDomainEventPublisher.create();
    private final InMemoryCustomerRepository repository = InMemoryCustomerRepository.create(existingCustomer);
    private final UpdateCustomerUseCase useCase = new UpdateCustomerUseCase(repository, publisher);

    @Nested
    @DisplayName("When updating a existing customer")
    class TestWhenUpdatingExistingCustomer implements FailingUpdateCustomerPresenter {
        private boolean isSuccess;

        @BeforeEach
        void setup() {
            useCase.update(existingCustomer.takeSnapshot().id(), updateData, this);
        }

        @Test
        @DisplayName("Then success presented.")
        void isSuccessfullyUpdated() {
            assertThat(isSuccess).isTrue();
        }

        @Test
        @DisplayName("Then Customer is updated")
        void isCustomerStored() {
            assertThat(repository.getAllCustomers())
                    .hasSize(1)
                    .anySatisfy(item -> {
                        assertThat(item.getId()).isEqualTo(existingCustomer.takeSnapshot().id());
                        assertThat(item.getDto()).isEqualTo(updateData);
                    });
        }

        @Test
        @DisplayName("Then CustomerUpdatedEvent is published")
        void anCustomerUpdatedEventIsPublished() {
            assertThat(publisher.getLastPublishedEvent(CustomerUpdatedEvent.class))
                    .hasValueSatisfying(event -> {
                        assertThat(event.getEventId()).isNotNull();
                        assertThat(event.getId()).isEqualTo(existingCustomer.takeSnapshot().id());
                        assertThat(event.getCommercialName()).isEqualTo(updateData.getCommercialName());
                    });
        }

        @Override
        public void success() {
            isSuccess = true;
        }
    }

    @Nested
    @DisplayName("When creating a new customer with an already existing vat nr")
    class TestWhenUpdatingNonExistingCustomer implements FailingUpdateCustomerPresenter {
        private boolean customerIdDoesNotExist;

        @BeforeEach
        void setup() {
            useCase.update(CustomerId.newId(), updateData, this);
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
                    .noneMatch(s -> s.getDto().equals(updateData));
        }

        @Test
        @DisplayName("Then CustomerUpdatedEvent is published")
        void noCustomerUpdatedEventIsThrown() {
            assertThat(publisher.getLastPublishedEvent(CustomerUpdatedEvent.class))
                    .isEmpty();
        }

        @Override
        public void customerIdDoesNotExist(CustomerId id) {
            customerIdDoesNotExist = true;
        }
    }

    public interface FailingUpdateCustomerPresenter extends UpdateCustomerPresenter {
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