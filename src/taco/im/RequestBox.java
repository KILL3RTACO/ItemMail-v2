package taco.im;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import taco.im.util.ChatUtils;
import taco.im.util.ItemNames;

public class RequestBox {

	private ItemMail plugin = null;
	private ChatUtils cu = new ChatUtils();
	Player player = null;
	private Object[] unanswered = null;
	private int MAX_REQUESTS_ON_PAGE = 7;
	
	public RequestBox(Player p, ItemMail instance){
		player = p;
		plugin = instance;
		reloadRequests();
	}
	
	public void getRequestsAtPage(int page){
		reloadRequests();
		int pages = (getUnansweredCount() / MAX_REQUESTS_ON_PAGE);
		if(getUnansweredCount() % MAX_REQUESTS_ON_PAGE > 0) pages ++;
		int start = ((page - 1) * 7);
		if(getUnansweredCount() == 0){
			player.sendMessage(cu.format("Your requestbox is empty", true));
		}else if(page > pages){
			player.sendMessage(cu.format("%cThat page doesnt exist", true));
		}else{
			player.sendMessage(cu.format("%b----------[%3ItemMail: Requests: Page %1" + page + " %3of %1" + pages + "%b]----------", false));
			for(int i = start; i <= start + (MAX_REQUESTS_ON_PAGE - 1); i++){
				if(i + 1 > unanswered.length) break;
				Request m = (Request) unanswered[i];
				player.sendMessage(cu.format("%7[%1" + (i + 1) + "%7] %6FROM: %2" + m.getSender() + 
						"%6 REQUESTING: %2" + m.getItemAmount() + " " + ItemNames.getDisplayName(m.getItems()), false));
			}
			player.sendMessage(cu.format("%b-----------------------------------------------", false));
		}
	}
	
	public int getUnansweredCount(){
		reloadRequests();
		return unanswered.length;
	}
	
	public void reloadRequests(){
		ArrayList<Request> requests = new ArrayList<Request>();
		try {
			String sql = "SELECT * FROM `item_mail` WHERE `receiver`='" + player.getName() + "' AND `type`='request' AND `read`='0'";
			ResultSet rs = plugin.mysql.getResultSet(sql);
			while(rs.next()){
				String s = rs.getString(2);
				String r = rs.getString(3);
				ItemStack i = new ItemStack(rs.getInt(5), rs.getInt(7), (short)rs.getInt(6));
				requests.add(new Request(s, r, i, plugin));
			}
			unanswered = requests.toArray();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void declineAllMail(){
		reloadRequests();
		if(player.hasPermission(ItemMail.DECLINE_PERMISSION)){
			if(getUnansweredCount() == 0){
				player.sendMessage(cu.format("%cYou have no requests to decline", true));
			}else{
				int count = 0;
				for(Object o : unanswered){
					Request m = (Request)o;
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
				player.sendMessage(cu.format("%2" + count + " %arequest(s) declined", true));
			}
		}else{
			player.sendMessage(cu.invalidPerm);
		}
	}
}
