package taco.im.util;

import org.bukkit.ChatColor;

public class ChatUtils {      
	
	public String notNum(String s){
		return formatColors("&c'&f" + s + "&c' is not a number");
	}
	
	public String formatColors(String message){
		if(message.contains("&"))
			message.replace('&', ChatColor.COLOR_CHAR);
		return message;
	}
	
	public boolean isNum(String s){
		try{
			Integer.parseInt(s);
			return true;
		}catch(NumberFormatException e){
			return false;
		}
	}
	
}
