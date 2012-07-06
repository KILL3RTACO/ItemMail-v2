package taco.im.obj;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import taco.im.ItemMail;
import taco.im.util.ChatUtils;

public class ItemMailPlayer {

	private Player p = null;
	private ItemMail plugin = null;
	private ChatUtils cu = new ChatUtils();
	
	public ItemMailPlayer(Player player, ItemMail instance){
		p = player;
		plugin = instance;
	}
	
	public boolean canHold(ItemStack items){
		PlayerInventory inv = getInventory();
		int min = items.getAmount();
		int amount = 0;
		for(ItemStack x : inv){
			if(x == null){
				amount += items.getMaxStackSize();
			}else{
				if(x.getType() == items.getType() && x.getDurability() == items.getDurability()){
					amount = items.getType().getMaxStackSize() - x.getAmount();
				}
			}
		}
		if(amount >= min) return true;
		else return false;
	}
	
	public boolean hasMail(){
		if(getMessageAmount() == 0)
			return false;
		else return true;
	}
	
	public boolean hasPermission(String arg0){
		return p.hasPermission(arg0);
	}
	
	public ResultSet getAllMail(){
		try {
			String name = getName();
			String sql = "SELECT * FROM `item_mail` WHERE `receiver`='" + name + "'";
			return plugin.mysql.getResultSet(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public PlayerInventory getInventory(){
		return p.getInventory();
	}
	
	public int getMessageAmount(){
		try {
			String name = getName();
			String sql = "SELECT * FROM `item_mail` WHERE `receiver`='" + name + "'";
			int amount = 0;
			ResultSet rs = plugin.mysql.getResultSet(sql);
			while(rs.next())
				amount += 1;
			return amount;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public String getName(){
		return p.getName();
	}
	
	public void sendErrorMessage(String message){
		sendMessage(cu.format("%c" + message, true));
	}
	
	public void sendInfoMessage(String message){
		sendMessage(cu.format(message, true));
	}
	
	public void sendInvalidPermissionMessage(){
		sendErrorMessage("You don't have permission to do this");
	}
	
	public void sendMessage(String arg0){
		p.sendMessage(arg0);
	}
	
}
