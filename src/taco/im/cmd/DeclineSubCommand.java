package taco.im.cmd;

import org.bukkit.entity.Player;

import taco.im.MailBoxType;
import taco.im.request.Request;

public class DeclineSubCommand {

	public DeclineSubCommand(Player p, int index){
		Request req = Request.getRequestFromTable(p, index);
		try {
			req.decline();
		} catch (Exception e) {
			p.sendMessage(e.getMessage());
		}
	}
	
	public DeclineSubCommand(MailBoxType box){
		try {
			box.deleteAll();
		} catch (Exception e) {
			box.getOwner().sendMessage(e.getMessage());
		}
	}
}
