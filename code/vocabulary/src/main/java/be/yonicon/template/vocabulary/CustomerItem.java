package be.yonicon.template.vocabulary;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class CustomerItem {
    private final CustomerId id;
    private final CustomerDTO dto;

    private CustomerItem(Builder builder) {
        id = requireNonNull(builder.id);
        dto = requireNonNull(builder.dto);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public CustomerId getId() {
        return id;
    }

    public CustomerDTO getDto() {
        return dto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerItem that = (CustomerItem) o;
        return id.equals(that.id) && dto.equals(that.dto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dto);
    }

    public static final class Builder {
        private CustomerId id;
        private CustomerDTO dto;

        private Builder() {
        }

        public Builder withId(CustomerId id) {
            this.id = id;
            return this;
        }

        public Builder withDto(CustomerDTO dto) {
            this.dto = dto;
            return this;
        }

        public CustomerItem build() {
            return new CustomerItem(this);
        }
    }
}
