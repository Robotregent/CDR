package example.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.enterprise.context.ApplicationScoped;

import example.SomeApplicationException;
import example.TodoItem;
import example.service.Service;

@ApplicationScoped
public class ServiceImplementation implements Service {
	
	private Map<Integer, TodoItem> todoDB = new ConcurrentHashMap<Integer, TodoItem>();
	private AtomicInteger idCounter = new AtomicInteger();
	
	public ServiceImplementation() {
		TodoItem first = new TodoItem();
		first.setDescription("First FuckUp Item");
		first.setPrio(1);
		Integer id = idCounter.incrementAndGet();
		todoDB.put(id,first);
	}	
	
	public void setRemote(){
		todoDB.get(1).setDescription(todoDB.get(1).getDescription()+" remote");
	}
	
	public TodoItem[] getItems() {
		return  todoDB.values().toArray(new TodoItem[0]);
	}
	
	public Integer takeItem(TodoItem tdi){
		Integer id = idCounter.incrementAndGet();
		todoDB.put(id, tdi);
		return id;		
	}
	
	public TodoItem getItem(int id) throws SomeApplicationException {
		TodoItem result = todoDB.get(id);
		if (result == null)
			throw new SomeApplicationException("No Item");
		return result;
	}
}
