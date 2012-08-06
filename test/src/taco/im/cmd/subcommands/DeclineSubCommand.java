package taco.im.cmd.subcommands;

import org.bukkit.entity.Player;

import taco.im.MailBoxType;
import taco.im.cmd.ItemMailBoxCommand;
import taco.im.request.Request;

public class DeclineSubCommand extends ItemMailBoxCommand{

	public DeclineSubCommand(){
		this.aliases = new String[]{"decline", "deny", "dec", "no", "d"};
	}
	
	public void execute(Player p, int index){
		Request req = Request.getRequestFromTable(p, index);
		try {
			req.decline();
		} catch (Exception e) {
			p.sendMessage(e.getMessage());
		}
	}
	
	public void execute(MailBoxType box){
		try {
			box.deleteAll();
		} catch (Exception e) {
			box.getOwner().sendMessage(e.getMessage());
		}
	}
}
