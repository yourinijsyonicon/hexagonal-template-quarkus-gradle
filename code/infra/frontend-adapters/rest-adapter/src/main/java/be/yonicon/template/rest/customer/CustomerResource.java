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
import org.jboss.resteasy.reactive.ResponseStatus;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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
