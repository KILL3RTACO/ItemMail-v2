package taco.im.cmd;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import taco.im.ItemMail;
import taco.im.Mail;
import taco.im.MailBox;
import taco.im.util.ChatUtils;

public class ItemMailCommand implements CommandExecutor {
	
	private ItemMail plugin = null;
	private ChatUtils cu = new ChatUtils();
	
	public ItemMailCommand(ItemMail instance){
		plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		Player player = null;
		if(sender instanceof Player){
			player = (Player)sender;
		}
		
		if(args.length == 0){
			new MailBox(player, plugin).getMailAtPage(1);
		}else if(args.length == 1){
			if(cu.isNum(args[0])){
				int page = Integer.parseInt(args[0]);
				new MailBox(player, plugin).getMailAtPage(page);
			}
		}
		return true;
	}

}
