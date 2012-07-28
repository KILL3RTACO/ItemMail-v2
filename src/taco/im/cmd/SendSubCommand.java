package taco.im.cmd;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import taco.im.mail.Mail;
import taco.im.util.ChatUtils;
import taco.im.util.ItemNames;

public class SendSubCommand {

	ChatUtils cu = new ChatUtils();
	
	public SendSubCommand(Player player, String receiver, ItemStack items){
		Mail mail = new Mail(player.getName(), receiver, items);
		try {
			mail.send();
		} catch (Exception e) {
			player.sendMessage(e.getMessage());
		}
	}
	
	/*
	 * This method is used when sending all of a material. /im send <material> *
	 */
	
	public SendSubCommand(Player player, String receiver, String material){
		ItemStack items = ItemNames.getItemStackFromString(material, "1");
		if(items == null){
			player.sendMessage("&c\"&6" + material + "&c\" not found");
		}else{
			
		}
	}
	
}
