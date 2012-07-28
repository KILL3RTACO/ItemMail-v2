package taco.im;

import java.io.File;
import java.sql.SQLException;

import org.bukkit.Server;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import taco.im.cmd.ItemMailCommand;
import taco.im.cmd.help.HelpStrings;
import taco.im.listener.ItemMailListener;
import taco.im.sql.ItemMailSQL;

public class ItemMail extends JavaPlugin{

	public static Config config;
	public static Language lang;
	public static Server server;
	public static ItemMail plugin;
	private boolean hasError = false;
	public static ItemMailSQL db = null;
	
	public void onDisable(){
		info("Disabled");
	}
	
	public void onEnable(){
		plugin = this;
		server = getServer();
		initConfig();
		initLanguage();
		CommandExecutor executor = new ItemMailCommand();
		getCommand("itemmail").setExecutor(executor);
		try {
			info("[MySQL] Connecting...");
			initDatabase();
			info("[MySQL] Connected to server");
		} catch (Exception e) {
			info("[MySQL] Failed to connect, please check the config.yml");
			hasError = true;
		}
		if(!hasError){
			getServer().getPluginManager().registerEvents(new ItemMailListener(), this);
		}
		info("Enabled");
	}
	
	private void initConfig(){
		info("Loading config...");
		config = new Config(new File(getDataFolder() + "/config.yml"));
		config.addDefaultConfigValue("mysql.database.name", "minecraft");
		config.addDefaultConfigValue("mysql.database.username", "root");
		config.addDefaultConfigValue("mysql.database.password", "root");
		config.addDefaultConfigValue("mysql.server.address", "localhost");
		config.addDefaultConfigValue("mysql.server.port", 3306);
		info("Saving config...");
		config.save();
	}
	
	private void initLanguage(){
		info("Loading language.yml...");
		lang = new Language(new File(getDataFolder() + "/language.yml"));
		lang.addDefaultValue("im.header", "&7[&1ItemMail&7]&f");
		lang.addDefaultValue("im.help.key_line", "&5Key&7: &6<> &7= &2required  &6[] &7= &2optional  &6/ &7= &2 or  &6* &7= &2all");
		lang.addDefaultValue("mail.box.empty", "%header &bYour mailbox is empty");
		lang.addDefaultValue("mail.box.single", "%header &bYou have &31 &bunopened package");
		lang.addDefaultValue("mail.box.plural", "%header &bYou have &3%numMail &bunopened package");
		lang.addDefaultValue("request.box.empty", "%header &bYour requestbox is empty");
		lang.addDefaultValue("request.box.single", "%header &bYou have &31 &bunanswered request");
		lang.addDefaultValue("request.box.plural", "%header &bYou have &3%numRequest &bunanswered requests");
		info("Saving language.yml...");
	}
	
	public void info(String msg){
		System.out.println("[ItemMail] " + msg);
	}
	
	public void sendHelp(Player player, int page){
		HelpStrings.getHelpAtPage(page, player);
		player.sendMessage(lang.getHelpKey());

	}
	
	private void initDatabase() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		db = new ItemMailSQL();
	}
	
}
