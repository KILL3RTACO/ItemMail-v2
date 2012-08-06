package taco.im.log;

import java.util.HashMap;

import org.bukkit.entity.Player;

import taco.im.ItemMail;
import taco.im.MailType;
import taco.im.PermissionsHelper;
import taco.im.util.ChatUtils;

public class GlobalLog {

	private HashMap<String, Boolean> map = new HashMap<String, Boolean>();
	private ChatUtils cu = new ChatUtils();
	
	public void toggle(Player player){
		if(player.hasPermission(PermissionsHelper.GLOBAL_LOG_TOGGLE_PERMISSION) 
				|| player.hasPermission(PermissionsHelper.ALL_ADMIN_PERMISSION)){
			//if !map.contains player, add them else toggle value
			if(!map.containsKey(player.getName())){
				map.put(player.getName(), true);
			}else{
				map.put(player.getName(), !map.get(player.getName()));
			}
			boolean status = map.get(player.getName());
			if(status){
				player.sendMessage(cu.formatColors("&3ItemMail GlobalLog&7: &bON"));
			}else{
				player.sendMessage(cu.formatColors("&3ItemMail GlobalLog&7: &bOFF"));
			}
		}else{
			
		}
	}
	
	public void sendLog(LogString msg, MailType mType){
		for(String name : map.keySet()){
			if(map.get(name)){
				Player p = ItemMail.server.getPlayer(name);
				if(p != null) p.sendMessage(msg.getMessage(mType));
			}
		}
	}
}
