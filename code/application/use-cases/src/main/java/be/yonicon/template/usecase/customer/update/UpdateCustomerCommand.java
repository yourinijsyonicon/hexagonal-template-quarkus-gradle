package be.yonicon.template.usecase.customer.update;

import be.yonicon.template.vocabulary.CustomerDTO;
import be.yonicon.template.vocabulary.CustomerId;

public record UpdateCustomerCommand(CustomerId id, CustomerDTO dto) {

    public static UpdateCustomerCommand newCommand(final CustomerId id, final CustomerDTO dto) {
        return new UpdateCustomerCommand(id, dto);
    }
}
