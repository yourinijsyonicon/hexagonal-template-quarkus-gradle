package be.yonicon.template.applicationapi.customer;

import be.yonicon.template.vocabulary.CustomerId;
import be.yonicon.template.vocabulary.CustomerItem;

public interface GetCustomer {
    CustomerItem getCustomer(final CustomerId id);
}
