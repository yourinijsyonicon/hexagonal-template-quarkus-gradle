package be.yonicon.template.config.properties;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithName;

import static java.util.Optional.ofNullable;

@ConfigMapping(prefix = "be.yonicon.template.infra")
public interface InfraProperties {

    @WithName("customer")
    CustomerProperties getCustomer();

    default boolean isCustomerInMemorySet() {
        return ofNullable(getCustomer())
                .map(CustomerProperties::getBackend)
                .map("inmem"::equals)
                .orElse(false);
    }
}
