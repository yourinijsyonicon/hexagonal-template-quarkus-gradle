package be.yonicon.template.rest;


import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

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
