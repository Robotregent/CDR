package remote.server;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import example.SomeApplicationException;

@Provider 
public class ServerExceptionMapper implements ExceptionMapper<SomeApplicationException> {

	@Override
	public Response toResponse(SomeApplicationException e) {
		System.out.println("Server ExceptionMapper: Mapped SomeApplicationException to Statuscode 523");
		return Response.status(523).build();
	}

}
