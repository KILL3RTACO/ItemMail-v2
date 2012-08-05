package taco.im.event.mail;

import taco.im.event.ItemMailEvent;
import taco.im.mail.Mail;

public class MailSentEvent extends ItemMailEvent{
	
	public MailSentEvent(Mail mail){
		this.type = mail;
	}
	
	public Mail getMail(){
		return (Mail)this.type;
	}
	
	public boolean hasPlayer(){
		return type.hasPlayer;
	}

}
