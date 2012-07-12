package taco.im.cmd;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import taco.im.ItemMail;
import taco.im.Mail;

public class ItemMailCommand implements CommandExecutor {
	
	private ItemMail plugin = null;
	
	public ItemMailCommand(ItemMail instance){
		plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		Player player = null;
		if(sender instanceof Player){
			player = (Player)sender;
		}
		
		if(args.length == 0){
			
		}else if(args.length == 1){
			Mail mail = Mail.getMailFromTable(player, 1, plugin);
			if(mail == null){
				player.sendMessage("That mail doesn't exist");
			}else{
				mail.open();
			}
		}
		return false;
	}

}
