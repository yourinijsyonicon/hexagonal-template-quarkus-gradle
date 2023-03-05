package be.yonicon.template.test.customer;

import be.yonicon.template.domain.customer.Customer;
import be.yonicon.template.domain.customer.CustomerSnapshot;
import be.yonicon.template.vocabulary.CustomerDTO;
import be.yonicon.template.vocabulary.CustomerId;

public class CustomerTestDataBuilder {
    private CustomerId customerId;
    private String commercialName;
    private String legalName;
    private String contactPerson;
    private String vatNr;

    public CustomerTestDataBuilder withCustomerId(CustomerId customerId) {
        this.customerId = customerId;
        return this;
    }

    public CustomerTestDataBuilder withCommercialName(String commercialName) {
        this.commercialName = commercialName;
        return this;
    }

    public CustomerTestDataBuilder withLegalName(String legalName) {
        this.legalName = legalName;
        return this;
    }

    public CustomerTestDataBuilder withContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
        return this;
    }

    public CustomerTestDataBuilder withVatNr(String vatNr) {
        this.vatNr = vatNr;
        return this;
    }

    public Customer build() {
        return Customer.fromSnapshot(
                new CustomerSnapshot(
                        customerId,
                        buildDto())
        );
    }

    public CustomerDTO buildDto() {
        return CustomerDTO.newBuilder()
                .withVatNr(vatNr)
                .withCommercialName(commercialName)
                .withLegalName(legalName)
                .withContactPerson(contactPerson)
                .build();
    }
}
