package cdr;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Properties;

import javax.ws.rs.core.UriBuilder;

import org.jboss.resteasy.client.ClientRequestFactory;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

/**
 * Must be implemented for a concrete service to activate the CDR pattern.  
 * @author robotregent
 *
 * @param <T> The service
 */
public abstract class CDRFactory <T> {	

	/**
	 * Producer method for concrete factory. 
	 * Add @Produces and use getService()
	 * JSR-299 §3.3 forbids a type variable as a return type of a producer method. 
	 * Therefore you have to implement this abstract producer method and use getService() 
	 * @return
	 */
	protected abstract T produces();

	/**
	 * Provides the proxy object. 
	 * @param clazz
	 * @return
	 */
	protected T getService (Class<T> clazz){				
		
		System.out.print("Factory for: " + clazz.getCanonicalName());		
				
		URI uri = getUri(clazz.getCanonicalName());	
		ClientRequestFactory crf = new ClientRequestFactory(UriBuilder.fromUri(uri).build());	
		
		registerClientInterceptor(crf);	
		
		ResteasyProviderFactory pf = ResteasyProviderFactory.getInstance();
		
		registerClientExceptionMapper(pf);		

		return crf.createProxy(clazz);
	}
	
	private URI getUri(String callingInterface){
		URI result=null;
		String key = callingInterface+".url";
		
		// Is URL in system properties defined?
		result = getUriFromSystem(key);				
		if (result == null)
			// Is URL in properties file defined?
			result = getUriFromProperties(key);
		
		if (result != null)
			System.out.println("Looking for Service at: "+result);
		else
			System.out.println("No url found");		
		
		return result;
	}
	
	private URI getUriFromProperties(String key) {
		URI result=null;

		InputStream urls = this.getClass().getClassLoader().getResourceAsStream("META-INF/urls.properties");
		Properties props = new Properties();		
		
		try {
			props.load(urls);
			String prop = props.getProperty(key);
			if (prop != null)
				result = UriBuilder.fromUri(prop).build();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}		
		return result;
	}
	
	private URI getUriFromSystem(String key) {
		URI result=null;		
		String prop = System.getProperty(key);
		if (prop != null)
			result = UriBuilder.fromUri(prop).build();
		return result;
	}
	
	/**
	 * Hook for resteasy client side interceptors.
	 * Override to use. 
	 * @param crf
	 */
	protected void registerClientInterceptor(ClientRequestFactory crf){
	}
	
	/**
	 * Hook for resteasy client side exception mapping.
	 * Override to use. 
	 * @param pf
	 */
	protected void registerClientExceptionMapper(ResteasyProviderFactory pf){
	}
}
