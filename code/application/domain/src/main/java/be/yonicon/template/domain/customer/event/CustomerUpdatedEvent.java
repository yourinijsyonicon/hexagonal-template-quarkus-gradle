package be.yonicon.template.domain.customer.event;

import be.yonicon.template.domain.event.AbstractEvent;
import be.yonicon.template.vocabulary.CustomerId;

import static java.util.Objects.requireNonNull;

public class CustomerUpdatedEvent extends AbstractEvent {
    private final CustomerId id;
    private final String commercialName;

    public CustomerUpdatedEvent(CustomerId id, String commercialName) {
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
