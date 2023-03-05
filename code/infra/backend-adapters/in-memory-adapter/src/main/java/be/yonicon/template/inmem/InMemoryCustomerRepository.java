package be.yonicon.template.inmem;

import be.yonicon.template.domain.customer.Customer;
import be.yonicon.template.domain.customer.CustomerRepository;
import be.yonicon.template.domain.customer.CustomerSnapshot;
import be.yonicon.template.query.customer.CustomersArchive;
import be.yonicon.template.vocabulary.CustomerDTO;
import be.yonicon.template.vocabulary.CustomerId;
import be.yonicon.template.vocabulary.CustomerItem;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Optional.ofNullable;

public class InMemoryCustomerRepository implements CustomerRepository, CustomersArchive {
    private final Map<CustomerId, CustomerSnapshot> customerMap = new ConcurrentHashMap<>();

    private InMemoryCustomerRepository() {
    }

    public static InMemoryCustomerRepository create(final Customer... customers) {
        final var repository = new InMemoryCustomerRepository();
        Arrays.stream(customers)
                .forEach(repository::save);
        return repository;
    }

    @Override
    public void save(Customer customer) {
        customerMap.put(customer.takeSnapshot().id(), customer.takeSnapshot());
    }

    @Override
    public Optional<Customer> get(CustomerId customerId) {
        return ofNullable(customerMap.get(customerId))
                .map(Customer::fromSnapshot);
    }

    @Override
    public void remove(CustomerId customerId) {
        customerMap.remove(customerId);
    }

    @Override
    public Optional<Customer> findByVatNr(String vatNr) {
        return customerMap.values()
                .stream()
                .filter(it -> it.dto().getVatNr().equals(vatNr))
                .findFirst()
                .map(Customer::fromSnapshot);
    }

    @Override
    public List<CustomerItem> getAllCustomers() {
        return customerMap.entrySet()
                .stream()
                .map(entry -> mapSnapshotToItem(entry.getKey(), entry.getValue()))
                .toList();
    }

    @Override
    public Optional<CustomerItem> findCustomer(CustomerId id) {
        return ofNullable(customerMap.get(id))
                .map(snapshot -> mapSnapshotToItem(id, snapshot));
    }

    public void clear() {
        customerMap.clear();
    }

    private CustomerItem mapSnapshotToItem(CustomerId id, CustomerSnapshot snapshot) {
        return CustomerItem.newBuilder()
                .withId(id)
                .withDto(CustomerDTO.newBuilder()
                        .withCommercialName(snapshot.dto().getCommercialName())
                        .withLegalName(snapshot.dto().getLegalName())
                        .withContactPerson(snapshot.dto().getContactPerson())
                        .withVatNr(snapshot.dto().getVatNr())
                        .build())
                .build();
    }
}
