package taco.im.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import taco.im.MailBoxType;
import taco.im.MailType;

public class ItemMailEvent extends Event {

	protected MailType type;
	protected MailBoxType mbType;
	
	@Override
	public HandlerList getHandlers() {
		// TODO Auto-generated method stub
		return null;
	}

}
