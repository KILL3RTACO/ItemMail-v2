package taco.im.mail;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import taco.im.ItemMail;
import taco.im.MailBoxType;
import taco.im.MailType;
import taco.im.PermissionsHelper;
import taco.im.util.ChatUtils;
import taco.im.util.ItemNames;


public class Mail implements MailType{

	private ChatUtils cu = new ChatUtils();
	private String sender, receiver;
	private ItemStack items;
	
	public Mail(String sender, String receiver, ItemStack items){
		this.sender = sender;
		this.receiver = receiver;
		this.items = items;
		
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
		Player p = ItemMail.server.getPlayer(receiver);
		if(p.hasPermission(PermissionsHelper.DELETE_PERMISSION)){
			doReadStatement();
			//TODO place-holder
			p.sendMessage(cu.formatColors("&aPackage trashed"));
		}else{
			//TODO send message (language.yml)
		}
	}
	
	public boolean send(){
		Player pSender = ItemMail.server.getPlayer(sender);
		Player pReceiver = ItemMail.server.getPlayer(receiver);
		if(pReceiver == null){
			OfflinePlayer op = ItemMail.server.getOfflinePlayer(receiver);
			if(op.hasPlayedBefore()){
				if(hasItems() && testSendConditions(pSender)){
					doSendStatement(op.getName());
					if(isPlayer(sender)){
						pSender.getInventory().removeItem(items);
						//TODO place-holder
						pSender.sendMessage(cu.formatColors("&aSent &2" + getItemAmount() + " " + ItemNames.getDisplayName(items) + 
								" &ato &d" + op.getName()));
					}
					return true;
				}else{
					return false;
				}
			}else{
				pSender.sendMessage(cu.formatColors("&cPlayer '&f" + receiver + "&c' does not exist or has not played here before"));
				return false;
			}
		}else{
			if(hasItems() && testSendConditions(pSender)){
				if(pSender.getName().equalsIgnoreCase(pReceiver.getName())){
					//TODO place-holder
					pSender.sendMessage(cu.formatColors("&cYou can't send items to yourself"));
					return false;
				}else{
					doSendStatement(pReceiver.getName());
					pSender.getInventory().removeItem(items);
					pSender.sendMessage(cu.formatColors("&aSent &2" + getItemAmount() + " " + ItemNames.getDisplayName(items) + 
							" &ato &d" + pReceiver.getName()));
					return true;
				}
			}else{
				return false;
			}
		}
	}
	
	private boolean isPlayer(String name){
		return ItemMail.server.getPlayer(name) != null;
	}
	
	private boolean hasItems() {
		Player player = ItemMail.server.getPlayer(sender);
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
			//TODO place-holder
			player.sendMessage(cu.formatColors("&cYou don't have &6" + items.getAmount() + " " + items.getType()));
			return false;
		}
	}
	
	private boolean testSendConditions(Player sender){
		if(sender.hasPermission(PermissionsHelper.SEND_PERMISSION) || sender.hasPermission(PermissionsHelper.ALL_GENERAL_PERMISSION)){
			if(sender.getGameMode() == GameMode.CREATIVE){
				if(sender.hasPermission(PermissionsHelper.SEND_IN_CREATIVE_PERMISSION) 
						|| sender.hasPermission(PermissionsHelper.ALL_GENERAL_PERMISSION)){
					return true;
				}else{
					//TODO send message (language.yml)
					return false;
				}
			}else{
				if(getItemType() == Material.AIR){
					//TODO place-holder
					sender.sendMessage(cu.formatColors("&cYou can't send &6AIR"));
					return false;
				}else if(getItemAmount() == 0){
					//TODO place-holder
					sender.sendMessage(cu.formatColors("&cYou can't send &60&c of something"));
					return false;
				}else{
					return true;
				}
			}
		}else{
			//TODO send message (language.yml)
			return false;
		}
	}
	
	private boolean hasRoom(){
		Player player = ItemMail.server.getPlayer(receiver);
		int space = 0, needed = getItemAmount();
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
		if(this != null){
			Player player = ItemMail.server.getPlayer(receiver);
			if(player.hasPermission(PermissionsHelper.OPEN_PERMISSION) || player.hasPermission(PermissionsHelper.ALL_GENERAL_PERMISSION)){
				if(player.getGameMode() != GameMode.CREATIVE && hasRoom()){
					doReadStatement();
					player.getInventory().addItem(items);
					//TODO place-holder
					player.sendMessage(cu.formatColors("&6CONTENTS: &2" + getItemAmount() + " " + ItemNames.getDisplayName(items)));
				}else if(!hasRoom()){
					//TODO send message (language.yml) no room
				}else{
					//TODO send message (language.yml) invalid game mode: open
				}
			}else{
				//TODO send message (language.yml) invalid permission
			}
		}else{
			//TODO send message (language.yml) mail non existant
		}
	}
	
	private void doReadStatement(){
		try {
			String sql = "SELECT * FROM `item_mail` WHERE `receiver`='" + receiver + "' AND `type`='gift' AND `read`='0'";
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
				"'" + recvr + "', 'gift', '" + getItemTypeId() + "', '" + getItemDamage() + "', '" + getItemAmount() + "')";
			ItemMail.db.statement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Mail getMailFromTable(Player receiver, int index){
		String sql = "SELECT * FROM `item_mail`	WHERE `type`='gift' AND `receiver`='" + receiver.getName() + "' AND `read`='0'";
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
					return new Mail(s, r, new ItemStack(id, a,(short)d));
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
		return new MailBox(ItemMail.server.getPlayer(receiver));
	}
}
