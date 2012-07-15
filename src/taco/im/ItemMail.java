package taco.im;

import java.io.File;
import java.sql.SQLException;

import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import taco.im.cmd.ItemMailCommand;
import taco.im.sql.ItemMailSQL;
import taco.im.util.ChatUtils;

public class ItemMail extends JavaPlugin implements Listener{

	public Config config;
	private ChatUtils cu = new ChatUtils();
	private boolean hasError = false;
	public ItemMailSQL mysql = null;
	public static String SEND_PERMISSION = "ItemMail.action.send";
	public static String OPEN_PERMISSION = "ItemMail.action.open";
	public static String DELETE_PERMISSION = "ItemMail.action.delete";
	
	public void onDisable(){
		info("Disabled");
	}
	
	public void onEnable(){
		config = new Config(new File(this.getDataFolder() + "/config.yml"));
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
			hasError = true;
		}
		if(!hasError){
			getServer().getPluginManager().registerEvents(this, this);
		}
		info("Enabled");
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event){
		Player player =  event.getPlayer();
		MailBox mb = new MailBox(player, this);
		if(mb.getUnopenedCount() == 0) player.sendMessage(cu.format("%3Your mailbox is empty", true));
		else player.sendMessage(cu.format("%3You have %1" + mb.getUnopenedCount() + " %3unopened package(s)", true));
	}
	
	public void info(String msg){
		System.out.println("[ItemMail] " + msg);
	}
	
	private void setupMySQL() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		mysql = new ItemMailSQL(this);
	}
	
}
