package be.yonicon.template.applicationapi.customer;

import be.yonicon.template.vocabulary.CustomerId;

public interface DeleteCustomer {
    void delete(CustomerId id, DeleteCustomerPresenter presenter);
}
