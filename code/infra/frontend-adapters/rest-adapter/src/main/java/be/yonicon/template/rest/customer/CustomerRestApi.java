package be.yonicon.template.rest.customer;

import be.yonicon.template.rest.customer.content.request.CustomerContentRequest;
import be.yonicon.template.rest.customer.content.response.CustomerContentResponse;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.validation.Valid;
import javax.ws.rs.PathParam;
import java.util.List;

@Tag(name = "CustomerRestApi", description = "Customer Rest API Operations")
public interface CustomerRestApi {
    @Operation(description = "Gets a list of all customers")
    @APIResponse(responseCode = "200", description = "Customers listed")
    List<CustomerContentResponse> getCustomers();

    @Operation(description = "Gets the customer by id")
    @APIResponse(responseCode = "200", description = "Customer found")
    @APIResponse(responseCode = "404", description = "Customer not found")
    CustomerContentResponse getCustomer(final String customerId);

    @Operation(description = "Creates a new customer")
    @APIResponse(responseCode = "201", description = "Customer created")
    @APIResponse(responseCode = "409", description = "Conflicting input")
    void createCustomer(@Valid final CustomerContentRequest customerContent);

    @Operation(description = "Updates an existing customer")
    @APIResponse(responseCode = "200", description = "Customer updated")
    @APIResponse(responseCode = "404", description = "Customer not found")
    @APIResponse(responseCode = "409", description = "Conflicting input")
    void updateCustomer(@PathParam("customerId") final String customerId, @Valid @RequestBody final CustomerContentRequest customerContent);

    @Operation(description = "Deletes an existing customer")
    @APIResponse(responseCode = "200", description = "Customer deleted")
    @APIResponse(responseCode = "404", description = "Customer not found")
    void deleteCustomer(final String customerId);
}
