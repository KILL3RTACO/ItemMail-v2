package taco.im.cmd.subcommands;

import org.bukkit.entity.Player;

import taco.im.MailBoxType;
import taco.im.cmd.ItemMailBoxCommand;
import taco.im.request.Request;

public class AcceptSubCommand extends ItemMailBoxCommand{
	
	public AcceptSubCommand(){
		this.aliases = new String[]{"accept"};
	}

	@Override
	public void execute(MailBoxType boxType) {
		//Nothing
	}

	@Override
	public void execute(Player p, int index) {
		Request req = Request.getRequestFromTable(p, index);
		try {
			req.accept();
		} catch (Exception e) {
			p.sendMessage(e.getMessage());
		}
	}
	
}
