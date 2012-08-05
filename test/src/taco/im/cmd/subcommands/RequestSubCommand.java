package taco.im.cmd.subcommands;

import org.bukkit.entity.Player;

import taco.im.ItemMail;
import taco.im.MailType;
import taco.im.cmd.ItemMailCommand;
import taco.im.request.Request;

public class RequestSubCommand extends ItemMailCommand{
	
	public RequestSubCommand(){
		this.aliases = new String[]{"request", "req", "r"};
	}

	public void execute(MailType mType){
		Request req = (Request)mType;
		Player player = ItemMail.server.getPlayer(req.getSender());
		try {
			req.send();
		} catch (Exception e) {
			player.sendMessage(e.getMessage());
		}
	}
	
}
