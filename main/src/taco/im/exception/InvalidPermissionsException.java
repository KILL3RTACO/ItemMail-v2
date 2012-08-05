package taco.im.exception;

public class InvalidPermissionsException extends Exception {
	
	private static final long serialVersionUID = -2198444261060732345L;
	
	public InvalidPermissionsException(String message){
		super(message);
	}

}
