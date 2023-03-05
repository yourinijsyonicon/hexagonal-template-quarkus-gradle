package be.yonicon.template.domain.customer;

import be.yonicon.template.vocabulary.CustomerDTO;

public class CustomerFactory {
    private final CustomerRepository customerRepository;

    public CustomerFactory(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createNew(CustomerDTO customerDTO) {
        if (customerRepository.findByVatNr(customerDTO.getVatNr()).isPresent()) {
            throw new CustomerVatNrAlreadyExistsException(customerDTO);
        }

        return Customer.createNew(customerDTO);
    }
}
