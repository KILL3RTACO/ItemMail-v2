package taco.im.util;

import java.sql.ResultSet;

import taco.im.ItemMail;
import taco.im.obj.ItemMailPlayer;

public class MailMan {

	ItemMail plugin = null;
	
	public MailMan(ItemMail instance){
		plugin = instance;
	}
	
	public void checkAllMail(ItemMailPlayer player){
		if(player.hasPermission(ItemMail.OPEN_PERMISSION) || player.hasPermission(ItemMail.ALL_PERMISSION)){
			if(player.hasMail()){
				ResultSet mail = player.getAllMail();
			}else{
				player.sendErrorMessage("You inbox is empty");
			}
		}else{
			player.sendInvalidPermissionMessage();
		}
	}
	
	public void openMailAt(ItemMailPlayer player, int index){
		//TODO check if player has specified amount of mail if so, check if they can hold
	}
}
