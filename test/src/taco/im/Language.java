package taco.im;

import java.io.File;
import java.io.IOException;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import taco.im.util.ChatUtils;

public class Language {

	private ChatUtils cu = new ChatUtils();
	private YamlConfiguration lang = null;
	private File langFile = null;
	
	public Language(File file){
		lang = YamlConfiguration.loadConfiguration(file);
		this.langFile = file;
	}
	
	public void addDefaultValue(String path, Object value){	
		if(!lang.contains(path)){
			if(value instanceof String){
				lang.set(path, (String)value);
			}else{
				lang.set(path, value);
			}
		}
	}
	
	public void header(){
		lang.options().header("ItemMail Language Configuration\n" +
				"by KILL3RTACO\n\n" +
				"Most of the concept/idea behind this plugin (including this file) is based off Mail, a plugin by vanZeben.\n" +
				"He is an excellent coder and you should check out his plugins. Kudos to you, vanZeben/ImDeity - <3 Taco\n\n" +
				"Mail - http://dev.bukkit.org/server-mods/mail\n" +
				"vanZeben's profile - http://dev.bukkit.org/profiles/vanzeben\n" +
				"ImDeity: Kingdoms server - http://ww.imdeity.com\n\n" +
				"Please follow the color codes defined in - http://www.minecraftwiki.net/wiki/Classic_server_protocol#Color_Codes\n");
	}
	
	public void save(){
		try {
			lang.save(langFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String colors(String path){
		return cu.formatColors(lang.getString(path));
	}
	
	private String getHeader(){
		return colors("im.header");
	}
	
	public String getEmptyMailBoxMsg(Player owner){
		/*
		 * Available variables:
		 * %header - ItemMail header
		 * %player - Owner of the MailBox/RequestBox
		 */
		return colors("mail.box.empty").replaceAll("%header", getHeader())
				.replaceAll("%player", owner.getName());
	}

	public String getSingularMailMsg(Player owner) {
		/*
		 * Available variables:
		 * %header - ItemMail header
		 * %player - Owner of the MailBox/RequestBox
		 */
		return colors("mail.box.singular").replaceAll("%header", getHeader())
				.replaceAll("%player", owner.getName());
	}

	public String getPluralMailMsg(Player owner) {
		/*
		 * Available variables:
		 * %header - ItemMail header
		 * %player - Owner of the MailBox/RequestBox
		 */
		return colors("mail.box.plural").replaceAll("%header", getHeader())
				.replaceAll("%player", owner.getName());
	}
	
	public String getEmptyRequestBoxMsg(Player owner){
		/*
		 * Available variables:
		 * %header - ItemMail header
		 * %player - Owner of the MailBox/RequestBox
		 */
		return colors("request.box.empty").replaceAll("%header", getHeader())
				.replaceAll("%player", owner.getName());
	}

	public String getSingularRequestMsg(Player owner) {
		/*
		 * Available variables:
		 * %header - ItemMail header
		 * %player - Owner of the MailBox/RequestBox
		 */
		return colors("request.box.singular").replaceAll("%header", getHeader())
				.replaceAll("%player", owner.getName());
	}

	public String getPluralRequestMsg() {
		/*
		 * Available variables:
		 * %header - ItemMail header
		 * %player - Owner of the MailBox/RequestBox
		 */
		return colors("request.box.plural").replaceAll("%header", getHeader());
	}

	public String getBlacklistAddMsg(Material material){
		/*
		 * Available variables:
		 * %header - ItemMail header
		 * %item - Item to be added/removed from blacklist
		 */
		return colors("blacklist.add").replaceAll("%header", getHeader())
				.replaceAll("%item", material.toString());
	}
	
	public String getHelpKey() {
		return colors("im.help.key_line");
	}
	
}
