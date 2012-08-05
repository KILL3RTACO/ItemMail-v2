package taco.im.cmd;

import org.bukkit.entity.Player;

import taco.im.MailBoxType;

public abstract class ItemMailBoxCommand {

	protected String[] aliases;
	protected String help;
	
	public abstract void execute(MailBoxType boxType);
	
	public abstract void execute(Player p, int index);
	
	public boolean getAliasMatch(String name){
		for(String s : aliases){
			if(name.equalsIgnoreCase(s)) return true;
		}
		return false;
	}
	
}
