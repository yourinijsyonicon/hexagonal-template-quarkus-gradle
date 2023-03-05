package be.yonicon.template.config;

import be.yonicon.template.domain.customer.Customer;
import be.yonicon.template.domain.customer.CustomerSnapshot;
import be.yonicon.template.inmem.InMemoryCustomerRepository;
import be.yonicon.template.vocabulary.CustomerDTO;
import be.yonicon.template.vocabulary.CustomerId;

import static java.util.Objects.isNull;

public final class InMemorySetup {

    private static InMemoryCustomerRepository customerInstance;

    private InMemorySetup() {
    }

    public static InMemoryCustomerRepository inMemoryCustomerRepository() {
        if (isNull(customerInstance)) {
            customerInstance = InMemoryCustomerRepository.create(
                    createGoogleCustomer(),
                    createMicrosoftCustomer()
            );
        }
        return customerInstance;
    }


    private static Customer createGoogleCustomer() {
        return Customer.fromSnapshot(new CustomerSnapshot(CustomerId.newId(),
                CustomerDTO.newBuilder()
                        .withCommercialName("Google - Your Best Search Engine Friend")
                        .withLegalName("Google nv")
                        .withContactPerson("Jos Goegel")
                        .withVatNr("0123456789")
                        .build()));
    }

    private static Customer createMicrosoftCustomer() {
        return Customer.fromSnapshot(new CustomerSnapshot(CustomerId.newId(),
                CustomerDTO.newBuilder()
                        .withCommercialName("Microsoft - We Windows You")
                        .withLegalName("Bill Gates nv")
                        .withContactPerson("Bill Gates")
                        .withVatNr("0987654321")
                        .build()));
    }
}

