package be.yonicon.template.domain;

import java.util.Optional;

public interface Repository<AGGREGATE_ID, AGGREGATE> {

    void save(AGGREGATE aggregate);

    Optional<AGGREGATE> get(AGGREGATE_ID aggregateId);

    void remove(AGGREGATE_ID aggregateId);
}
