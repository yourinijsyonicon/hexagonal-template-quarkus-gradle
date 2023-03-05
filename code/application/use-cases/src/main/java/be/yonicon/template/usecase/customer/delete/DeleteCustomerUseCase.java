package be.yonicon.template.usecase.customer.delete;

import be.yonicon.template.applicationapi.customer.DeleteCustomer;
import be.yonicon.template.applicationapi.customer.DeleteCustomerPresenter;
import be.yonicon.template.domain.DomainEventPublisher;
import be.yonicon.template.domain.customer.Customer;
import be.yonicon.template.domain.customer.CustomerRepository;
import be.yonicon.template.domain.customer.CustomerSnapshot;
import be.yonicon.template.domain.customer.event.CustomerDeletedEvent;
import be.yonicon.template.usecase.BufferedEventPublisher;
import be.yonicon.template.usecase.UseCase;
import be.yonicon.template.vocabulary.CustomerId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class DeleteCustomerUseCase implements UseCase<DeleteCustomerCommand, DeleteCustomerPresenter>, DeleteCustomer {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteCustomerUseCase.class);

    private final CustomerRepository repository;
    private final DomainEventPublisher domainEventPublisher;

    public DeleteCustomerUseCase(CustomerRepository repository, DomainEventPublisher domainEventPublisher) {
        this.repository = repository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public void delete(CustomerId id, DeleteCustomerPresenter presenter) {
        execute(DeleteCustomerCommand.newCommand(id),
                presenter);
    }

    @Override
    public void execute(DeleteCustomerCommand command,
                        DeleteCustomerPresenter presenter) {
        new BufferedEventPublisher(domainEventPublisher).doBuffered(publisher -> executeBuffered(command, presenter, publisher));
    }

    private void executeBuffered(DeleteCustomerCommand command,
                                 DeleteCustomerPresenter presenter,
                                 DomainEventPublisher publisher) {
        Optional<Customer> customer = repository.get(command.id());

        customer.ifPresentOrElse(
                it -> removeCustomer(command, presenter, publisher, it),
                () -> customerIdDoesNotExist(command, presenter));
    }

    private void customerIdDoesNotExist(DeleteCustomerCommand command, DeleteCustomerPresenter presenter) {
        LOGGER.warn("Delete Customer : customer id {} does not exist", command.id());
        presenter.customerIdDoesNotExist(command.id());
    }

    private void removeCustomer(DeleteCustomerCommand command,
                                DeleteCustomerPresenter presenter,
                                DomainEventPublisher publisher,
                                Customer customer) {
        repository.remove(command.id());

        publisher.publish(createCustomerDeletedEvent(customer));

        presenter.success();
    }

    private CustomerDeletedEvent createCustomerDeletedEvent(Customer customer) {
        CustomerSnapshot snapshot = customer.takeSnapshot();
        return new CustomerDeletedEvent(snapshot.id(), snapshot.dto().getCommercialName());
    }
}
