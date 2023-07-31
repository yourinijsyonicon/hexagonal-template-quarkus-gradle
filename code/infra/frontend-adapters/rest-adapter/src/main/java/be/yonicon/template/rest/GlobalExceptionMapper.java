package be.yonicon.template.rest;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(final Exception exception) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .type(MediaType.TEXT_PLAIN)
                .entity(exception.getMessage())
                .build();
    }
}
