package be.yonicon.template.rest.customer.content.response;

import be.yonicon.template.vocabulary.CustomerItem;

public final class CustomerContentResponseMapper {
    private CustomerContentResponseMapper() {
    }

    public static CustomerContentResponse map(final CustomerItem item) {

        return CustomerContentResponse.newBuilder()
                .withId(item.getId().toString())
                .withCommercialName(item.getDto().getCommercialName())
                .withLegalName(item.getDto().getLegalName())
                .withContactPerson(item.getDto().getContactPerson())
                .withVatNr(item.getDto().getVatNr())
                .build();
    }
}
