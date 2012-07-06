package taco.im.obj;

import org.bukkit.entity.Player;

import taco.im.ItemMail;

public class ItemMailPlayer {

	private Player p = null;
	private ItemMail plugin = null;
	
	public ItemMailPlayer(Player player, ItemMail instance){
		p = player;
		plugin = instance;
	}
	
}
