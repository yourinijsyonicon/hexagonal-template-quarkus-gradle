package be.yonicon.template.rest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.stream.Collectors;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(final ConstraintViolationException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .type(MediaType.TEXT_PLAIN)
                .entity(mapExceptionMessage(exception))
                .build();
    }

    private static String mapExceptionMessage(final ConstraintViolationException exception) {
        return exception.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("\n"));
    }
}
