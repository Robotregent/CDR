package remote;

import java.net.MalformedURLException;
import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.container.test.api.OverProtocol;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.descriptor.api.Descriptors;
import org.jboss.shrinkwrap.descriptor.api.spec.cdi.beans.BeansDescriptor;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import remote.ServiceProducer;
import remote.client.ClientExceptionMapper;
import remote.client.Client_Activator;
import remote.client.Endpoint;
import remote.client.IEndpoint;
import remote.server.ServerExceptionMapper;
import remote.server.ServerPostProcessInterceptor;
import remote.server.Server_Activator;


@RunWith(Arquillian.class)
public class RemoteDeployment_IT {
	
	private static IEndpoint client;
	private static Integer id;
	
	@Deployment(order=1, name="Server", testable = false) @OverProtocol("Servlet 3.0")
	public static WebArchive createRemoteDeployment(){		
		
		WebArchive war = ShrinkWrap.create(WebArchive.class, "Server.war")				
				.addAsLibraries(Maven.resolver().resolve("cdr:example-service-impl:1.0-SNAPSHOT").withTransitivity().asFile())
				.addClasses(Server_Activator.class, ServerPostProcessInterceptor.class, ServerExceptionMapper.class)	
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
		
		return war;
	}
	
	@Deployment(order=2 , name="Client", testable = true)
	public static WebArchive createLocalDeployment(){		
	
		WebArchive war = ShrinkWrap.create(WebArchive.class, "Client.war")				
				.addAsLibraries(Maven.resolver().resolve("cdr:example-service-api:1.0-SNAPSHOT").withTransitivity().asFile())
				.addAsLibraries(Maven.resolver().resolve("cdr:cdr-lib:1.0-SNAPSHOT").withTransitivity().asFile())
				.addClasses(ClientExceptionMapper.class, ServiceProducer.class,IEndpoint.class, Endpoint.class,  Client_Activator.class)	
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
	            .addAsResource("META-INF/urls.properties");	
		return war;
	}
	
	@BeforeClass @OperateOnDeployment("Client") 
	public static void initResteasyClient() throws MalformedURLException {
        RegisterBuiltin.register(ResteasyProviderFactory.getInstance());
    }

	@Test @OperateOnDeployment("Client") @RunAsClient @InSequence(1)
	public void create (@ArquillianResource URL url) throws Exception{

        client = ProxyFactory.create(IEndpoint.class,url.toString());
        		
		id = client.put();
		
		Assert.assertNotNull(id);	
		System.out.println("Create item with id: " + id);
	}
	
	@Test @OperateOnDeployment("Client") @RunAsClient @InSequence(2)
	public void get () throws Exception {	        
        String r = client.get(id);
		Assert.assertNotNull(r);
		System.out.println("Get created item. Has description: " + r);
	}
	
	@Test @OperateOnDeployment("Client") @RunAsClient @InSequence(3)
	public void exception () throws Exception{
		String r = client.get(42);
		Assert.assertNotNull(r);
		System.out.println("Catched Exception: " + r);
	}
	
}
