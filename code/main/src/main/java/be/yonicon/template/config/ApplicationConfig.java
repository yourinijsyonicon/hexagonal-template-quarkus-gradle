package be.yonicon.template.config;

import be.yonicon.template.applicationapi.customer.CreateCustomer;
import be.yonicon.template.applicationapi.customer.DeleteCustomer;
import be.yonicon.template.applicationapi.customer.GetCustomer;
import be.yonicon.template.applicationapi.customer.ListCustomer;
import be.yonicon.template.applicationapi.customer.UpdateCustomer;
import be.yonicon.template.config.properties.InfraProperties;
import be.yonicon.template.domain.DomainEventPublisher;
import be.yonicon.template.domain.customer.CustomerFactory;
import be.yonicon.template.domain.customer.CustomerRepository;
import be.yonicon.template.query.customer.CustomersArchive;
import be.yonicon.template.query.customer.GetCustomerQuery;
import be.yonicon.template.query.customer.ListCustomersQuery;
import be.yonicon.template.usecase.customer.create.CreateCustomerUseCase;
import be.yonicon.template.usecase.customer.delete.DeleteCustomerUseCase;
import be.yonicon.template.usecase.customer.update.UpdateCustomerUseCase;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;

@Dependent
public class ApplicationConfig {
    @Produces
    public CustomerRepository customerRepository(InfraProperties properties) {
        if (properties.isCustomerInMemorySet()) {
            return InMemorySetup.inMemoryCustomerRepository();
        }

        throw new IllegalStateException("Customer Repository is required.");
    }

    @Produces
    public CustomersArchive customersArchive(InfraProperties properties) {
        if (properties.isCustomerInMemorySet()) {
            return InMemorySetup.inMemoryCustomerRepository();
        }

        throw new IllegalStateException("Customers Archive is required.");
    }

    @Produces
    public CreateCustomer createCustomer(CustomerRepository customerRepository,
                                         CustomerFactory customerFactory,
                                         DomainEventPublisher domainEventPublisher) {
        return new CreateCustomerUseCase(customerRepository, customerFactory, domainEventPublisher);
    }

    @Produces
    public UpdateCustomer updateCustomer(CustomerRepository customerRepository,
                                         DomainEventPublisher domainEventPublisher) {
        return new UpdateCustomerUseCase(customerRepository, domainEventPublisher);
    }

    @Produces
    public DeleteCustomer deleteCustomer(CustomerRepository customerRepository,
                                         DomainEventPublisher domainEventPublisher) {
        return new DeleteCustomerUseCase(customerRepository, domainEventPublisher);
    }

    @Produces
    public ListCustomer listCustomer(CustomersArchive customersArchive) {
        return new ListCustomersQuery(customersArchive);
    }

    @Produces
    public GetCustomer getCustomer(CustomersArchive customersArchive) {
        return new GetCustomerQuery(customersArchive);
    }

    @Produces
    public CustomerFactory customerFactory(CustomerRepository customerRepository) {
        return new CustomerFactory(customerRepository);
    }
}
