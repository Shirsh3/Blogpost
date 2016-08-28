package tutorial.core.service.exception;

public class BlogNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BlogNotFoundException(){
		
	}
	
	public BlogNotFoundException(String message){
		super(message);
	}
	
	public BlogNotFoundException(Throwable cause,String message){
		
		super(message,cause);
	}
	
	public BlogNotFoundException(Throwable cause){
		super(cause);
	}
}
