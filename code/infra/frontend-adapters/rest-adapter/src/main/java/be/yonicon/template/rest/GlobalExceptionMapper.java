package be.yonicon.template.rest;


import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

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
