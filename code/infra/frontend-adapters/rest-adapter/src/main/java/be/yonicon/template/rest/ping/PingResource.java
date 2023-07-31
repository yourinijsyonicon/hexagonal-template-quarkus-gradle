package be.yonicon.template.rest.ping;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/ping")
@Tag(name = "PingRestApi", description = "Ping API Operations")
public class PingResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String ping() {
        return "i am alive";
    }
}
