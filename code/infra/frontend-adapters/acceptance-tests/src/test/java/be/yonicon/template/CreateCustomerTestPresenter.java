package be.yonicon.template;

import be.yonicon.template.applicationapi.customer.CreateCustomerPresenter;

public class CreateCustomerTestPresenter implements CreateCustomerPresenter {
    private boolean success;
    private boolean vatNrAlreadyExists;

    @Override
    public void success() {
        success = true;
        vatNrAlreadyExists = false;
    }

    @Override
    public void customerVatNrAlreadyExists(String vatNr) {
        vatNrAlreadyExists = true;
        success = false;
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isVatNrAlreadyExists() {
        return vatNrAlreadyExists;
    }
}
