package taco.im.cmd.subcommands;

import java.sql.SQLException;

import taco.im.ItemMail;

public class BlacklistSubCommand {
	
	private String[] aliases = new String[]{"blacklist", "bl", "blist"};
	private String[] addAliases = new String[]{"add", "a"};
	private String[] rmAliases = new String[]{"remove", "rm", "r"};
	
	public void addToBlacklist(int id, int damage){
		if(!ItemMail.db.bl.getIsBlacklisted(id, damage)){
			try {
				String sql = "INSERT INTO `im_blacklist` (`item_id`, `item_damage`) VALUES ('" + id + "' ,'" + damage + "')";
				ItemMail.db.statement(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void removeFromBlacklist(int id, int damage){
		if(ItemMail.db.bl.getIsBlacklisted(id, damage)){
			try {
				String sql = "DELETE FROM `im_blacklist` WHERE `item_id`='" + id +"' AND `damage`='" + damage + "'";
				ItemMail.db.statement(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean hasAlias(String name){
		for(String s : aliases){
			if(name.equalsIgnoreCase(s)){
				return true;
			}
		}
		return false;
	}
	
	public boolean hasAddAlias(String name){
		for(String s : addAliases){
			if(name.equalsIgnoreCase(s)){
				return true;
			}
		}
		return false;
	}
	
	public boolean hasRemoveAlias(String name){
		for(String s : rmAliases){
			if(name.equalsIgnoreCase(s)){
				return true;
			}
		}
		return false;
	}
}
