package be.yonicon.template.rest.customer.content.response;

public final class CustomerContentResponse {
    private final String id;
    private final String commercialName;
    private final String legalName;
    private final String contactPerson;
    private final String vatNr;

    private CustomerContentResponse(Builder builder) {
        id = builder.id;
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

    public String getId() {
        return id;
    }

    public static final class Builder {
        private String commercialName;
        private String legalName;
        private String contactPerson;
        private String vatNr;
        private String id;

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

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public CustomerContentResponse build() {
            return new CustomerContentResponse(this);
        }

    }
}
