package remote;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import example.service.DefaultExampleService;

@ApplicationPath("/") 
public class Server_Activator extends Application  {
	@Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> rootResources = new HashSet<Class<?>>();
        
        rootResources.add(DefaultExampleService.class);
        rootResources.add(ServerPostProcessInterceptor.class);
        rootResources.add(ServiceExceptionMapper.class);
        
        return rootResources;
    }
}