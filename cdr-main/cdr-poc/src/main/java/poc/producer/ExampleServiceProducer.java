package poc.producer;

import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Produces;

import org.jboss.resteasy.client.ClientRequestFactory;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

import cdr.CDRFactory;
import example.service.ExampleService;

@Alternative
public class ExampleServiceProducer extends CDRFactory<ExampleService> {

	@Override
	@Produces
	protected ExampleService produces() {
		// TODO Auto-generated method stub
		return getService();
	}
	
	@Override
	protected void registerInterceptor(ClientRequestFactory crf){
//		crf.getSuffixInterceptors().getExecutionInterceptorList().add(clientInterceptor);
		ResteasyProviderFactory pf = ResteasyProviderFactory.getInstance();
		ClientExceptionMapper clientExeptionMapper = new ClientExceptionMapper();
		pf.addClientErrorInterceptor(clientExeptionMapper);
	}
	
	
}