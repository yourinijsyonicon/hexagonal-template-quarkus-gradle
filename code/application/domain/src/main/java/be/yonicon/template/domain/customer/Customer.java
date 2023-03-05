package be.yonicon.template.domain.customer;

import be.yonicon.template.vocabulary.CustomerDTO;
import be.yonicon.template.vocabulary.CustomerId;

public class Customer {
    private final CustomerId id;
    private String commercialName;
    private String legalName;
    private String contactPerson;
    private String vatNr;

    private Customer(CustomerId id, String commercialName, String legalName, String contactPerson, String vatNr) {
        this.id = id;
        this.commercialName = commercialName;
        this.legalName = legalName;
        this.contactPerson = contactPerson;
        this.vatNr = vatNr;
    }

    public static Customer createNew(final CustomerDTO dto) {
        return new Customer(
                CustomerId.newId(),
                dto.getCommercialName(),
                dto.getLegalName(),
                dto.getContactPerson(),
                dto.getVatNr());
    }

    public static Customer fromSnapshot(final CustomerSnapshot snapshot) {
        return new Customer(
                snapshot.id(),
                snapshot.dto().getCommercialName(),
                snapshot.dto().getLegalName(),
                snapshot.dto().getContactPerson(),
                snapshot.dto().getVatNr());
    }

    public CustomerSnapshot takeSnapshot() {
        return new CustomerSnapshot(
                id,
                CustomerDTO.newBuilder()
                        .withCommercialName(commercialName)
                        .withLegalName(legalName)
                        .withContactPerson(contactPerson)
                        .withVatNr(vatNr)
                        .build());
    }

    public void update(CustomerDTO customerDTO) {
        commercialName = customerDTO.getCommercialName();
        legalName = customerDTO.getLegalName();
        contactPerson = customerDTO.getContactPerson();
        vatNr = customerDTO.getVatNr();
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", commercialName='" + commercialName + '\'' +
                ", legalName='" + legalName + '\'' +
                ", contactPerson='" + contactPerson + '\'' +
                ", vatNr='" + vatNr + '\'' +
                '}';
    }
}
