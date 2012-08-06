package taco.im;

import org.bukkit.entity.Player;

public interface MailBoxType {
	
	public Player getOwner();

	public void getElementsAtPage(int page);
	
	public int getUnreadCount();
	
	public void reload();
	
	public void deleteAll();
	
}
