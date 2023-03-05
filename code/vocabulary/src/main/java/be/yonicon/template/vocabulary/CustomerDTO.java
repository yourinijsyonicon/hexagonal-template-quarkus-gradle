package be.yonicon.template.vocabulary;

import java.util.Objects;

public class CustomerDTO {
    private final String commercialName;
    private final String legalName;
    private final String contactPerson;
    private final String vatNr;

    private CustomerDTO(Builder builder) {
        commercialName = builder.commercialName;
        legalName = builder.legalName;
        contactPerson = builder.contactPerson;
        vatNr = builder.vatNr;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getCommercialName() {
        return commercialName;
    }

    public String getLegalName() {
        return legalName;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public String getVatNr() {
        return vatNr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerDTO that = (CustomerDTO) o;
        return Objects.equals(commercialName, that.commercialName) && Objects.equals(legalName, that.legalName) && Objects.equals(contactPerson, that.contactPerson) && Objects.equals(vatNr, that.vatNr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commercialName, legalName, contactPerson, vatNr);
    }

    @Override
    public String toString() {
        return "CustomerDTO{" +
                "commercialName='" + commercialName + '\'' +
                ", legalName='" + legalName + '\'' +
                ", contactPerson='" + contactPerson + '\'' +
                ", vatNr='" + vatNr + '\'' +
                '}';
    }

    public static final class Builder {
        private String commercialName;
        private String legalName;
        private String contactPerson;
        private String vatNr;

        private Builder() {
        }

        public Builder withCommercialName(String commercialName) {
            this.commercialName = commercialName;
            return this;
        }

        public Builder withLegalName(String legalName) {
            this.legalName = legalName;
            return this;
        }

        public Builder withContactPerson(String contactPerson) {
            this.contactPerson = contactPerson;
            return this;
        }

        public Builder withVatNr(String vatNr) {
            this.vatNr = vatNr;
            return this;
        }

        public CustomerDTO build() {
            return new CustomerDTO(this);
        }
    }
}
