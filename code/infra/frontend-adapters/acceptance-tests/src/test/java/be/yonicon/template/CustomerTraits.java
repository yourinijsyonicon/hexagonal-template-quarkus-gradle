package be.yonicon.template;

import be.yonicon.template.vocabulary.CustomerDTO;
import be.yonicon.template.vocabulary.CustomerId;
import be.yonicon.template.vocabulary.CustomerItem;

import java.util.List;
import java.util.Optional;

public interface CustomerTraits extends ContextAware {
    default DeleteCustomerTestPresenter deleteCustomer(CustomerId id) {
        DeleteCustomerTestPresenter presenter = new DeleteCustomerTestPresenter();
        getContext().deleteCustomer.delete(id, presenter);
        return presenter;
    }

    default UpdateCustomerTestPresenter updateCustomer(CustomerId id, CustomerDTO dto) {
        UpdateCustomerTestPresenter presenter = new UpdateCustomerTestPresenter();
        getContext().updateCustomer.update(id, dto, presenter);
        return presenter;
    }

    default CreateCustomerTestPresenter createCustomer(CustomerDTO dto) {
        CreateCustomerTestPresenter presenter = new CreateCustomerTestPresenter();
        getContext().createCustomer.create(dto, presenter);
        return presenter;
    }

    default Optional<CustomerItem> findCustomerByVatNr(String vatNr) {
        return getContext().listCustomer.getAllCustomers().stream()
                .filter(item -> getVatNr(item).equals(vatNr))
                .findFirst();
    }

    default List<CustomerItem> getAllCustomers() {
        return getContext().listCustomer.getAllCustomers();
    }

    private String getVatNr(CustomerItem item) {
        return Optional.ofNullable(item)
                .map(CustomerItem::getDto)
                .map(CustomerDTO::getVatNr)
                .orElse("");
    }
}
