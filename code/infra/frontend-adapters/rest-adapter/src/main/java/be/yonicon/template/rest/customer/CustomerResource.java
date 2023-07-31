package be.yonicon.template.rest.customer;

import be.yonicon.template.applicationapi.customer.CreateCustomer;
import be.yonicon.template.applicationapi.customer.DeleteCustomer;
import be.yonicon.template.applicationapi.customer.GetCustomer;
import be.yonicon.template.applicationapi.customer.ListCustomer;
import be.yonicon.template.applicationapi.customer.UpdateCustomer;
import be.yonicon.template.rest.ResourceNotFoundException;
import be.yonicon.template.rest.customer.content.request.CustomerContentRequest;
import be.yonicon.template.rest.customer.content.request.CustomerContentRequestMapper;
import be.yonicon.template.rest.customer.content.response.CustomerContentResponse;
import be.yonicon.template.rest.customer.content.response.CustomerContentResponseMapper;
import be.yonicon.template.vocabulary.CustomerId;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.ResponseStatus;

import java.util.List;

import static java.util.Optional.ofNullable;

@Path("/customer")
public class CustomerResource implements CustomerRestApi {
    private final CreateCustomer createCustomer;
    private final DeleteCustomer deleteCustomer;
    private final UpdateCustomer updateCustomer;
    private final ListCustomer listCustomer;
    private final GetCustomer getCustomer;

    public CustomerResource(CreateCustomer createCustomer,
                            DeleteCustomer deleteCustomer,
                            UpdateCustomer updateCustomer,
                            ListCustomer listCustomer,
                            GetCustomer getCustomer) {
        this.createCustomer = createCustomer;
        this.deleteCustomer = deleteCustomer;
        this.updateCustomer = updateCustomer;
        this.listCustomer = listCustomer;
        this.getCustomer = getCustomer;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public List<CustomerContentResponse> getCustomers() {
        return listCustomer
                .getAllCustomers()
                .stream()
                .map(CustomerContentResponseMapper::map)
                .toList();
    }

    @GET
    @Path("/{customerId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public CustomerContentResponse getCustomer(@PathParam("customerId") final String customerId) {
        return ofNullable(customerId)
                .map(CustomerId::from)
                .map(getCustomer::getCustomer)
                .map(CustomerContentResponseMapper::map)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", customerId));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(201)
    @Override
    public void createCustomer(@Valid final CustomerContentRequest customerContent) {
        ofNullable(customerContent)
                .map(CustomerContentRequestMapper::map)
                .ifPresent(it -> createCustomer.create(it, new RestCreateCustomerPresenter()));
    }

    @PUT
    @Path("/{customerId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Override
    public void updateCustomer(@PathParam("customerId") final String customerId,
                               @Valid final CustomerContentRequest customerContent) {
        ofNullable(customerContent)
                .map(CustomerContentRequestMapper::map)
                .ifPresent(it -> updateCustomer.update(toCustomerId(customerId), it, new RestUpdateCustomerPresenter()));
    }

    @DELETE
    @Path("/{customerId}")
    @Override
    public void deleteCustomer(@PathParam("customerId") final String customerId) {
        deleteCustomer.delete(
                toCustomerId(customerId),
                new RestDeleteCustomerPresenter());
    }

    private CustomerId toCustomerId(final String customerId) {
        return ofNullable(customerId)
                .map(CustomerId::from)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", customerId));
    }
}
