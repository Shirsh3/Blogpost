package tutorial.core.service.exception;

public class AccountExistsException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AccountExistsException(){
		
	}
	
	public AccountExistsException(String message){
		super(message);
	}
	
	public AccountExistsException(Throwable cause,String message){
		
		super(message,cause);
	}
	
	public AccountExistsException(Throwable cause){
		super(cause);
	}
}
