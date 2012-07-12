package taco.im;

import java.sql.SQLException;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;


public class Request implements BaseMail {

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

	public void send(){
		try {
			String sql = "INSERT INTO `item_mail` (`sender`, `receiver`, `type`, `item_id`, `damage`, `amount`) VALUES('" + sender + "', " +
					"'" + receiver + "', 'request', '" + getItemTypeId() + "', '" + getItemDamage() + "'" + getItemAmount() + ")";
			plugin.mysql.statement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void accept(){
		
	}
	public void decline(){
		
	}
}
