package taco.im;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import taco.im.util.ChatUtils;
import taco.im.util.ItemNames;


public class Request implements BaseMail {

	private ChatUtils cu = new ChatUtils();
	private String sender, receiver;
	private ItemStack items;
	private ItemMail plugin = null;
	
	public Request(String sender, String receiver, ItemStack items, ItemMail instance){
		this.sender = sender;
		this.receiver = receiver;
		this.items = items;
		plugin = instance;
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

	public boolean send(){
		Player pSender = plugin.getServer().getPlayer(sender);
		Player pReceiver = plugin.getServer().getPlayer(receiver);
		if(pReceiver == null){
			OfflinePlayer op = plugin.getServer().getOfflinePlayer(receiver);
			if(op.hasPlayedBefore()){
				if(getItemAmount() == 0){
					pSender.sendMessage(cu.format("%cYou can't ask for %60%c of something", true));
					return false;
				}else{
					doSendStatement(op.getName());
					pSender.sendMessage(cu.format("%aAsked %d" + op.getName() + "%a for %2" + getItemAmount() + 
							" " + ItemNames.getDisplayName(items), true));
					return true;
				}
			}else{
				pSender.sendMessage(cu.format("%cPlayer '%f" + receiver + "%c' does not exist or has not played on this server before", true));
				return false;
			}
		}else{
			if(pReceiver.getName().equalsIgnoreCase(pSender.getName())){
				pSender.sendMessage(cu.format("%cYou can't ask yourself for items", true));
				return false;
			}else{
				if(getItemAmount() == 0){
					pSender.sendMessage(cu.format("%cYou can't ask for %60%c of something", true));
					return false;
				}else{
					doSendStatement(pReceiver.getName());
					pSender.sendMessage(cu.format("%aAsked %d" + pReceiver.getName() + "%a for %2" + getItemAmount() + 
							" " + ItemNames.getDisplayName(items), true));
					return true;
				}
				
			}
		}
	}
	
	private void doReadStatement(){
		try {
			String sql = "SELECT * FROM `item_mail` WHERE `receiver`='" + receiver + "'AND `type`='request' AND `read`='0'";
			ResultSet rs = plugin.mysql.getResultSet(sql);
			int tId = getItemTypeId();
			int d = getItemDamage();
			int a = getItemAmount();
			while(rs.next()){
				if(rs.getInt(5) == tId && rs.getInt(6) == d && rs.getInt(7) == a){
					sql = "UPDATE `item_mail` SET `read`='1' WHERE `id`='" + rs.getInt(1) + "'";
					plugin.mysql.statement(sql);
					break;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void doSendStatement(String name){
		String sendr = plugin.getServer().getPlayer(sender).getName();
		String recvr = name;
		try {
			String sql = "INSERT INTO `item_mail` (`sender`, `receiver`, `type`, `item_id`, `damage`, `amount`) VALUES('" + sendr + "', " +
					"'" + recvr + "', 'request', '" + getItemTypeId() + "', '" + getItemDamage() + "', '" + getItemAmount() + "')";
			plugin.mysql.statement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void accept(){
		Player player = plugin.getServer().getPlayer(receiver);
		if(player.hasPermission(ItemMail.ACCEPT_PERMISSION)){
			Mail mail = new Mail(receiver, sender, items, plugin);
			if(mail.send()){
				player.sendMessage(cu.format("%aRequest accepted", true));
				doReadStatement();
			}
		}else{
			player.sendMessage(cu.invalidPerm);
		}
	}
	public void decline(){
		Player player = plugin.getServer().getPlayer(receiver);
		if(player.hasPermission(ItemMail.DECLINE_PERMISSION)){
			player.sendMessage(cu.format("%aRequest denied", true));
			doReadStatement();
		}else{
			player.sendMessage(cu.invalidPerm);
		}
	}
	
	public static Request getRequestFromTable(Player receiver, int index, ItemMail im){
		String sql = "SELECT * FROM `item_mail`	WHERE `type`='request' AND `receiver`='" + receiver.getName() + "' AND `read`='0'";
		try {
			ResultSet rs = im.mysql.getResultSet(sql);
			int row = 0;
			while(rs.next()){
				row++;
				if(row == index){
					String s = rs.getString(2);
					String r = rs.getString(3);
					int id = rs.getInt(5);
					int d = rs.getInt(6);
					int a = rs.getInt(7);
					return new Request(s, r, new ItemStack(id, a,(short)d), im);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
}
