package be.yonicon.template.rest;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ResourceConflictExceptionMapper implements ExceptionMapper<ResourceConflictException> {

    @Override
    public Response toResponse(final ResourceConflictException exception) {
        return Response.status(Response.Status.CONFLICT)
                .type(MediaType.TEXT_PLAIN)
                .entity(exception.getMessage())
                .build();
    }
}
