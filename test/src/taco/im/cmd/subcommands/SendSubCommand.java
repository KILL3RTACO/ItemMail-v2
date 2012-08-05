package taco.im.cmd.subcommands;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import taco.im.ItemMail;
import taco.im.MailType;
import taco.im.cmd.ItemMailCommand;
import taco.im.mail.Mail;
import taco.im.util.ChatUtils;
import taco.im.util.ItemNames;

public class SendSubCommand extends ItemMailCommand{

	ChatUtils cu = new ChatUtils();
	
	public SendSubCommand(){
		this.aliases = new String[]{"send", "s", "gift", "g"};
	}
	
	public void execute(MailType mType){
		Mail mail = (Mail)mType;
		Player player = ItemMail.server.getPlayer(mail.getSender());
		try {
			mail.send();
		} catch (Exception e) {
			player.sendMessage(e.getMessage());
		}
	}
	
	public void execute(Player player, String receiver, String material){
		ItemStack items = ItemNames.getItemStackFromString(material, "1");
		if(items == null){
			player.sendMessage("&c\"&6" + material + "&c\" not found");
		}else{
			for(ItemStack i : player.getInventory()){
				
			}
		}
	}
	
}
