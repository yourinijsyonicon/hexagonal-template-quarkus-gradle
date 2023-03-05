package be.yonicon.template.usecase.customer.create;

import be.yonicon.template.vocabulary.CustomerDTO;

public final class CreateCustomerCommand {
    private final CustomerDTO dto;

    private CreateCustomerCommand(CustomerDTO dto) {
        this.dto = dto;
    }

    public CustomerDTO getDto() {
        return dto;
    }

    public static CreateCustomerCommand newCommand(final CustomerDTO dto) {
        return new CreateCustomerCommand(dto);
    }
}
