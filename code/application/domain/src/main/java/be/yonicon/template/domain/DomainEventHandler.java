package be.yonicon.template.domain;

import be.yonicon.template.domain.event.DomainEvent;

public interface DomainEventHandler {
    void handle(DomainEvent event);
}
