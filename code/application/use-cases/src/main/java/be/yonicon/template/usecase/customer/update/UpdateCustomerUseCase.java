package be.yonicon.template.usecase.customer.update;

import be.yonicon.template.applicationapi.customer.UpdateCustomer;
import be.yonicon.template.applicationapi.customer.UpdateCustomerPresenter;
import be.yonicon.template.domain.DomainEventPublisher;
import be.yonicon.template.domain.customer.Customer;
import be.yonicon.template.domain.customer.CustomerRepository;
import be.yonicon.template.domain.customer.CustomerSnapshot;
import be.yonicon.template.domain.customer.event.CustomerUpdatedEvent;
import be.yonicon.template.usecase.BufferedEventPublisher;
import be.yonicon.template.usecase.UseCase;
import be.yonicon.template.vocabulary.CustomerDTO;
import be.yonicon.template.vocabulary.CustomerId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class UpdateCustomerUseCase implements UseCase<UpdateCustomerCommand, UpdateCustomerPresenter>, UpdateCustomer {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateCustomerUseCase.class);

    private final CustomerRepository repository;
    private final DomainEventPublisher domainEventPublisher;

    public UpdateCustomerUseCase(CustomerRepository repository, DomainEventPublisher domainEventPublisher) {
        this.repository = repository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public void update(CustomerId id, CustomerDTO dto, UpdateCustomerPresenter presenter) {
        UpdateCustomerCommand command = UpdateCustomerCommand.newCommand(id, dto);
        execute(command, presenter);
    }

    @Override
    public void execute(UpdateCustomerCommand command, UpdateCustomerPresenter presenter) {
        new BufferedEventPublisher(domainEventPublisher).doBuffered(publisher -> executeBuffered(command, presenter, publisher));
    }

    private void executeBuffered(UpdateCustomerCommand command,
                                 UpdateCustomerPresenter presenter,
                                 DomainEventPublisher publisher) {
        Optional<Customer> customer = repository.get(command.id());

        customer.ifPresentOrElse(
                it -> updateCustomer(command, presenter, publisher, it),
                () -> customerIdDoesNotExist(command, presenter));
    }

    private void customerIdDoesNotExist(UpdateCustomerCommand command, UpdateCustomerPresenter presenter) {
        LOGGER.warn("Update Customer : customer id {} does not exist", command.id());
        presenter.customerIdDoesNotExist(command.id());
    }

    private void updateCustomer(UpdateCustomerCommand command,
                                UpdateCustomerPresenter presenter,
                                DomainEventPublisher publisher,
                                Customer customer) {
        customer.update(command.dto());

        repository.save(customer);

        publisher.publish(createCustomerUpdatedEvent(customer));

        presenter.success();
    }

    private CustomerUpdatedEvent createCustomerUpdatedEvent(Customer customer) {
        CustomerSnapshot snapshot = customer.takeSnapshot();
        return new CustomerUpdatedEvent(snapshot.id(), snapshot.dto().getCommercialName());
    }
}
