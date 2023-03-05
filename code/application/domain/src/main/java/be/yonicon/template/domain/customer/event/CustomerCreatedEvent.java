package be.yonicon.template.domain.customer.event;

import be.yonicon.template.domain.event.AbstractEvent;
import be.yonicon.template.vocabulary.CustomerId;

import static java.util.Objects.requireNonNull;

public class CustomerCreatedEvent extends AbstractEvent {
    private final CustomerId id;
    private final String commercialName;

    public CustomerCreatedEvent(CustomerId id, String commercialName) {
        this.id = requireNonNull(id);
        this.commercialName = requireNonNull(commercialName);
    }

    public CustomerId getId() {
        return id;
    }

    public String getCommercialName() {
        return commercialName;
    }
}
