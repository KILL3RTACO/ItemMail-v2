package taco.im;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import taco.im.cmd.ItemMailCommand;
import taco.im.sql.ItemMailSQL;

public class ItemMail extends JavaPlugin{

	private File configFile = new File("plugins/ItemMail/config.yml");
	public Config config = new Config(configFile);
	public ItemMailSQL mysql = null;
	
	public void onDisable(){
		
	}
	
	public void onEnable(){
		addDataFolders();
		info("Loading config...");
		config.loadDefaults();
		CommandExecutor executor = new ItemMailCommand(this);
		getCommand("itemmail").setExecutor(executor);
		try {
			info("[MySQL] Connecting...");
			setupMySQL();
			info("[MySQL] Connected to server");
		} catch (Exception e) {
			info("[MySQL] Failed to connect, please check the config.yml");
		}
		info("Enabled");
	}
	
	private void addDataFolders(){
		try {
			createDir(configFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void createDir(File file) throws IOException{
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
	
	public void info(String msg){
		System.out.println("[ItemMail] " + msg);
	}
	
	private void setupMySQL() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		mysql = new ItemMailSQL(this);
	}
	
}
