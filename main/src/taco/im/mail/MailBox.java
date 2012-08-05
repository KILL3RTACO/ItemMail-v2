package taco.im.mail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import taco.im.ItemMail;
import taco.im.MailBoxType;
import taco.im.PermissionsHelper;
import taco.im.exception.InvalidPermissionsException;
import taco.im.util.ChatUtils;
import taco.im.util.ItemNames;

public class MailBox implements MailBoxType{
	private ChatUtils cu = new ChatUtils();
	private Player player = null;
	private Object[] unopened = null;
	private int MAX_MAIL_ON_PAGE = 7;
	
	public MailBox(Player p){
		player = p;
		reload();
	}
	
	public Player getOwner(){
		return player;
	}
	
	public void getElementsAtPage(int page){
		reload();
		int pages = (getUnreadCount() / MAX_MAIL_ON_PAGE);
		if(getUnreadCount() % MAX_MAIL_ON_PAGE > 0) pages ++;
		int start = ((page - 1) * 7);
		if(getUnreadCount() == 0){
			player.sendMessage(cu.formatColors("Your mailbox is empty"));
		}else if(page > pages){
			player.sendMessage(cu.formatColors("&cThat page doesnt exist"));
		}else{
			player.sendMessage(cu.formatColors("&b------------[&3ItemMail: Mail: Page &1" + page + " &3of &1" + pages + "&b]------------"));
			for(int i = start; i <= start + (MAX_MAIL_ON_PAGE - 1); i++){
				if(i + 1 > unopened.length) break;
				Mail m = (Mail) unopened[i];
				player.sendMessage(cu.formatColors("&7[&1" + (i + 1) + "&7] &6FROM: &2" + m.getSender() + 
						"&6 ITEMS: &2" + m.getItemAmount() + " " + ItemNames.getDisplayName(m.getItems())));
			}
			player.sendMessage(cu.formatColors("&b-----------------------------------------------"));
		}
	}
	
	public int getUnreadCount(){
		reload();
		return unopened.length;
	}
	
	public void reload(){
		ArrayList<Mail> mail = new ArrayList<Mail>();
		try {
			String sql = "SELECT * FROM `item_mail` WHERE `receiver`='" + player.getName() + "' AND `type`='gift' AND `read`='0'";
			ResultSet rs = ItemMail.db.getResultSet(sql);
			while(rs.next()){
				String s = rs.getString(2);
				String r = rs.getString(3);
				ItemStack i = new ItemStack(rs.getInt(5), rs.getInt(7), (short)rs.getInt(6));
				mail.add(new Mail(s, r, i));
			}
			unopened = mail.toArray();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteAll() throws InvalidPermissionsException{
		reload();
		if(player.hasPermission(PermissionsHelper.DELETE_PERMISSION)){
			if(getUnreadCount() == 0){
				player.sendMessage(cu.formatColors("&cYou have no mail to delete"));
			}else{
				int count = 0;
				for(Object o : unopened){
					Mail m = (Mail)o;
					try {
						String sql = "SELECT * FROM `item_mail` WHERE `receiver`='" + m.getReceiver() + "' AND `read`='0'";
						ResultSet rs = ItemMail.db.getResultSet(sql);
						int tId = m.getItemTypeId();
						int d = m.getItemDamage();
						int a = m.getItemAmount();
						while(rs.next()){
							if(rs.getInt(5) == tId && rs.getInt(6) == d && rs.getInt(7) == a){
								sql = "UPDATE `item_mail` SET `read`='1' WHERE `id`='" + rs.getInt(1) + "'";
								ItemMail.db.statement(sql);
								count++;
							}
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				player.sendMessage(cu.formatColors("&2" + count + " &apackage(s) trashed"));
			}
		}else{
			throw new InvalidPermissionsException("");
		}
	}
}
