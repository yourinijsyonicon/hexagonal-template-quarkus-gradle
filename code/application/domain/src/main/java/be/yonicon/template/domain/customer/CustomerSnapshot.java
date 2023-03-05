package be.yonicon.template.domain.customer;

import be.yonicon.template.vocabulary.CustomerDTO;
import be.yonicon.template.vocabulary.CustomerId;

public record CustomerSnapshot(CustomerId id, CustomerDTO dto) {
}
