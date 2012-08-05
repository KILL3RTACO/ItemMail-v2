package taco.im.exception;

public class ItemsNotFoundException extends Exception{

	private static final long serialVersionUID = -5665869104539441605L;
	
	public ItemsNotFoundException(String message){
		super(message);
	}
}
