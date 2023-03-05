package be.yonicon.template.applicationapi.customer;

public interface CreateCustomerPresenter {
    void success();

    void customerVatNrAlreadyExists(String vatNr);
}
