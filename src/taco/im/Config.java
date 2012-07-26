package taco.im;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

public class Config {
	
	private YamlConfiguration config = null;
	private File configFile = null;
	
	public Config(File file){
		config = YamlConfiguration.loadConfiguration(file);
		this.configFile = file;
	}
	
	public void loadDefaults(){
		config.options().header("ItemMail configuration options\nby KILL3RTACO");
		if(!config.contains("mysql.database.name"))							//MySQL options
			config.set("mysql.database.name", "minecraft");
		if(!config.contains("mysql.database.username"))
			config.set("mysql.database.username", "root");
		if(!config.contains("mysql.database.password"))
			config.set("mysql.database.password", "root");
		if(!config.contains("mysql.server.address"))
			config.set("mysql.server.address", "localhost");
		if(!config.contains("mysql.server.port"))
			config.set("mysql.server.port", 3306);
		save();
	}
	
	private void save(){
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
