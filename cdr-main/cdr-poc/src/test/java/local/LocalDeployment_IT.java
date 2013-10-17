package local;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import example.SomeApplicationException;
import example.TodoItem;
import example.service.Service;

@RunWith(Arquillian.class)
public class LocalDeployment_IT {
	
	private static Integer id;
	
	@Deployment
	public static WebArchive createDeployment(){		
		WebArchive war = ShrinkWrap.create(WebArchive.class, "local.war")				
				.addAsLibraries(Maven.resolver().resolve("cdr:example-service-impl:1.0-SNAPSHOT").withTransitivity().asFile())		
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");	
		return war;
	}
	
	@Inject Service service;
	
	@Test @InSequence(1)
	public void create (Service service) throws Exception{
		TodoItem t = new TodoItem();
		t.setDescription("Testitem");
		id = service.takeItem(t);
		Assert.assertNotNull(id);	
		System.out.println("Create item with id: " + id);
	}
	
	@Test @InSequence(2)
	public void get () throws Exception{
		TodoItem r = service.getItem(id);
		Assert.assertNotNull(r);	
		System.out.println("Get created item. Has description: " + r.getDescription());	
	}
	
	@Test @InSequence(3)
	public void exception () throws Exception{
		try {
			TodoItem r = service.getItem(33);
			System.out.println(r);
		} catch (SomeApplicationException e) {
			Assert.assertNotNull(e);	
			System.out.println("Catched Exception: " + e.getMessage());
		}
	}
}
