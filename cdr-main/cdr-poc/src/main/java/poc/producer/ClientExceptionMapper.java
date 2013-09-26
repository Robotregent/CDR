package poc.producer;

import java.io.IOException;
import java.io.InputStream;

import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.client.core.BaseClientResponse;
import org.jboss.resteasy.client.core.ClientErrorInterceptor;

import example.SomeApplicationException;

public class ClientExceptionMapper implements ClientErrorInterceptor{

	@SuppressWarnings("rawtypes")
	public void handle(ClientResponse response) throws RuntimeException {
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
		// Wenn keine neue Exception geworfen wird, macht resteasy normal weiter und erzeugt ClientResponseFailure
		// https://docs.jboss.org/resteasy/docs/2.3.6.Final/userguide/html_single/#Client_error_handling
		// in neueren Versionen von RESTeasy gibts nen richtigen ClientExceptionMapper, der in der Semantik zu der Serverseite passt
		// https://docs.jboss.org/resteasy/docs/3.0-beta-3/javadocs/org/jboss/resteasy/client/exception/mapper/ClientExceptionMapper.html
	}

}
