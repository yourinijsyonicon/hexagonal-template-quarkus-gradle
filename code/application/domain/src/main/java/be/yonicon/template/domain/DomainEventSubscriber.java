package be.yonicon.template.domain;

public interface DomainEventSubscriber {

    void subscribe(DomainEventHandler eventHandler);
}
