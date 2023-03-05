package be.yonicon.template.query.customer;

import be.yonicon.template.vocabulary.CustomerId;
import be.yonicon.template.vocabulary.CustomerItem;

import java.util.List;
import java.util.Optional;

public interface CustomersArchive {
    List<CustomerItem> getAllCustomers();

    Optional<CustomerItem> findCustomer(final CustomerId id);
}
