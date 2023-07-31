package be.yonicon.template.config;

import be.yonicon.template.domain.DomainEventPublisher;
import be.yonicon.template.inmem.InMemoryDomainEventPublisher;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;

@Dependent
public class DomainEventConfig {
    @Produces
    public DomainEventPublisher domainEventPublisher() {
        return new InMemoryDomainEventPublisher();
    }
}
