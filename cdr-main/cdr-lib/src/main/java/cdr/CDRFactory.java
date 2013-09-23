package cdr;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.net.URI;
import java.util.Properties;

import javax.ws.rs.core.UriBuilder;

import org.jboss.resteasy.client.ClientRequestFactory;

public abstract class CDRFactory <T extends CDR> {	

	protected abstract T produces();
	
	@SuppressWarnings("unchecked")
	protected T getService (){				
		
		System.out.print("Factory for: ");		
		ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();		
		Class<T> clazz = (Class<T>) superclass.getActualTypeArguments()[0];
		System.out.println(clazz.getCanonicalName());
				
		URI uri = getUri(clazz.getCanonicalName());	
		ClientRequestFactory crf = new ClientRequestFactory(UriBuilder.fromUri(uri).build());	
		registerInterceptor(crf);					

		return crf.createProxy(clazz);
	}
	
	protected URI getUri(String callingInterface){
		URI result=null;
		String key = callingInterface+".url";
		System.out.println("Looking for: "+key);
		
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
	protected void registerInterceptor(ClientRequestFactory crf){
//		crf.getSuffixInterceptors().getExecutionInterceptorList().add(clientInterceptor);
//		ResteasyProviderFactory pf = ResteasyProviderFactory.getInstance();
//		pf.addClientErrorInterceptor(clientExeptionMapper);
	}
}
