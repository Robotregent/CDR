package remote.client;

import javax.inject.Inject;

import example.SomeApplicationException;
import example.TodoItem;
import example.service.Service;

public class Endpoint implements IEndpoint{
	
	@Inject
    private Service service;
	
	public Endpoint(){
		
	}		
	
	public String get(int id) throws SomeApplicationException {
		
		String result = null;
			
		try {
			TodoItem i = service.getItem(id);
			result 	   = i.getDescription();
		} catch (SomeApplicationException e) {			
			result = e.getMessage();
		}				
		return result;
	}	
	
	public Integer put() {
		
		Integer result = null;
			
		try {
			TodoItem t = new TodoItem();
			t.setDescription("Testitem");
			result = service.takeItem(t);
		} catch (SomeApplicationException e) {			
			result = -1;
		}			
		
		return result;
	}
}
