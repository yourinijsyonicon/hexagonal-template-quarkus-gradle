package be.yonicon.template.usecase.customer.create;

import be.yonicon.template.applicationapi.customer.CreateCustomerPresenter;
import be.yonicon.template.domain.customer.CustomerFactory;
import be.yonicon.template.domain.customer.event.CustomerCreatedEvent;
import be.yonicon.template.inmem.InMemoryCustomerRepository;
import be.yonicon.template.inmem.InMemoryDomainEventPublisher;
import be.yonicon.template.test.customer.CustomerMother;
import be.yonicon.template.vocabulary.CustomerDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("Given a template application containing 1 customer")
class CreateCustomerUseCaseTest {
    private final InMemoryDomainEventPublisher publisher = InMemoryDomainEventPublisher.create();
    private final InMemoryCustomerRepository repository = InMemoryCustomerRepository.create(CustomerMother.alibabaCustomer().build());
    private final CustomerFactory factory = new CustomerFactory(repository);
    private final CreateCustomerUseCase useCase = new CreateCustomerUseCase(repository, factory, publisher);

    @Nested
    @DisplayName("When creating a new customer")
    class TestWhenCreatingNewCustomer implements FailingCreateCustomerPresenter {
        private final CustomerDTO customer = CustomerMother.amazonCustomer().buildDto();

        private boolean isSuccess;

        @BeforeEach
        void setup() {
            useCase.create(customer, this);
        }

        @Test
        @DisplayName("Then success presented.")
        void isSuccessfullyCreated() {
            assertThat(isSuccess).isTrue();
        }

        @Test
        @DisplayName("Then Customer is stored")
        void isCustomerStored() {
            assertThat(repository.getAllCustomers())
                    .hasSize(2)
                    .anySatisfy(item -> {
                        assertThat(item.getId()).isNotNull();
                        assertThat(item.getDto()).isEqualTo(customer);
                    });
        }

        @Test
        @DisplayName("Then CustomerCreatedEvent is published")
        void anCustomerCreatedEventIsPublished() {
            assertThat(publisher.getLastPublishedEvent(CustomerCreatedEvent.class))
                    .hasValueSatisfying(event -> {
                        assertThat(event.getEventId()).isNotNull();
                        assertThat(event.getId()).isNotNull();
                        assertThat(event.getCommercialName()).isEqualTo(customer.getCommercialName());
                    });
        }

        @Override
        public void success() {
            isSuccess = true;
        }
    }

    @Nested
    @DisplayName("When creating a new customer with an already existing vat nr")
    class TestWhenCreatingNewCustomerWithAlreadyExistingVatNr implements FailingCreateCustomerPresenter {
        private final CustomerDTO customer = CustomerMother.amazonCustomer()
                .withVatNr(CustomerMother.alibabaCustomer().buildDto().getVatNr())
                .buildDto();

        private boolean vatNrAlreadyExistsPresented;

        @BeforeEach
        void setup() {
            useCase.create(customer, this);
        }

        @Test
        @DisplayName("Then vat nr already exists presented.")
        void isVatNrAlreadyExistsPresented() {
            assertThat(vatNrAlreadyExistsPresented).isTrue();
        }

        @Test
        @DisplayName("Then Customer is not stored")
        void isCustomerNotStored() {
            assertThat(repository.getAllCustomers())
                    .hasSize(1)
                    .noneMatch(s -> s.getDto().equals(customer));
        }

        @Test
        @DisplayName("Then CustomerCreatedEvent is published")
        void noCustomerCreatedEventIsThrown() {
            assertThat(publisher.getLastPublishedEvent(CustomerCreatedEvent.class))
                    .isEmpty();
        }

        @Override
        public void customerVatNrAlreadyExists(String vatNr) {
            vatNrAlreadyExistsPresented = true;
        }
    }

    public interface FailingCreateCustomerPresenter extends CreateCustomerPresenter {
        @Override
        default void success() {
            fail("success should not be called.");
        }

        @Override
        default void customerVatNrAlreadyExists(String vatNr) {
            fail("customerVatNrAlreadyExists should not be called");
        }
    }
}