package taco.im.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import taco.im.ItemMail;
import taco.im.cmd.subcommands.AcceptSubCommand;
import taco.im.cmd.subcommands.BlacklistSubCommand;
import taco.im.cmd.subcommands.DeclineSubCommand;
import taco.im.cmd.subcommands.DeleteSubCommand;
import taco.im.cmd.subcommands.OpenSubCommand;
import taco.im.cmd.subcommands.RequestSubCommand;
import taco.im.cmd.subcommands.SendSubCommand;
import taco.im.mail.MailBox;
import taco.im.request.RequestBox;
import taco.im.util.ChatUtils;
import taco.im.util.ItemNames;

public class CommandHandler implements CommandExecutor {
	
	private ChatUtils cu = new ChatUtils();
	private AcceptSubCommand acceptCommand = new AcceptSubCommand();
	private BlacklistSubCommand blacklistCommand;
	private DeclineSubCommand declineCommand = new DeclineSubCommand();
	private DeleteSubCommand deleteCommand = new DeleteSubCommand();
	private OpenSubCommand openCommand = new OpenSubCommand();
	private RequestSubCommand requestCommand = new RequestSubCommand();
	private SendSubCommand sendCommand = new SendSubCommand();

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
			// /im accept {last-received}
			}else if(acceptCommand.getAliasMatch(args[0])){
				
			// /im decline {lasy-received}	
			}else if(declineCommand.getAliasMatch(args[0])){
				
			// /im delete {last-received}	
			}else if(deleteCommand.getAliasMatch(args[0])){
				
			// /im open {last-received}	
			}else if(openCommand.getAliasMatch(args[0])){
				
			// /im 	
			}
		}else if(args.length == 2){
		}else if(args.length == 3){
		}else if(args.length == 4){
			// /im send <player> [item] [#/*]
			
		}
		return true;
	}

}
