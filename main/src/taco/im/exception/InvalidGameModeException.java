package taco.im.exception;

public class InvalidGameModeException extends Exception {

	private static final long serialVersionUID = -621712855059517409L;

	public InvalidGameModeException(String message){
		super(message);
	}
}
