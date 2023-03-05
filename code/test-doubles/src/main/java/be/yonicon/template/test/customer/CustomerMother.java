package be.yonicon.template.test.customer;

import be.yonicon.template.vocabulary.CustomerId;

public final class CustomerMother {
    public static final CustomerId AMAZON_ID = CustomerId.newId();
    public static final CustomerId ALIBABA_ID = CustomerId.newId();

    private CustomerMother() {
    }

    public static CustomerTestDataBuilder amazonCustomer() {
        return new CustomerTestDataBuilder()
                .withCustomerId(AMAZON_ID)
                .withCommercialName("Amazon")
                .withLegalName("Amazon Legal")
                .withContactPerson("Jeff Bezos")
                .withVatNr("0123456789");
    }

    public static CustomerTestDataBuilder alibabaCustomer() {
        return new CustomerTestDataBuilder()
                .withCustomerId(ALIBABA_ID)
                .withCommercialName("Alibaba")
                .withLegalName("Alibaba Legal")
                .withContactPerson("Daniel Zhang")
                .withVatNr("0987654321");
    }
}
