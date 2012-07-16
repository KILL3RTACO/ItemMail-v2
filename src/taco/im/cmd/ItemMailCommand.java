package taco.im.cmd;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import taco.im.ItemMail;
import taco.im.Mail;
import taco.im.MailBox;
import taco.im.Request;
import taco.im.RequestBox;
import taco.im.util.ChatUtils;
import taco.im.util.ItemNames;

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
		
		if(args.length == 0){																								// /im 
			new MailBox(player, plugin).getMailAtPage(1);
		}else if(args.length == 1){
			if(cu.isNum(args[0])){																							// im [#]
				int page = Integer.parseInt(args[0]);
				new MailBox(player, plugin).getMailAtPage(page);
			}else{
				if(args[0].equalsIgnoreCase("?") || args[0].equalsIgnoreCase("help")){
					plugin.sendHelp(player);
				}else if(args[0].equalsIgnoreCase("open") || args[0].equalsIgnoreCase("o") || args[0].equalsIgnoreCase("read") // /im open
						|| args[0].equalsIgnoreCase("r")){
					MailBox mb = new MailBox(player, plugin);
					Mail mail = Mail.getMailFromTable(player, mb.getUnopenedCount(), plugin);
					if(mail == null){
						player.sendMessage(cu.mailNonExistant);
					}else{
						mail.open();
					}
				}else if(args[0].equalsIgnoreCase("delete") || args[0].equalsIgnoreCase("del") || args[0].equalsIgnoreCase("d")){ // /im del
					MailBox mb = new MailBox(player, plugin);
					Mail m = Mail.getMailFromTable(player, mb.getUnopenedCount(), plugin);
					m.trash();
				}else if(args[0].equalsIgnoreCase("requests") || args[0].equalsIgnoreCase("reqs") || 		// /im reqs
						args[0].equalsIgnoreCase("rs")){
					new RequestBox(player, plugin).getRequestsAtPage(1);
				}else if(args[0].equalsIgnoreCase("accept")){												// /im accept
					RequestBox rb = new RequestBox(player, plugin);
					Request r = Request.getRequestFromTable(player, rb.getUnansweredCount(), plugin);
					if(r == null){
						player.sendMessage(cu.requestNonExistant);
					}else{
						r.accept();
					}
				}else if(args[0].equalsIgnoreCase("decline")){												// /im decline
					RequestBox rb = new RequestBox(player, plugin);
					Request r = Request.getRequestFromTable(player, rb.getUnansweredCount(), plugin);
					if(r == null){
						player.sendMessage(cu.requestNonExistant);
					}else{
						r.decline();
					}
				}else{
					player.sendMessage(cu.invalidArgs);
				}
			}
		}else if(args.length == 2){
			if(args[0].equalsIgnoreCase("send") || args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("gift") // /im send <p>
					|| args[0].equalsIgnoreCase("g")){
				ItemStack items = player.getItemInHand();
				if(items.getType() == Material.AIR) player.sendMessage(cu.format("%cYou are not holding anything", true));
				else{
					Mail m = new Mail(player.getName(), args[1], items, plugin);
					m.send();
				}
			}else if(args[0].equalsIgnoreCase("open") || args[0].equalsIgnoreCase("o") || args[0].equalsIgnoreCase("read") // /im open [#]
					|| args[0].equalsIgnoreCase("r")){
				if(cu.isNum(args[1])){
					Mail mail = Mail.getMailFromTable(player, Integer.parseInt(args[1]), plugin);
					if(mail == null){
						player.sendMessage(cu.mailNonExistant);
					}else{
						mail.open();
					}
				}else{
					player.sendMessage(cu.notNum(args[1]));
				}
			}else if(args[0].equalsIgnoreCase("delete") || args[0].equalsIgnoreCase("del") || args[0].equalsIgnoreCase("d")){ // /im del [#]
				if(cu.isNum(args[1])){
					Mail mail = Mail.getMailFromTable(player, Integer.parseInt(args[1]), plugin);
					if(mail == null){
						player.sendMessage(cu.mailNonExistant);
					}else{
						mail.trash();
					}
				}else if(args[1].equalsIgnoreCase("*") || args[1].equalsIgnoreCase("all")){
						MailBox mb = new MailBox(player, plugin);
						mb.trashAllMail();
				}else{
					player.sendMessage(cu.invalidArgs);
				}
			}else if(args[0].equalsIgnoreCase("requests") || args[0].equalsIgnoreCase("reqs") || 			// /im reqs [#]
					args[0].equalsIgnoreCase("rs")){
				if(cu.isNum(args[1])){
					new RequestBox(player, plugin).getRequestsAtPage(Integer.parseInt(args[1]));
				}else{
					player.sendMessage(cu.notNum(args[1]));
				}
			}else if(args[0].equalsIgnoreCase("accept")){
				if(cu.isNum(args[1])){
					Request r = Request.getRequestFromTable(player, Integer.parseInt(args[1]), plugin);
					if(r == null){
						player.sendMessage(cu.requestNonExistant);
					}else{
						r.accept();
					}
				}else{
					player.sendMessage(cu.notNum(args[1]));
				}
			}else if(args[0].equalsIgnoreCase("decline")){													// /im decline [#]
				if(cu.isNum(args[1])){
					Request r = Request.getRequestFromTable(player, Integer.parseInt(args[1]), plugin);
					if(r == null){
						player.sendMessage(cu.requestNonExistant);
					}else{
						r.decline();
					}
				}else if(args[1].equalsIgnoreCase("*") || args[1].equalsIgnoreCase("all")){					// /im decline *
					RequestBox rb = new RequestBox(player, plugin);
					rb.declineAllMail();
				}else{
					player.sendMessage(cu.notNum(args[1]));
				}
			}else{
				player.sendMessage(cu.invalidArgs);
			}
		}else if(args.length == 3){
			if(args[0].equalsIgnoreCase("send") || args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("gift") // /im send <p> [i] 1
					|| args[0].equalsIgnoreCase("g")){
				ItemStack items = ItemNames.getItemStackFromString(args[2], "1");
				if(items == null){
					player.sendMessage(cu.format("%c'%f" + args[2] + " 1%c' does not make an ItemStack", true));
				}else{
					Mail m = new Mail(player.getName(), args[1], items, plugin);
					m.send();
				}
			}else if(args[0].equalsIgnoreCase("request") || args[0].equalsIgnoreCase("req") || 			// /im req <p> [i] 1
					args[0].equalsIgnoreCase("r")){
				ItemStack items = ItemNames.getItemStackFromString(args[2], "1");
				if(items == null){
					player.sendMessage(cu.format("%c'%f" + args[2] + " 1%c' does not make an ItemStack", true));
				}else{
					Request r = new Request(player.getName(), args[1], items, plugin);
					r.send();
				}
			}else{
				player.sendMessage(cu.invalidArgs);
			}
		}else if(args.length == 4){
			if(args[0].equalsIgnoreCase("send") || args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("gift") // /im send <p> [i] [#]
					|| args[0].equalsIgnoreCase("g")){
				ItemStack items = ItemNames.getItemStackFromString(args[2], args[3]);
				if(items == null){
					player.sendMessage(cu.format("%c'%f" + args[2] + " 1%c' does not make an ItemStack", true));
				}else{
					Mail m = new Mail(player.getName(), args[1], items, plugin);
					m.send();
				}
			}else if(args[0].equalsIgnoreCase("request") || args[0].equalsIgnoreCase("req") || 			// /im req <p> [i] [#]
					args[0].equalsIgnoreCase("r")){
				ItemStack items = ItemNames.getItemStackFromString(args[2], args[3]);
				if(items == null){
					player.sendMessage(cu.format("%c'%f" + args[2] + " 1%c' does not make an ItemStack", true));
				}else{
					Request r = new Request(player.getName(), args[1], items, plugin);
					r.send();
				}
			}else{
				player.sendMessage(cu.invalidArgs);
			}
		}
		return true;
	}

}
