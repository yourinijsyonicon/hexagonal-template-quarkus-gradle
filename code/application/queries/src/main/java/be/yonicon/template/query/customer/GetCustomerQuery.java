package be.yonicon.template.query.customer;

import be.yonicon.template.applicationapi.customer.GetCustomer;
import be.yonicon.template.vocabulary.CustomerId;
import be.yonicon.template.vocabulary.CustomerItem;

public class GetCustomerQuery implements GetCustomer {
    private final CustomersArchive archive;

    public GetCustomerQuery(CustomersArchive archive) {
        this.archive = archive;
    }

    @Override
    public CustomerItem getCustomer(CustomerId id) {
        return archive.findCustomer(id).orElse(null);
    }
}
