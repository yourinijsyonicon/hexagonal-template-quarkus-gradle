package be.yonicon.template.rest.customer;

import be.yonicon.template.applicationapi.customer.CreateCustomerPresenter;
import be.yonicon.template.rest.ResourceConflictException;

public class RestCreateCustomerPresenter implements CreateCustomerPresenter {
    @Override
    public void success() {
        // nothing to do here...
    }

    @Override
    public void customerVatNrAlreadyExists(String vatNr) {
        throw new ResourceConflictException("Customer", "Vat Nr", vatNr);
    }
}
