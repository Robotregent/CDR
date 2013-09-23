package local;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import example.SomeApplicationException;
import example.TodoItem;
import example.service.ExampleService;

@RunWith(Arquillian.class)
public class Local {
	
	@Deployment
	public static WebArchive createDeployment(){		
		WebArchive war = ShrinkWrap.create(WebArchive.class, "local.war")				
				.addAsLibraries(Maven.resolver().resolve("cdr:example-service-impl:1.0-SNAPSHOT").withTransitivity().asFile())		
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");	
		return war;
	}
	
	@Inject ExampleService service;
	
	@Test 
	public void get () throws Exception{
		TodoItem r = service.getItem(1);
		Assert.assertNotNull(r);	
		System.out.println(r);
	}
	
	@Test 
	public void create (ExampleService service) throws Exception{
		Integer r = service.takeItem(new TodoItem());
		Assert.assertNotNull(r);	
		System.out.println(r);
	}
	
	@Test 
	public void exception () throws Exception{
		try {
			TodoItem r = service.getItem(33);
			System.out.println(r);
		} catch (SomeApplicationException e) {
			Assert.assertNotNull(e);				
		}
	}
}
