package be.yonicon.template.usecase;

import be.yonicon.template.domain.DomainEventPublisher;
import be.yonicon.template.domain.event.DomainEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BufferedEventPublisher implements DomainEventPublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(BufferedEventPublisher.class);

    private final List<DomainEvent> buffer;
    private final DomainEventPublisher domainEventPublisher;

    public BufferedEventPublisher(DomainEventPublisher domainEventPublisher) {
        this.buffer = new ArrayList<>();
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public void publish(DomainEvent event) {
        LOGGER.debug("Received {} to publish, delaying until the end of the function...", event);
        buffer.add(event);
    }

    public void flush() {
        for (DomainEvent it : buffer) {
            domainEventPublisher.publish(it);
        }
        buffer.clear();
    }

    @SuppressWarnings("squid:S2221")
    public void doBuffered(Consumer<DomainEventPublisher> lambda) {
        try {
            lambda.accept(this);
            flush();
        } catch (Exception e) {
            LOGGER.error("An error occurred during the execution of the lambda. Events will not be released.");
            throw e;
        }
    }
}