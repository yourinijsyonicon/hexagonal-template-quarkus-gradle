package be.yonicon.template.rest.customer.content.request;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Valid
public class CustomerContentRequest {
    @NotBlank(message = "Please provide The Commercial Name")
    @Schema(example = "The Commercial Name", required = true, description = "The Commercial Name of our Customer")
    private String commercialName;

    @NotBlank(message = "Please provide the Legal Name")
    @Schema(example = "The Legal Name", description = "The Legal Name of our Customer")
    private String legalName;

    @NotBlank(message = "Please provide the Contact Person")
    @Schema(example = "Joske Vermeulen", description = "The Contact Person of our customer")
    private String contactPerson;

    @Length(min = 10, max = 10, message = "The Vat number needs to be 10 characters long")
    @Schema(example = "0123456789", required = true, description = "The VAT number of our customer, format is without symbols (f.e. 0123456789)")
    private String vatNr;

    public CustomerContentRequest() {
    }

    public CustomerContentRequest(String commercialName, String legalName, String contactPerson, String vatNr) {
        this.commercialName = commercialName;
        this.legalName = legalName;
        this.contactPerson = contactPerson;
        this.vatNr = vatNr;
    }

    public void setCommercialName(String commercialName) {
        this.commercialName = commercialName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public void setVatNr(String vatNr) {
        this.vatNr = vatNr;
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
}
