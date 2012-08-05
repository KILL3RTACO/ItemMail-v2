package taco.im.cmd.subcommands;

import org.bukkit.entity.Player;

import taco.im.MailBoxType;
import taco.im.cmd.ItemMailBoxCommand;
import taco.im.mail.Mail;
import taco.im.util.ChatUtils;

public class DeleteSubCommand extends ItemMailBoxCommand{

	ChatUtils cu = new ChatUtils();
	
	public DeleteSubCommand(){
		this.aliases = new String[]{"delete", "del"};
	}
	
	public void execute(Player p, int index){
		Mail mail = Mail.getMailFromTable(p, index);
		try {
			mail.trash();
			p.sendMessage(cu.formatColors("&aMessage deleted"));
		} catch (Exception e) {
			p.sendMessage(e.getMessage());
		}
	}
	
	public void execute(MailBoxType box){
		try {
			box.deleteAll();
		} catch (Exception e) {
			
		}
	}
	
}
