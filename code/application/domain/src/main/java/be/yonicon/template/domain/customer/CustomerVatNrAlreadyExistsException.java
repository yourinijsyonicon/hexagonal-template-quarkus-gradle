package be.yonicon.template.domain.customer;

import be.yonicon.template.vocabulary.CustomerDTO;

public class CustomerVatNrAlreadyExistsException extends RuntimeException {
    public CustomerVatNrAlreadyExistsException(CustomerDTO customer) {
        super(String.format("A customer with vatNr %s already exists", customer.getVatNr()));
    }
}
