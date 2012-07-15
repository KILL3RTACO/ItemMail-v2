package taco.im;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import taco.im.util.ChatUtils;

public class MailBox {
	
	private ItemMail plugin = null;
	private ChatUtils cu = new ChatUtils();
	private Player player = null;
	private Object[] unopened = null;
	private int MAX_MAIL_ON_PAGE = 7;
	
	public MailBox(Player p, ItemMail instance){
		player = p;
		plugin = instance;
		reloadMail();
	}
	
	public void getMailAtPage(int page){
		reloadMail();
		int pages = (getUnopenedCount() / MAX_MAIL_ON_PAGE);
		if(getUnopenedCount() % MAX_MAIL_ON_PAGE > 0) pages ++;
		int start = ((page - 1) * 7);
		if(getUnopenedCount() == 0){
			player.sendMessage(cu.format("Your mailbox is empty", true));
		}else if(page > pages){
			player.sendMessage(cu.format("%cThat page doesnt exist", true));
		}else{
			player.sendMessage(cu.format("%b--------------[%3ItemMail: Page %1" + page + " %3of %1" + pages + "%b]---------------", false));
			for(int i = start; i <= start + (MAX_MAIL_ON_PAGE - 1); i++){
				if(i + 1 > unopened.length) break;
				Mail m = (Mail) unopened[i];
				player.sendMessage(cu.format("%7[%1" + (i + 1) + "%7] %6FROM: %2" + m.getSender() + 
						"%6 ITEMS: %2" + m.getItemAmount() + " " + m.getItemType(), false));
			}
			player.sendMessage(cu.format("%b-----------------------------------------------", false));
		}
	}
	
	public int getUnopenedCount(){
		reloadMail();
		return unopened.length;
	}
	
	private void reloadMail(){
		ArrayList<Mail> mail = new ArrayList<Mail>();
		try {
			String sql = "SELECT * FROM `item_mail` WHERE `receiver`='" + player.getName() + "' AND `read`='0'";
			ResultSet rs = plugin.mysql.getResultSet(sql);
			while(rs.next()){
				String s = rs.getString(2);
				String r = rs.getString(3);
				ItemStack i = new ItemStack(rs.getInt(5), rs.getInt(7), (short)rs.getInt(6));
				mail.add(new Mail(s, r, i, plugin));
			}
			unopened = mail.toArray();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void trashAllMail(){
		reloadMail();
		if(player.hasPermission(ItemMail.DELETE_PERMISSION)){
			if(getUnopenedCount() == 0){
				player.sendMessage(cu.format("%cYou have no mail to delete", true));
			}else{
				int count = 0;
				for(Object o : unopened){
					Mail m = (Mail)o;
					try {
						String sql = "SELECT * FROM `item_mail` WHERE `receiver`='" + m.getReceiver() + "' AND `read`='0'";
						ResultSet rs = plugin.mysql.getResultSet(sql);
						int tId = m.getItemTypeId();
						int d = m.getItemDamage();
						int a = m.getItemAmount();
						while(rs.next()){
							if(rs.getInt(5) == tId && rs.getInt(6) == d && rs.getInt(7) == a){
								sql = "UPDATE `item_mail` SET `read`='1' WHERE `id`='" + rs.getInt(1) + "'";
								plugin.mysql.statement(sql);
								count++;
							}
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				player.sendMessage(cu.format("%2" + count + " %apackage(s) trashed", true));
			}
		}else{
			player.sendMessage(cu.invalidPerm);
		}
	}
}
