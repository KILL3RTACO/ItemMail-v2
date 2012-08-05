package taco.im.cmd;

import org.bukkit.entity.Player;

import taco.im.mail.Mail;

public class OpenSubCommand {
	
	public OpenSubCommand(Player p, int index){
		Mail mail = Mail.getMailFromTable(p, index);
		try {
			mail.open();
		} catch (Exception e) {
			p.sendMessage(e.getMessage());
		}
	}
	
	/*
	 * This constructor is used to open all mail
	 */
	
	public OpenSubCommand(Player p){
		//TODO loop through all mail and open if possible
	}
	
}
