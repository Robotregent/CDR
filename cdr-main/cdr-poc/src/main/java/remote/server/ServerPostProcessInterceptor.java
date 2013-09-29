package remote.server;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.interception.AcceptedByMethod;
import org.jboss.resteasy.spi.interception.PostProcessInterceptor;
/**
 * A CDR-Hook to intercept after the response object is created but before the marshaling.
 * There is also a PreProcessInterceptor, who runs before the method invocation 
 * and a ClientExecutionInterceptors to modify the client request.
 * CDR use this to keep the method signature clean. In this example the ServerPostProcessInterceptor 
 * adds the Location-Header to the response object. 
 * So there is no trace of the REST semantic in the business logic.
 * 
 * @author robotregent
 *
 */
@Provider
@ServerInterceptor
public class ServerPostProcessInterceptor implements PostProcessInterceptor, AcceptedByMethod {	
	
	@Context
	HttpServletRequest servletRequest;
	
	public void postProcess(ServerResponse response) {
		Integer id = (Integer) response.getEntity();
		response.setStatus(201);
		MultivaluedMap<String, Object> headers = response.getMetadata();
		 
		String ru = servletRequest.getRequestURL().toString();
		
		if (ru.endsWith("/"))
			ru += id;
		else
			ru += "/"+id;		
		
		headers.add("Location", ru );
		
		System.out.println("Server PostProcess, created: "+ ru);
	}

	@SuppressWarnings("rawtypes")
	public boolean accept(Class declaring, Method method) {
		return method.isAnnotationPresent(POST.class);
	}

}
