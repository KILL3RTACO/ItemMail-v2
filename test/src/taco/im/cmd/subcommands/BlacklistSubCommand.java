package taco.im.cmd.subcommands;

import java.sql.SQLException;

import org.bukkit.Material;

import taco.im.ItemMail;

public class BlacklistSubCommand {
	
	private String[] aliases = new String[]{"blacklist", "bl", "blist"};
	private String[] addAliases = new String[]{"add", "a"};
	private String[] rmAliases = new String[]{"remove", "rm", "r"};
	
	public void addToBlacklist(int id, int damage){
		try {
			String sql = "INSERT INTO `im_blacklist` (`item_id`, `item_damage`) VALUES ('" + id + "' ,'" + damage + "')";
			ItemMail.db.statement(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void removeFromBlacklist(int id, int damage){
		try {
			String sql = "INSERT INTO `im_blacklist` (`item_id`, `item_damage`) VALUES ('" + id + "' ,'" + damage + "')";
			ItemMail.db.statement(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean getAliasMatch(String name){
		for(String s : aliases){
			if(name.equalsIgnoreCase(s)){
				return true;
			}
		}
		return false;
	}
	
	public boolean getAddAliasMatch(String name){
		for(String s : addAliases){
			if(name.equalsIgnoreCase(s)){
				return true;
			}
		}
		return false;
	}
	
	public boolean getRemoveAliasMatch(String name){
		for(String s : rmAliases){
			if(name.equalsIgnoreCase(s)){
				return true;
			}
		}
		return false;
	}
}
