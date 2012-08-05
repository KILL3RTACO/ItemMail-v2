package taco.im.event.request;

import taco.im.event.ItemMailEvent;
import taco.im.request.Request;

public class RequestSentEvent extends ItemMailEvent{
	
	public RequestSentEvent(Request request){
		this.type = request;
	}

	public Request getRequest(){
		return (Request)this.type;
	}
	
}
