package taco.im;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

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
	
	public String getEmptyMailBoxMsg(){
		return colors("mail.box.empty").replace("%header", getHeader());		//Start of MAIL strings
	}

	public String getSingularMailMsg() {
		return colors("mail.box.singular").replace("%header", getHeader());
	}

	public String getPluralMailMsg() {
		return colors("mail.box.plural").replace("%header", getHeader());
	}
	
	public String getEmptyRequestBoxMsg(){
		return colors("request.box.empty").replace("%header", getHeader());		//Start of REQUEST strings
	}

	public String getSingularRequestMsg() {
		return colors("request.box.singular").replace("%header", getHeader());
	}

	public String getPluralRequestMsg() {
		return colors("request.box.plural").replace("%header", getHeader());
	}

	public String getHelpKey() {
		return colors("im.help.key_line");
	}
	
}
