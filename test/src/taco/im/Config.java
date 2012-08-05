package taco.im;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

public class Config {
	
	private YamlConfiguration config = null;
	private File configFile = null;
	
	public Config(File file){
		config = YamlConfiguration.loadConfiguration(file);
		this.configFile = file;
	}
	
	public void addDefaultConfigValue(String path, Object value){	
		if(!config.contains(path)){
			if(value instanceof String){
				config.set(path, (String)value);
			}else{
				config.set(path, value);
			}
		}
	}
	
	public void header(){
		config.options().header("ItemMail MySQL Configuration\n" +
				"by KILL3RTACO\n\n" +
				"Most of the concept/idea behind this plugin (including this file) is based off Mail, a plugin by vanZeben.\n" +
				"He is an excellent coder and you should check out his plugins. Kudos to you, vanZeben/ImDeity - <3 Taco\n\n" +
				"Mail - http://dev.bukkit.org/server-mods/mail\n" +
				"vanZeben's profile - http://dev.bukkit.org/profiles/vanzeben\n" +
				"ImDeity: Kingdoms server - http://ww.imdeity.com\n\n" +
				"Please follow the color codes defined in - http://www.minecraftwiki.net/wiki/Classic_server_protocol#Color_Codes\n");
	}
	
	public boolean addItemToBlacklist(int id){
		List<Integer> list = config.getIntegerList("im.blacklist_item_ids");
		if(list.contains(id)){
			return false;
		}else{
			list.add(id);
			return true;
		}
	}
	
	public boolean removeItemFromBlacklist(int id){
		List<Integer> list = config.getIntegerList("im.blacklist_item_ids");
		if(list.contains(id)){
			list.remove((Object)id);
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isItemBlacklisted(int id){
		return config.getIntegerList("im.blacklist_item_ids").contains(id);
	}
	
	public void save(){
		try {
			config.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getMySqlDatabaseName(){
		return getString("mysql.database.name");
	}

	public String getMySqlDatabaseUsername(){
		return getString("mysql.database.username");
	}
	
	public String getMySqlDatabasePassword(){
		return getString("mysql.database.password");
	}
	
	public String getMySqlServerAddress(){
		return getString("mysql.server.address");
	}
	
	public int getMySqlServerPort(){
		return getInt("mysql.server.port");
	}
	
	private String getString(String path){
		return config.getString(path);
	}
	
	private int getInt(String path){
		return config.getInt(path);
	}
	
}
