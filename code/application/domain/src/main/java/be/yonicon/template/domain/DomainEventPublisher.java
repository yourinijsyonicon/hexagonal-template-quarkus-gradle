package be.yonicon.template.domain;

import be.yonicon.template.domain.event.DomainEvent;

public interface DomainEventPublisher {
    void publish(DomainEvent event);
}
