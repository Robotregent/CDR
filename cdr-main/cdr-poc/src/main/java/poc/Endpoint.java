package poc;

import javax.inject.Inject;

import example.SomeApplicationException;
import example.TodoItem;
import example.service.ExampleService;

public class Endpoint implements IEndpoint{
	
	@Inject
    private ExampleService service;
	
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
	
	public String put() {
		
		String result = null;
			
		try {
			TodoItem t = new TodoItem();
			
			Integer newItemId = service.takeItem(t);
			
			result = Integer.toString( newItemId );
		} catch (SomeApplicationException e) {			
			result = e.getMessage();
		}			
		
		return result;
	}
}
