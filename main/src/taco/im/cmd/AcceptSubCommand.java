package taco.im.cmd;

import org.bukkit.entity.Player;

import taco.im.MailBoxType;
import taco.im.request.Request;

public class AcceptSubCommand {
	
	public AcceptSubCommand(Player p, int index){
		Request req = Request.getRequestFromTable(p, index);
		try {
			req.accept();
		} catch (Exception e) {
			p.sendMessage(e.getMessage());
		}
	}
	
	public AcceptSubCommand(MailBoxType box){
		//TODO accept all requests if possible
	}
	
}
