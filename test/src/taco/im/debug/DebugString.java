package taco.im.debug;

import taco.im.MailType;
import taco.im.util.ChatUtils;
import taco.im.util.ItemNames;

public enum DebugString {

	MAIL_SENT_DEBUG("%sender: %itemAmount %itemName sent to %receiver"),
	REQUEST_SENT_DEBUG("%sender: Asked %receiver for %itemAmount %itemName");
	
	private String rawHeader = "&7[ItemMailDebug] ";
	private ChatUtils cu = new ChatUtils();
	private String rawMessage;
	private String message;
	
	private DebugString(String rawMessage){
		this.rawMessage = rawMessage;
	}
	
	public String getMessage(MailType mType){
		formatMessage(mType);
		return message;
	}

	private void formatMessage(MailType mType) {
		message = rawMessage.replaceAll("%sender", mType.getSender())
				.replaceAll("%itemAmount", "" + mType.getItemAmount())
				.replaceAll("%itemName", ItemNames.getDisplayName(mType.getItems()))
				.replaceAll("%receiver", mType.getReceiver());
		message = cu.formatColors(rawHeader + message);
	}
	
}
