package taco.im.log;

import taco.im.MailType;
import taco.im.util.ChatUtils;
import taco.im.util.ItemNames;

public enum LogString {
	
	MAIL_SENT_LOG("%sender sent %itemAmount %itemName to %receiver"),
	MAIL_OPENED_LOG("%receiver opened mail sent by %sender: %itemAmount %itemName"),
	REQUEST_SENT_LOG("%sender requested %receiver to send %itemAmount %itemName to them"),
	REQUEST_DECLINED_LOG("%reciever declined %sender's ");
	//No string for REQUEST_ACCEPTED exists because when a Request is accepted, the resulting items are sent as Mail
	
	private ChatUtils cu = new ChatUtils();
	private MailType mail;
	private String rawHeader = "&7[ItemMail GlobalLog] ";
	private String rawMessage;
	private String message;
	
	private LogString(String rawMessage){
		this.rawMessage = rawMessage;
	}
	
	public String getMessage(MailType mType){
		this.mail = mType;
		formatMessage();
		return message;
	}

	private void formatMessage() {
		message = rawMessage.replaceAll("%sender", mail.getSender())
				.replaceAll("%receiver", mail.getReceiver())
				.replaceAll("%itemAmount", "" + mail.getItemAmount())
				.replaceAll("%itemName", ItemNames.getDisplayName(mail.getItems()));
		message = cu.formatColors(rawHeader + message);
	}
}
