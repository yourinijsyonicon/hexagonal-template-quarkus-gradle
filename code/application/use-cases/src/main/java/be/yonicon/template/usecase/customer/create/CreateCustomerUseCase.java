package be.yonicon.template.usecase.customer.create;

import be.yonicon.template.applicationapi.customer.CreateCustomer;
import be.yonicon.template.applicationapi.customer.CreateCustomerPresenter;
import be.yonicon.template.domain.DomainEventPublisher;
import be.yonicon.template.domain.customer.Customer;
import be.yonicon.template.domain.customer.CustomerFactory;
import be.yonicon.template.domain.customer.CustomerRepository;
import be.yonicon.template.domain.customer.CustomerSnapshot;
import be.yonicon.template.domain.customer.CustomerVatNrAlreadyExistsException;
import be.yonicon.template.domain.customer.event.CustomerCreatedEvent;
import be.yonicon.template.usecase.BufferedEventPublisher;
import be.yonicon.template.usecase.UseCase;
import be.yonicon.template.vocabulary.CustomerDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateCustomerUseCase implements UseCase<CreateCustomerCommand, CreateCustomerPresenter>, CreateCustomer {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateCustomerUseCase.class);

    private final CustomerRepository repository;
    private final CustomerFactory factory;
    private final DomainEventPublisher domainEventPublisher;

    public CreateCustomerUseCase(CustomerRepository repository,
                                 CustomerFactory factory,
                                 DomainEventPublisher domainEventPublisher) {
        this.repository = repository;
        this.factory = factory;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public void create(CustomerDTO dto, CreateCustomerPresenter presenter) {
        CreateCustomerCommand command = CreateCustomerCommand.newCommand(dto);
        execute(command, presenter);
    }

    @Override
    public void execute(CreateCustomerCommand command, CreateCustomerPresenter presenter) {
        new BufferedEventPublisher(domainEventPublisher).doBuffered(publisher -> executeBuffered(command, presenter, publisher));
    }

    private void executeBuffered(CreateCustomerCommand command,
                                 CreateCustomerPresenter presenter,
                                 DomainEventPublisher publisher) {
        try {
            Customer customer = factory.createNew(command.getDto());
            repository.save(customer);

            CustomerCreatedEvent event = createCustomerCreatedEvent(customer);
            publisher.publish(event);
            presenter.success();
        } catch (CustomerVatNrAlreadyExistsException e) {
            LOGGER.warn("Create Customer : Customer With VatNr {} already exists", command.getDto().getVatNr());
            presenter.customerVatNrAlreadyExists(command.getDto().getVatNr());
        }
    }

    private CustomerCreatedEvent createCustomerCreatedEvent(Customer customer) {
        CustomerSnapshot snapshot = customer.takeSnapshot();
        return new CustomerCreatedEvent(
                snapshot.id(),
                snapshot.dto().getCommercialName());
    }
}
