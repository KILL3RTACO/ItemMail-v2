package taco.im.request;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import taco.im.ItemMail;
import taco.im.MailBoxType;
import taco.im.MailType;
import taco.im.PermissionsHelper;
import taco.im.exception.InvalidGameModeException;
import taco.im.exception.InvalidPermissionsException;
import taco.im.exception.RequestNonExistantException;
import taco.im.mail.Mail;
import taco.im.util.ChatUtils;
import taco.im.util.ItemNames;


public class Request implements MailType {

	private ChatUtils cu = new ChatUtils();
	private String sender, receiver;
	private ItemStack items;
	
	public Request(String sender, String receiver, ItemStack items){
		this.sender = sender;
		this.receiver = receiver;
		this.items = items;
	}

	public int getItemAmount(){
		return items.getAmount();
	}

	public String getSender() {
		return sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public ItemStack getItems() {
		return items;
	}

	public Material getItemType() {
		return items.getType();
	}
	
	public int getItemTypeId() {
		return items.getTypeId();
	}
	
	public int getItemDamage() {
		return items.getDurability();
	}

	public boolean send() throws InvalidPermissionsException{
		Player pSender = ItemMail.server.getPlayer(sender);
		Player pReceiver = ItemMail.server.getPlayer(receiver);
		if(pSender.hasPermission(PermissionsHelper.REQUEST_PERMISSION)){
			if(pReceiver == null){
				OfflinePlayer op = ItemMail.server.getOfflinePlayer(receiver);
				if(op.hasPlayedBefore()){
					if(getItemAmount() == 0){
						pSender.sendMessage(cu.formatColors("&cYou can't ask for &60&c of something"));
						return false;
					}else{
						doSendStatement(op.getName());
						//TODO place-holder
						pSender.sendMessage(cu.formatColors("&aAsked &d" + op.getName() + "&a for &2" + getItemAmount() + 
								" " + ItemNames.getDisplayName(items)));
						return true;
					}
				}else{
					pSender.sendMessage(cu.formatColors("&cPlayer '&f" + receiver + "&c' does not exist or has not played on this server before"));
					return false;
				}
			}else{
				if(pReceiver.getName().equalsIgnoreCase(pSender.getName())){
					pSender.sendMessage(cu.formatColors("&cYou can't ask yourself for items"));
					return false;
				}else{
					if(getItemAmount() == 0){
						//TODO place-holder
						pSender.sendMessage(cu.formatColors("&cYou can't ask for &60&c of something"));
						return false;
					}else{
						doSendStatement(pReceiver.getName());
						pSender.sendMessage(cu.formatColors("&aAsked &d" + pReceiver.getName() + "&a for &2" + getItemAmount() + 
								" " + ItemNames.getDisplayName(items)));
						return true;
					}
					
				}
			}
		}else{
			throw new InvalidPermissionsException("");
		}
	}
	
	private void doReadStatement(){
		try {
			String sql = "SELECT * FROM `item_mail` WHERE `receiver`='" + receiver + "'AND `type`='request' AND `read`='0'";
			ResultSet rs = ItemMail.db.getResultSet(sql);
			int tId = getItemTypeId();
			int d = getItemDamage();
			int a = getItemAmount();
			while(rs.next()){
				if(rs.getInt(5) == tId && rs.getInt(6) == d && rs.getInt(7) == a){
					sql = "UPDATE `item_mail` SET `read`='1' WHERE `id`='" + rs.getInt(1) + "'";
					ItemMail.db.statement(sql);
					break;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void doSendStatement(String name){
		String sendr = ItemMail.server.getPlayer(sender).getName();
		String recvr = name;
		try {
			String sql = "INSERT INTO `item_mail` (`sender`, `receiver`, `type`, `item_id`, `damage`, `amount`) VALUES('" + sendr + "', " +
					"'" + recvr + "', 'request', '" + getItemTypeId() + "', '" + getItemDamage() + "', '" + getItemAmount() + "')";
			ItemMail.db.statement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void accept() throws InvalidPermissionsException, InvalidGameModeException, RequestNonExistantException{
		if(this != null){
			Player player = ItemMail.server.getPlayer(receiver);
			if(player.hasPermission(PermissionsHelper.ACCEPT_PERMISSION)){
				Mail mail = new Mail(receiver, sender, items);
				if(mail.send()){
					//TODO place-holder
					player.sendMessage(cu.formatColors("&aRequest accepted"));
					doReadStatement();
				}
			}else{
				throw new InvalidPermissionsException("");
			}
		}else{
			throw new RequestNonExistantException("");
		}
	}
	public void decline() throws InvalidPermissionsException{
		Player player = ItemMail.server.getPlayer(receiver);
		if(player.hasPermission(PermissionsHelper.DECLINE_PERMISSION)){
			//TODO place-holder
			player.sendMessage(cu.formatColors("&aRequest denied"));
			doReadStatement();
		}else{
			throw new InvalidPermissionsException("");
		}
	}
	
	public static Request getRequestFromTable(Player receiver, int index){
		String sql = "SELECT * FROM `item_mail`	WHERE `type`='request' AND `receiver`='" + receiver.getName() + "' AND `read`='0'";
		try {
			ResultSet rs = ItemMail.db.getResultSet(sql);
			int row = 0;
			while(rs.next()){
				row++;
				if(row == index){
					String s = rs.getString(2);
					String r = rs.getString(3);
					int id = rs.getInt(5);
					int d = rs.getInt(6);
					int a = rs.getInt(7);
					return new Request(s, r, new ItemStack(id, a,(short)d));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	@Override
	public MailBoxType getBox(){
		return new RequestBox(ItemMail.server.getPlayer(receiver));
	}
}
