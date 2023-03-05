package be.yonicon.template.query.customer;

import be.yonicon.template.applicationapi.customer.ListCustomer;
import be.yonicon.template.vocabulary.CustomerItem;

import java.util.List;

public class ListCustomersQuery implements ListCustomer {
    private final CustomersArchive archive;

    public ListCustomersQuery(CustomersArchive archive) {
        this.archive = archive;
    }

    @Override
    public List<CustomerItem> getAllCustomers() {
        return archive.getAllCustomers();
    }
}
