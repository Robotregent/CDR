package poc;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import example.SomeApplicationException;

@Path("/")
public interface IEndpoint {
	@GET
	@Path("get/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String get(@PathParam("id") int id) throws SomeApplicationException;
	
	@GET
	@Path("create/")
	@Produces(MediaType.TEXT_PLAIN)
	public String put();
}
