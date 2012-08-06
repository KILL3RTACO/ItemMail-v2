package taco.im.cmd.subcommands;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import taco.im.MailBoxType;
import taco.im.cmd.ItemMailBoxCommand;
import taco.im.request.Request;
import taco.im.request.RequestBox;

public class AcceptSubCommand extends ItemMailBoxCommand{
	
	public AcceptSubCommand(){
		this.aliases = new String[]{"accept", "acc", "yes"};
	}

	@Override
	public void execute(MailBoxType boxType) {
		RequestBox rb = (RequestBox)boxType;
		if(rb.getUnreadCount() > 0){
			for(int i=rb.getUnreadCount(); i>=0; i--){
				Request req = Request.getRequestFromTable(rb.getOwner(), i);
				try {
					req.accept();
				} catch (Exception e){
					break;
				}
			}
			//TODO message("accepted as much as possible")
		}
		//TODO message("no requests to accept")
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
