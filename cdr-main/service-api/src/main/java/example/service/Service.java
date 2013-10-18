package example.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import example.SomeApplicationException;
import example.TodoItem;

@Path("/")
public interface Service {
	
	@GET
	@Path("/item")
	@Produces(MediaType.APPLICATION_JSON)
	public TodoItem[] getItems();	

	@POST
	@Path("/item")
	@Consumes(MediaType.APPLICATION_JSON)
	public Integer takeItem(TodoItem tdi);	

	@GET
	@Path("/item/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public TodoItem getItem(@PathParam("id") int id) throws SomeApplicationException;
}
