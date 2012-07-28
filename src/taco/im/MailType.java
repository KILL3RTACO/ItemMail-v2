package taco.im;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import taco.im.exception.InvalidGameModeException;
import taco.im.exception.InvalidPermissionsException;

public interface MailType {

	public MailBoxType getBox();
	
	public int getItemAmount();
	
	public int getItemDamage();
	
	public String getSender();
	
	public String getReceiver();
	
	public ItemStack getItems();
	
	public Material getItemType();
	
	public int getItemTypeId();
	
	public boolean send() throws InvalidPermissionsException, InvalidGameModeException;
	
}
