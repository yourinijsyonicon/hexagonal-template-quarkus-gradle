package be.yonicon.template.rest.customer.content.request;

import be.yonicon.template.vocabulary.CustomerDTO;

public final class CustomerContentRequestMapper {
    private CustomerContentRequestMapper() {
    }

    public static CustomerDTO map(final CustomerContentRequest request) {
        return CustomerDTO.newBuilder()
                .withCommercialName(request.getCommercialName())
                .withLegalName(request.getLegalName())
                .withContactPerson(request.getContactPerson())
                .withVatNr(request.getVatNr())
                .build();
    }
}
