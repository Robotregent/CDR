package remote;

import javax.enterprise.inject.Produces;

import org.jboss.resteasy.spi.ResteasyProviderFactory;

import remote.client.ClientExceptionMapper;
import cdr.CDRFactory;
import example.service.Service;
/**
 * Implementation of the abstract CDRFactory 
 * @author robotregent
 *
 */
public class ServiceProducer extends CDRFactory<Service> {

	@Override
	@Produces
	protected Service produces() {
		// TODO Auto-generated method stub
		return getService(Service.class);
	}
	
	@Override
	protected void registerClientExceptionMapper(ResteasyProviderFactory pf){
		ClientExceptionMapper clientExeptionMapper = new ClientExceptionMapper();
		pf.addClientErrorInterceptor(clientExeptionMapper);
	}
}