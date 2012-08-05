package taco.im.cmd.subcommands;

import org.bukkit.entity.Player;

import taco.im.MailBoxType;
import taco.im.cmd.ItemMailBoxCommand;
import taco.im.mail.Mail;

public class OpenSubCommand extends ItemMailBoxCommand{
	
	public void execute(Player p, int index){
		Mail mail = Mail.getMailFromTable(p, index);
		try {
			mail.open();
		} catch (Exception e) {
			p.sendMessage(e.getMessage());
		}
	}
	
	public void execute(MailBoxType mbType){
		//TODO open as much mail as possible
	}
	
}
