package be.yonicon.template.inmem;

import be.yonicon.template.domain.DomainEventHandler;
import be.yonicon.template.domain.DomainEventPublisher;
import be.yonicon.template.domain.DomainEventSubscriber;
import be.yonicon.template.domain.event.DomainEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class InMemoryDomainEventPublisher implements DomainEventPublisher, DomainEventSubscriber {

    private final List<DomainEvent> events = new ArrayList<>();
    private final List<DomainEventHandler> domainEventHandlers = new ArrayList<>();

    public static InMemoryDomainEventPublisher create(DomainEvent... events) {
        var eventPublisher = new InMemoryDomainEventPublisher();
        for (DomainEvent event : events) {
            eventPublisher.publish(event);
        }

        return eventPublisher;
    }

    public List<DomainEvent> receivedEvents() {
        return Collections.unmodifiableList(events);
    }

    public <T> Optional<T> getLastPublishedEvent(Class<T> t) {
        if (events.isEmpty()) {
            return Optional.empty();
        }
        return Optional
                .of(events.get(events.size() - 1))
                .map(t::cast);
    }

    @Override
    public void publish(DomainEvent event) {
        events.add(event);
        notifyHandlers(event);
    }

    private void notifyHandlers(DomainEvent event) {
        for (DomainEventHandler domainEventHandler : domainEventHandlers) {
            domainEventHandler.handle(event);
        }
    }

    public void clear() {
        events.clear();
    }

    @Override
    public void subscribe(DomainEventHandler eventHandler) {
        domainEventHandlers.add(eventHandler);
    }
}
