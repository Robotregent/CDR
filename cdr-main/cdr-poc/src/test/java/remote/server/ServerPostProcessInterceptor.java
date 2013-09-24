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
 * Kann zum aufhübschen der Responses dienen. In diesem Fall wird Location Header gesetzt. Über den 
 * PreProcessInterceptor und den HttpServletRequest Context könnte auch die Container Authentification abgehandelt werden. Mal sehen  
 * @author robotregent
 *
 */
@Provider
@ServerInterceptor
public class ServerPostProcessInterceptor implements PostProcessInterceptor, AcceptedByMethod {	
	
	@Context
	HttpServletRequest servletRequest;
	
	@Override
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

	@Override @SuppressWarnings("rawtypes")
	public boolean accept(Class declaring, Method method) {
		return method.isAnnotationPresent(POST.class);
	}

}
