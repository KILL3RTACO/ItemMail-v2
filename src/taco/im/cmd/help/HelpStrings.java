package taco.im.cmd.help;

import org.bukkit.entity.Player;

import taco.im.util.ChatUtils;

public enum HelpStrings {

	VIEW("&b/im [#]&7: &3View your mail"),
	OPEN("&b/im send <player> [item:damage] [#]&7: &3Send mail"),
	SEND("&b/im open [#]&7: &3Open mail"),
	DELETE("&b/im delete [#/*]&7: &3Delete unwanted mail"),
	VIEW_REQUESTS("&b/im requests [#]&7: &3View your requests"),
	REQUEST("&b/im request <player> [item:damage] [#] &7: &3Ask someone for items"),
	ACCEPT("&b/im accept [#]&7: &3Accept requests"),
	DECLINE("&b/im decline [#]&7: &3Decline requests"),
	HELP("&b/im ?&7: &3Shows this message");
	
	private String line;
	private static ChatUtils cu = new ChatUtils();
	
	private HelpStrings(String line){
		this.line = line;
	}
	
	public String getLine(){
		return this.line;
	}
	
	public static void getHelpAtPage(int page, Player p){
		int pages = values().length / 4;
		if(values().length % 4 != 0){
			pages++;
		}
		if(page != 0){
			if(page > pages){
				p.sendMessage(cu.formatColors("&cThat page doesn't exist")); //TODO this is just a placeholder
			}else{
				p.sendMessage(cu.formatColors("------[ItemMail Help: Page " + page + " of " + pages + "]------"));
				int start = (4 * (page - 1)) + 1;
				int current = 0;
				for(HelpStrings hs : values()){
					current++;
					if(current >= start && current < start + 4){
						p.sendMessage(cu.formatColors(hs.getLine()));
					}
				}
				
			}
			
		}else p.sendMessage(cu.formatColors("&cThat page doesn't exist")); //TODO this is just a placeholder
		
	}
	
}
