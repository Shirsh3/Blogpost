package tutorial.rest.mvc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ConflictException(){
		
	}
	
	public ConflictException(String message){
		super(message);
	}
	
	public ConflictException(Throwable cause,String message){
		
		super(message,cause);
	}
	
	public ConflictException(Throwable cause){
		super(cause);
	}
}
