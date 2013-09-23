package remote;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import example.SomeApplicationException;

@Provider 
public class ServiceExceptionMapper implements ExceptionMapper<SomeApplicationException> {

	@Override
	public Response toResponse(SomeApplicationException e) {
		System.out.println("Server ExceptionMapper");
		return Response.status(523).build();
	}

}
