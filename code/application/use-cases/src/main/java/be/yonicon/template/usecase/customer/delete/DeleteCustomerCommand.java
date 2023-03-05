package be.yonicon.template.usecase.customer.delete;

import be.yonicon.template.vocabulary.CustomerId;

public record DeleteCustomerCommand(CustomerId id) {

    public static DeleteCustomerCommand newCommand(final CustomerId id) {
        return new DeleteCustomerCommand(id);
    }
}
