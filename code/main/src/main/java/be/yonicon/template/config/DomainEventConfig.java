package be.yonicon.template.config;

import be.yonicon.template.domain.DomainEventPublisher;
import be.yonicon.template.inmem.InMemoryDomainEventPublisher;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;

@Dependent
public class DomainEventConfig {
    @Produces
    public DomainEventPublisher domainEventPublisher() {
        return new InMemoryDomainEventPublisher();
    }
}
