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
		if(page > pages || page <= 0){
			player.sendMessage(cu.format("%cThat page doesnt exist", true));
		}else{
			player.sendMessage(cu.format("%b--------------[%3ItemMail: Page %1" + page + " %3of %1" + pages + "%b]---------------", false));
			for(int i = start; i <= start + (MAX_MAIL_ON_PAGE - 1); i++){
				if(i + 1 > unopened.length) break;
				Mail m = (Mail) unopened[i];
				player.sendMessage(cu.format("%7[%1" + (i + 1) + "%7] %6FROM: %2" + m.getSender() + 
						"%6 ITEMS: %2" + m.getItemAmount() + " " + m.getItemType(), false));
			}
			player.sendMessage(cu.format("%3-------------------------------------------------", false));
		}
		
	}
	
	public int getUnopenedCount(){
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
				ItemStack i = new ItemStack(rs.getInt(5), rs.getInt(6), (short)rs.getInt(7));
				mail.add(new Mail(s, r, i, plugin));
			}
			unopened = mail.toArray();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
