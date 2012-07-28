package taco.im.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import taco.im.ItemMail;
import taco.im.mail.MailBox;
import taco.im.request.RequestBox;
import taco.im.util.ChatUtils;
import taco.im.util.ItemNames;

public class ItemMailCommand implements CommandExecutor {
	
	private ChatUtils cu = new ChatUtils();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		Player player = null;
		if(sender instanceof Player){
			player = (Player)sender;
		}
		
		if(args.length == 0){
			// /im
			new MailBox(player).getElementsAtPage(1);
		}else if(args.length == 1){
			// /im [#]
			if(cu.isNum(args[0])){
				new MailBox(player).getElementsAtPage(Integer.parseInt(args[0]));
			// /im ?
			}else if(args[0].equalsIgnoreCase("?") || args[0].equalsIgnoreCase("help")){
				ItemMail.plugin.sendHelp(player, 1);
			// /im accept
			}else if(args[0].equalsIgnoreCase("accept")){
				new AcceptSubCommand(player, new RequestBox(player).getUnreadCount());
			// /im decline
			}else if(args[0].equalsIgnoreCase("decline")){
				new DeclineSubCommand(player, new RequestBox(player).getUnreadCount());
			// /im requests
			}else if(args[0].equalsIgnoreCase("requests") || args[0].equalsIgnoreCase("reqs") || args[0].equalsIgnoreCase("rs")){
				new RequestBox(player).getElementsAtPage(1);
			}
		}else if(args.length == 2){
			// /im send <player> [items-in-hand]
			if(args[0].equalsIgnoreCase("send") || args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("gift")
					|| args[0].equalsIgnoreCase("g")){
				new SendSubCommand(player, args[1], player.getItemInHand());
			// /im accept [#/*]
			}else if(args[0].equals("accept")){
				if(cu.isNum(args[1])){
					new AcceptSubCommand(player, Integer.parseInt(args[1]));
				}else if(args[1].equalsIgnoreCase("*")){
					//TODO accept all if possible
				}
			// /im decline [#/*]	
			}else if(args[0].equalsIgnoreCase("decline")){
				if(cu.isNum(args[1])){
					new DeclineSubCommand(player, Integer.parseInt(args[1]));
				}else if(args[1].equalsIgnoreCase("*")){
					new DeclineSubCommand(new RequestBox(player));
				}
			// /im requests [#]
			}else if(args[0].equalsIgnoreCase("requests") || args[0].equalsIgnoreCase("reqs") || args[0].equalsIgnoreCase("rs")){
				if(cu.isNum(args[1])){
					new RequestBox(player).getElementsAtPage(Integer.parseInt(args[1]));
				}
			}
			
		}else if(args.length == 3){
			// /im send <player> [item]
			if(args[0].equalsIgnoreCase("send") || args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("gift")
					|| args[0].equalsIgnoreCase("g")){
				ItemStack items = ItemNames.getItemStackFromString(args[2], "1");
				if(items == null){
					player.sendMessage(cu.formatColors("&c\"&6" + args[2] + "\"&c not found"));
				}else{
					new SendSubCommand(player, args[1], items);
				}
			// /im request <player> <item>
			}else if(args[0].equalsIgnoreCase("request") || args[0].equalsIgnoreCase("req") || args[0].equalsIgnoreCase("r")){
				ItemStack items = ItemNames.getItemStackFromString(args[2], "1");
				if(items == null){
					player.sendMessage(cu.formatColors("&c\"&6" + args[2] + "\"&c not found"));
				}else{
					new RequestSubCommand(player, args[1], items);
				}
			}
		}else if(args.length == 4){
			// /im send <player> [item] [#/*]
			
		}
		return true;
	}

}
