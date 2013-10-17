package remote.client;

import java.io.IOException;
import java.io.InputStream;

import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.client.core.BaseClientResponse;
import org.jboss.resteasy.client.core.ClientErrorInterceptor;

import example.SomeApplicationException;

/**
 * A CDR-Hook to map HTTP status codes to Java-Exceptions. 
 * RSTEasy will raise a ClientResponseFailure if this class doesn't throw an Exception.   
 * Unfortunately this only works with RuntimeExceptions.
 * 
 * @author robotregent
 *
 */
public class ClientExceptionMapper implements ClientErrorInterceptor{

	@SuppressWarnings("rawtypes")
	public void handle(ClientResponse response) throws RuntimeException{
		try {
			BaseClientResponse r = (BaseClientResponse) response;
			String message = "SomeApplikationException. Status Code: " + response.getStatus();
			InputStream stream = r.getStreamFactory().getInputStream();
			stream.reset();			
			if (response.getStatus() == 523){
				System.out.println("ClientExceptionmapper: Mapped Stauscode 523 to SomeApplicationException");
				throw new SomeApplicationException(message);
			}			
		}
		catch (IOException e){
			e.printStackTrace();
		} 
	}
}
