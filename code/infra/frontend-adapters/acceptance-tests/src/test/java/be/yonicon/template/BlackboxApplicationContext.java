package be.yonicon.template;

import be.yonicon.template.applicationapi.customer.CreateCustomer;
import be.yonicon.template.applicationapi.customer.DeleteCustomer;
import be.yonicon.template.applicationapi.customer.GetCustomer;
import be.yonicon.template.applicationapi.customer.ListCustomer;
import be.yonicon.template.applicationapi.customer.UpdateCustomer;
import be.yonicon.template.domain.DomainEventHandler;
import be.yonicon.template.domain.DomainEventSubscriber;
import be.yonicon.template.domain.customer.CustomerFactory;
import be.yonicon.template.inmem.InMemoryCustomerRepository;
import be.yonicon.template.inmem.InMemoryDomainEventPublisher;
import be.yonicon.template.query.customer.GetCustomerQuery;
import be.yonicon.template.query.customer.ListCustomersQuery;
import be.yonicon.template.usecase.customer.create.CreateCustomerUseCase;
import be.yonicon.template.usecase.customer.delete.DeleteCustomerUseCase;
import be.yonicon.template.usecase.customer.update.UpdateCustomerUseCase;

public class BlackboxApplicationContext {
    // private repositories
    private final InMemoryDomainEventPublisher publisher = new InMemoryDomainEventPublisher();
    private final InMemoryCustomerRepository customerRepository = InMemoryCustomerRepository.create();
    private final CustomerFactory customerFactory = new CustomerFactory(customerRepository);

    // private use cases

    // public api
    public final CreateCustomer createCustomer = new CreateCustomerUseCase(customerRepository, customerFactory, publisher);
    public final UpdateCustomer updateCustomer = new UpdateCustomerUseCase(customerRepository, publisher);
    public final DeleteCustomer deleteCustomer = new DeleteCustomerUseCase(customerRepository, publisher);
    public final ListCustomer listCustomer = new ListCustomersQuery(customerRepository);
    public final GetCustomer getCustomer = new GetCustomerQuery(customerRepository);

    public BlackboxApplicationContext() {
        // subscribe(policy::new)
    }

    public void clear() {
        customerRepository.clear();
    }

    private void subscribe(DomainEventHandler domainEventHandler) {
        ((DomainEventSubscriber) publisher).subscribe(domainEventHandler);
    }
}
