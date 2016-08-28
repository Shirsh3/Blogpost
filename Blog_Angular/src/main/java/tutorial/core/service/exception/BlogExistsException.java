package tutorial.core.service.exception;

public class BlogExistsException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BlogExistsException(){
		
	}
	
	public BlogExistsException(String message){
		super(message);
	}
	
	public BlogExistsException(Throwable cause,String message){
		
		super(message,cause);
	}
	
	public BlogExistsException(Throwable cause){
		super(cause);
	}
}
