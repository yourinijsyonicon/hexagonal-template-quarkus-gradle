package be.yonicon.template.domain.customer;

import be.yonicon.template.domain.Repository;
import be.yonicon.template.vocabulary.CustomerId;

import java.util.Optional;

public interface CustomerRepository extends Repository<CustomerId, Customer> {
    Optional<Customer> findByVatNr(String vatNr);
}
