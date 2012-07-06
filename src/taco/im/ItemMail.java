package taco.im;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import org.bukkit.plugin.java.JavaPlugin;

import taco.im.sql.ItemMailSQL;
import taco.im.util.ChatUtils;

public class ItemMail extends JavaPlugin{

	private File configFile = new File("plugins/ItemMail/config.yml");
	public ChatUtils cu = new ChatUtils();
	public Config config = new Config();
	public ItemMailSQL mysql;
	
	public void onDisable(){
		
	}
	
	public void onEnable(){
		addDataFolders();
		info("Loading config...");
		config.loadDefaults();
	}
	
	private void addDataFolders(){
		try {
			createDirs(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createDirs(File file) throws IOException{
		if(!file.exists()){
			info("Cannot find /" + file.getPath().substring(20) +", creating new file...");
			if(file.getParentFile() != null)
				file.getParentFile().mkdirs();
			if(file.getPath().endsWith(".txt") || file.getPath().endsWith(".yml")){
				file.createNewFile();
			}else{
				file.mkdir();
			}
		}
	}
	
	public void info(String message) {
		System.out.println("[ItemMail] " + message);
	}
	
	private void setup() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		mysql = new ItemMailSQL(this);
	}
}
