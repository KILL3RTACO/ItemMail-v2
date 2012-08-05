package taco.im.exception;

public class MailNonExistantException extends Exception {

	private static final long serialVersionUID = 4195502234634487642L;

	public MailNonExistantException(String message){
		super(message);
	}
	
}
