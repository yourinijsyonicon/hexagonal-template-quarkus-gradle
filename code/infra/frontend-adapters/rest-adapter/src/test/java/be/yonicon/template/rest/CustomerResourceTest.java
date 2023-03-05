package be.yonicon.template.rest;

import be.yonicon.template.applicationapi.customer.CreateCustomer;
import be.yonicon.template.applicationapi.customer.DeleteCustomer;
import be.yonicon.template.applicationapi.customer.GetCustomer;
import be.yonicon.template.applicationapi.customer.ListCustomer;
import be.yonicon.template.applicationapi.customer.UpdateCustomer;
import be.yonicon.template.rest.customer.CustomerResource;
import be.yonicon.template.rest.customer.content.request.CustomerContentRequest;
import be.yonicon.template.rest.customer.content.response.CustomerContentResponse;
import be.yonicon.template.vocabulary.CustomerDTO;
import be.yonicon.template.vocabulary.CustomerId;
import be.yonicon.template.vocabulary.CustomerItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CustomerResourceTest {
    private static final CustomerId CUSTOMER_ID = CustomerId.newId();
    private static final CustomerDTO CUSTOMER_DTO = CustomerDTO.newBuilder()
            .withContactPerson("Contact Person")
            .withVatNr("Vat Nr")
            .withLegalName("Legal Name")
            .withCommercialName("Commercial Name")
            .build();
    public static final CustomerItem CUSTOMER_ITEM = CustomerItem.newBuilder()
            .withId(CUSTOMER_ID)
            .withDto(CUSTOMER_DTO)
            .build();

    private final GetCustomer getCustomer = mock(GetCustomer.class);
    private final ListCustomer listCustomer = mock(ListCustomer.class);
    private final CreateCustomer createCustomer = mock(CreateCustomer.class);
    private final UpdateCustomer updateCustomer = mock(UpdateCustomer.class);
    private final DeleteCustomer deleteCustomer = mock(DeleteCustomer.class);

    private final CustomerResource resource = new CustomerResource(
            createCustomer,
            deleteCustomer,
            updateCustomer,
            listCustomer,
            getCustomer);

    @BeforeEach
    void setUp() {
        when(getCustomer.getCustomer(CUSTOMER_ID)).thenReturn(CUSTOMER_ITEM);
        when(listCustomer.getAllCustomers()).thenReturn(List.of(CUSTOMER_ITEM));
    }

    @Nested
    class GetCustomerTests {
        @Test
        void testGettingExistingCustomer() {
            CustomerContentResponse customer = resource.getCustomer(CUSTOMER_ID.toString());

            assertThat(customer.getId()).isEqualTo(CUSTOMER_ID.toString());
            assertThat(customer.getLegalName()).isEqualTo(CUSTOMER_DTO.getLegalName());
            assertThat(customer.getCommercialName()).isEqualTo(CUSTOMER_DTO.getCommercialName());
            assertThat(customer.getContactPerson()).isEqualTo(CUSTOMER_DTO.getContactPerson());
            assertThat(customer.getVatNr()).isEqualTo(CUSTOMER_DTO.getVatNr());
        }

        @Test
        void testGettingNonExistentCustomer() {
            assertThatThrownBy(() -> resource.getCustomer(CustomerId.newId().toString()))
                    .isInstanceOf(ResourceNotFoundException.class);
        }
    }

    @Nested
    class ListCustomersTests {
        @Test
        void testGettingExistingCustomer() {
            List<CustomerContentResponse> customers = resource.getCustomers();

            assertThat(customers)
                    .isNotEmpty()
                    .allSatisfy(customer -> {
                        assertThat(customer.getId()).isEqualTo(CUSTOMER_ID.toString());
                        assertThat(customer.getLegalName()).isEqualTo(CUSTOMER_DTO.getLegalName());
                        assertThat(customer.getCommercialName()).isEqualTo(CUSTOMER_DTO.getCommercialName());
                        assertThat(customer.getContactPerson()).isEqualTo(CUSTOMER_DTO.getContactPerson());
                        assertThat(customer.getVatNr()).isEqualTo(CUSTOMER_DTO.getVatNr());
                    });
        }
    }

    @Nested
    class CreateCustomerTests {
        private final ArgumentCaptor<CustomerDTO> dtoCaptor = ArgumentCaptor.forClass(CustomerDTO.class);

        @BeforeEach
        void setUp() {
            doNothing().when(createCustomer).create(dtoCaptor.capture(), any());
        }

        @Test
        void testCreateNewCustomer() {
            CustomerContentRequest request = new CustomerContentRequest("commercial", "legal", "contact", "vatNr");

            resource.createCustomer(request);

            assertThat(dtoCaptor.getValue())
                    .satisfies(customerDTO -> {
                        assertThat(customerDTO.getCommercialName()).isEqualTo(request.getCommercialName());
                        assertThat(customerDTO.getLegalName()).isEqualTo(request.getLegalName());
                        assertThat(customerDTO.getContactPerson()).isEqualTo(request.getContactPerson());
                        assertThat(customerDTO.getVatNr()).isEqualTo(request.getVatNr());
                    });
        }
    }

    @Nested
    class UpdateCustomerTests {
        private final ArgumentCaptor<CustomerId> idCaptor = ArgumentCaptor.forClass(CustomerId.class);
        private final ArgumentCaptor<CustomerDTO> dtoCaptor = ArgumentCaptor.forClass(CustomerDTO.class);

        @BeforeEach
        void setUp() {
            doNothing().when(updateCustomer).update(idCaptor.capture(), dtoCaptor.capture(), any());
        }

        @Test
        void testUpdateCustomer() {
            CustomerContentRequest request = new CustomerContentRequest("commercial", "legal", "contact", "vatNr");
            CustomerId id = CustomerId.newId();
            resource.updateCustomer(id.toString(), request);

            assertThat(idCaptor.getValue())
                    .satisfies(customerId -> assertThat(customerId).isEqualTo(id));

            assertThat(dtoCaptor.getValue())
                    .satisfies(customerDTO -> {
                        assertThat(customerDTO.getCommercialName()).isEqualTo(request.getCommercialName());
                        assertThat(customerDTO.getLegalName()).isEqualTo(request.getLegalName());
                        assertThat(customerDTO.getContactPerson()).isEqualTo(request.getContactPerson());
                        assertThat(customerDTO.getVatNr()).isEqualTo(request.getVatNr());
                    });
        }
    }

    @Nested
    class DeleteCustomerTests {
        private final ArgumentCaptor<CustomerId> idCaptor = ArgumentCaptor.forClass(CustomerId.class);

        @BeforeEach
        void setUp() {
            doNothing().when(deleteCustomer).delete(idCaptor.capture(), any());
        }

        @Test
        void testDeleteCustomer() {
            CustomerId id = CustomerId.newId();
            resource.deleteCustomer(id.toString());

            assertThat(idCaptor.getValue())
                    .satisfies(customerId -> assertThat(customerId).isEqualTo(id));
        }
    }
}