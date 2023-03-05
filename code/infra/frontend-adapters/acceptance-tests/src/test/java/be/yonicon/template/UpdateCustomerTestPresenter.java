package be.yonicon.template;

import be.yonicon.template.applicationapi.customer.UpdateCustomerPresenter;
import be.yonicon.template.vocabulary.CustomerId;

public class UpdateCustomerTestPresenter implements UpdateCustomerPresenter {
    private boolean success;
    private boolean customerIdNotExists;

    @Override
    public void success() {
        success = true;
        customerIdNotExists = false;
    }

    @Override
    public void customerIdDoesNotExist(CustomerId id) {
        customerIdNotExists = true;
        success = false;
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isCustomerIdNotExistent() {
        return customerIdNotExists;
    }
}
