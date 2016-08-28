package tutorial.core.service.exception;

public class AccountNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AccountNotFoundException(){
		
	}
	
	public AccountNotFoundException(String message){
		super(message);
	}
	
	public AccountNotFoundException(Throwable cause,String message){
		
		super(message,cause);
	}
	
	public AccountNotFoundException(Throwable cause){
		super(cause);
	}
}
