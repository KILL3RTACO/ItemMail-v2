package taco.im.cmd;

import taco.im.MailType;

public abstract class ItemMailCommand {

	protected String[] aliases;
	protected String help;
	
	public abstract void execute(MailType mType);
	
	public boolean getAliasMatch(String name){
		for(String s : aliases){
			if(name.equalsIgnoreCase(s)) return true;
		}
		return false;
	}
	
}
