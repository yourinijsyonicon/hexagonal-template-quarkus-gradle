package be.yonicon.template.domain.event;

/**
 * Aggregate Events : Events thrown by Aggregates ;-) -> typically used for event Sourcing?
 * Domain Events : Internal Application Events that potentially trigger useCases. Often maps one on one with aggregate events
 * Application Events : External Events, part of public API
 * <p>
 * Marker interface for events
 * Prefix Archive to prevent mistakes with other event and event handlers
 */
public interface DomainEvent {

}
