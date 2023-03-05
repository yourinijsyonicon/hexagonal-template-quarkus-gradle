package be.yonicon.template.rest.ping;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/ping")
@Tag(name = "PingRestApi", description = "Ping API Operations")
public class PingResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String ping() {
        return "i am alive";
    }
}
