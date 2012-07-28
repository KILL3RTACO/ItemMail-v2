package taco.im.cmd;

import org.bukkit.entity.Player;

import taco.im.MailBoxType;
import taco.im.mail.Mail;
import taco.im.util.ChatUtils;

public class DeleteSubCommand {

	ChatUtils cu = new ChatUtils();
	
	public DeleteSubCommand(Player p, int index){
		Mail mail = Mail.getMailFromTable(p, index);
		try {
			mail.trash();
			p.sendMessage(cu.formatColors("&aMessage deleted"));
		} catch (Exception e) {
			p.sendMessage(e.getMessage());
		}
	}
	
	/*
	 * This method is used to delete all mail from a given Mailbox or RequestBox
	 */
	
	public DeleteSubCommand(MailBoxType box){
		try {
			box.deleteAll();
		} catch (Exception e) {
			
		}
	}
	
}
