package taco.im;

import org.bukkit.entity.Player;

import taco.im.exception.InvalidPermissionsException;

public interface MailBoxType {
	
	public Player getOwner();

	public void getElementsAtPage(int page);
	
	public int getUnreadCount();
	
	public void reload();
	
	public void deleteAll() throws InvalidPermissionsException;
	
}
