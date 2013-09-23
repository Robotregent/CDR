package remote;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import poc.Endpoint;

@ApplicationPath("/") 
public class Client_Activator extends Application  {
	@Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> rootResources = new HashSet<Class<?>>();
        
        rootResources.add(Endpoint.class);
        
        return rootResources;
    }
}