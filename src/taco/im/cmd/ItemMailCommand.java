package taco.im.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import taco.im.ItemMail;
import taco.im.obj.ItemMailPlayer;
import taco.im.util.MailMan;

public class ItemMailCommand implements CommandExecutor{
	
	private ItemMail plugin = null;
	private MailMan mailman = null;
	
	public ItemMailCommand(ItemMail instance){
		plugin = instance;
		mailman = new MailMan(plugin);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		ItemMailPlayer player;
		if(sender instanceof Player){
			player = new ItemMailPlayer((Player)sender, plugin);
			if(args.length == 0){
				//TODO check all mail
			}else if(args.length == 1){
				if(plugin.cu.isNum(args[0])){
					//TODO get mail on page
				}else if(args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?")){
					//TODO send plugin help
				}else if(args[0].equalsIgnoreCase("info")){
					//TODO send plugin info
				}else if(args[0].equalsIgnoreCase("open") || args[0].equalsIgnoreCase("o")){ // /im open [1]
					//TODO open first mail, if any
				}else{
					//TODO send invalid args message
				}
			}else if(args.length == 2){
				if(args[0].equalsIgnoreCase("open") || args[0].equalsIgnoreCase("o")){ // /im open [#]
					//TODO open mail if exists
				}else if(args[0].equalsIgnoreCase("send") || args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("gift")
						|| args[0].equalsIgnoreCase("g")){ // /im send [player] [material] [1]
					//TODO send mail, if other player has the permission "ItemMail.action.open"
				}else{
					//TODO send invalid args message
				}
			}else if(args.length == 3){
				if(args[0].equalsIgnoreCase("send") || args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("gift")
						|| args[0].equalsIgnoreCase("g")){ // /im send [player] [material] [#]
					//TODO send mail, if other player has the permission "ItemMail.action.open"
				}else{
					//TODO send invalid args message
				}
			}
		}
		return false;
	}

}
