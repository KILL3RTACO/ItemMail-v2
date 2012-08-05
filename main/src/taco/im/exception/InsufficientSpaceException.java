package taco.im.exception;

public class InsufficientSpaceException extends Exception {

	private static final long serialVersionUID = -3128472185726179291L;

	public InsufficientSpaceException(String message){
		super(message);
	}
}
