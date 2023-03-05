package be.yonicon.template.applicationapi.customer;

import be.yonicon.template.vocabulary.CustomerDTO;

public interface CreateCustomer {
    void create(CustomerDTO dto, CreateCustomerPresenter presenter);
}
