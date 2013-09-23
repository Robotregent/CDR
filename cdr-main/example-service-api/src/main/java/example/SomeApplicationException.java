package example;

public class SomeApplicationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	public SomeApplicationException (String m){
		super(m);
	}
	public SomeApplicationException (){
		super();
	}

}
