package taco.im;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import taco.im.util.ChatUtils;


public class Mail implements BaseMail{

	private ChatUtils cu = new ChatUtils();
	private String sender, receiver;
	private ItemStack items;
	private ItemMail plugin = null;
	
	public Mail(String sender, String receiver, ItemStack items, ItemMail instance){
		this.sender = sender;
		this.receiver = receiver;
		this.items = items;
		plugin = instance;
	}
	
	public int getItemAmount(){
		return items.getAmount();
	}
	
	public ItemStack getItems(){
		return items;
	}
	
	public int getItemDamage(){
		return items.getDurability();
	}
	
	public Material getItemType(){
		return items.getType();
	}
	
	public int getItemTypeId(){
		return items.getTypeId();
	}
	
	public String getSender(){
		return sender;
	}
	
	public String getReceiver(){
		return receiver;
	}
	
	public void trash(){
		Player p = plugin.getServer().getPlayer(receiver);
		if(p.hasPermission(ItemMail.DELETE_PERMISSION)){
			doReadStatement();
			p.sendMessage(cu.format("%aPackage trashed", true));
		}else{
			p.sendMessage(cu.invalidPerm);
		}
	}
	
	public void send(){
		Player pSender = plugin.getServer().getPlayer(sender);
		Player pReceiver = plugin.getServer().getPlayer(receiver);
		if(pReceiver == null){
			OfflinePlayer op = plugin.getServer().getOfflinePlayer(receiver);
			if(op.hasPlayedBefore()){
				if(testInventory() && testSendConditions(pSender)){
					doSendStatement(true);
					pSender.getInventory().removeItem(items);
					pSender.sendMessage(cu.format("%aSent %2" + getItemAmount() + " " + getItemType() + " %ato %d" + pSender.getName(), true));
				}else{
					pSender.sendMessage(cu.format("%cYou do not have %6" + getItemAmount() + " " + getItemType(), true));
				}
			}else{
				pSender.sendMessage(cu.format("%cPlayer '%f" + receiver + "%c' does not exist or has not played on this server before", true));
			}
		}else{
			if(testInventory() && testSendConditions(pSender)){
				if(pSender.getName() == pReceiver.getName()){
					pSender.sendMessage(cu.format("%cYou can't send items to yourself", true));
				}else{
					doSendStatement(true);
					pSender.getInventory().removeItem(items);
					pSender.sendMessage(cu.format("%aSent %2" + getItemAmount() + " " + getItemType() + " %ato %d" + pSender.getName(), true));
				}
			}
		}

	}
	
	private boolean testInventory() {
		Player player = plugin.getServer().getPlayer(sender);
		int needed = getItemAmount();
		int amount = 0;
		for(ItemStack i : player.getInventory()){
			if(i != null){
				if(i.getType() == items.getType() && i.getDurability() == items.getDurability()){
					amount += i.getAmount();
				}
			}
		}
		if(amount >= needed){
			return true;
		}else{
			player.sendMessage(cu.format("%cYou don't have %6" + items.getAmount() + " " + items.getType(), true));
			return false;
		}
	}
	
	private boolean testSendConditions(Player sender){
		if(sender.hasPermission(ItemMail.SEND_PERMISSION)){
			if(sender.getGameMode() == GameMode.CREATIVE){
				sender.sendMessage(cu.format("%cPlease change your gamemode to survival to send mail", true));
				return false;
			}else{
				if(getItemType() == Material.AIR){
					sender.sendMessage(cu.format("%cYou can't send %6AIR", true));
					return false;
				}else if(getItemAmount() == 0){
					sender.sendMessage(cu.format("%cYou can't send %60%c of something", true));
					return false;
				}else{
					return true;
				}
			}
		}else{
			sender.sendMessage(cu.invalidPerm);
			return false;
		}
	}
	
	private boolean hasRoom(){
		Player player = plugin.getServer().getPlayer(receiver);
		int space = 0, needed = 0;
		for(ItemStack i : player.getInventory()){
			if(i == null){
				space += items.getMaxStackSize();
			}else if(i.getType() == getItemType() && i.getDurability() == getItemDamage()){
				space += items.getMaxStackSize() - i.getAmount();
			}
		}
		if(space >= needed){
			return true;
		}else{
			return false;
		}
	}

	public void open(){
		Player player = plugin.getServer().getPlayer(receiver);
		if(player.hasPermission(ItemMail.OPEN_PERMISSION)){
			player.sendMessage(cu.format("%aOpening...", true));
			if(player.getGameMode() != GameMode.CREATIVE && hasRoom()){
				doReadStatement();
				player.getInventory().addItem(items);
				player.sendMessage(cu.format("%6CONTENTS: %2" + getItemAmount() + " " + getItemType(), true));
			}else if(!hasRoom()){
				player.sendMessage(cu.format("%cYou do not have room in your inventory to carry %6" + getItemAmount() + " " + getItemType(), true));
			}else{
				player.sendMessage(cu.format("%cPlease change your gamemode to survival mode to get mail", true));
			}
		}else{
			player.sendMessage(cu.invalidPerm);
		}
	}
	
	private void doReadStatement(){
		try {
			String sql = "SELECT * FROM `item_mail` WHERE `receiver`='" + receiver + "' AND `read`='0'";
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
	
	private void doSendStatement(boolean offline){
		String sendr, recvr = "";
		if(offline){
			recvr = plugin.getServer().getOfflinePlayer(receiver).getName();
		}else{
			recvr = plugin.getServer().getPlayer(receiver).getName();
		}
		sendr = plugin.getServer().getPlayer(sender).getName();
		try {
			String sql = "INSERT INTO `item_mail` (`sender`, `receiver`, `type`, `item_id`, `damage`, `amount`) VALUES('" + sendr + "', " +
				"'" + recvr + "', 'gift', '" + getItemTypeId() + "', '" + getItemDamage() + "', '" + getItemAmount() + "')";
			plugin.mysql.statement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Mail getMailFromTable(Player receiver, int index, ItemMail im){
		String sql = "SELECT * FROM `item_mail`	WHERE `type`='gift' AND `receiver`='" + receiver.getName() + "' AND `read`='0'";
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
					return new Mail(s, r, new ItemStack(id, a,(short)d), im);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
}
