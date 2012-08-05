package taco.im;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;

import taco.im.mail.Mail;
import taco.im.request.Request;
import taco.im.util.ChatUtils;

public class Notification {
	
	private ChatUtils cu = new ChatUtils();
	
	private Player receiver = null;
	private String message;
	private boolean hasNotificationOn = false;
	
	public Notification(MailType type){
		if(type instanceof Mail){
			this.receiver = ItemMail.server.getPlayer(type.getReceiver());
			this.message = "&aYou have new mail from &2" + type.getSender();
				if(hasMailNotificationOn(type.getReceiver())){
					hasNotificationOn = true;
				}
		}else if(type instanceof Request){
			this.receiver = ItemMail.server.getPlayer(type.getReceiver());
			this.message = "&aYou have a new request from &2" + type.getSender();
			if(hasRequestNotificationOn(type.getReceiver())){
				hasNotificationOn = true;
			}
		}
	}
	
	public void send(){
		if(receiver != null){
			if(hasNotificationOn){
				receiver.sendMessage(cu.formatColors(message));
			}
		}
	}
	
	private boolean hasMailNotificationOn(String player){
		String sql = "SELECT * FROM `im_notifcations` WHERE `type`='mail' AND `status`='";
		return doQuery(sql);
	}

	private boolean hasRequestNotificationOn(String player){
		String sql = "SELECT * FROM `im_notifcations` WHERE `type`='request' AND `status`='on'";
		return doQuery(sql);
	}
	
	private boolean doQuery(String sql) {
		try {
			ResultSet rs = ItemMail.db.getResultSet(sql);
			if(rs.next()){
				return true;
			}else{
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
