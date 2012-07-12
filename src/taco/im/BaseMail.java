package taco.im;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public interface BaseMail {

	public int getItemAmount();
	
	public int getItemDamage();
	
	public String getSender();
	
	public String getReceiver();
	
	public ItemStack getItems();
	
	public Material getItemType();
	
	public int getItemTypeId();
	
	public void send();
	
}
