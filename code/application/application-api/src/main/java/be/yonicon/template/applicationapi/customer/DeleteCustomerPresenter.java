package be.yonicon.template.applicationapi.customer;

import be.yonicon.template.vocabulary.CustomerId;

public interface DeleteCustomerPresenter {
    void success();

    void customerIdDoesNotExist(CustomerId id);
}
