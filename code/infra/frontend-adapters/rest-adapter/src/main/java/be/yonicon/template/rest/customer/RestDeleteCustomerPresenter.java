package be.yonicon.template.rest.customer;

import be.yonicon.template.applicationapi.customer.DeleteCustomerPresenter;
import be.yonicon.template.rest.ResourceNotFoundException;
import be.yonicon.template.vocabulary.CustomerId;

import static java.util.Optional.ofNullable;

public class RestDeleteCustomerPresenter implements DeleteCustomerPresenter {
    @Override
    public void success() {
        // nothing to do here...
    }

    @Override
    public void customerIdDoesNotExist(CustomerId id) {
        throw new ResourceNotFoundException(
                "Customer",
                ofNullable(id).map(CustomerId::toString).orElse("[No ID]"));
    }
}
