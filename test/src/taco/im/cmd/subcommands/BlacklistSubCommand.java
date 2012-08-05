package taco.im.cmd.subcommands;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import taco.im.ItemMail;
import taco.im.PermissionsHelper;
import taco.im.util.ChatUtils;

public class BlacklistSubCommand {

	private ChatUtils cu = new ChatUtils();
	
	public BlacklistSubCommand(Player invoker, String num, boolean add){
		String perm;
		if(add) perm = PermissionsHelper.ADD_BLACKLISTED_ITEM_PERMISSION;
		else perm = PermissionsHelper.REMOVE_BLACKLISTED_ITEM_PERMISSION;
		if(invoker.hasPermission(perm)){
			if(cu.isNum(num)){
				int id = Integer.parseInt(num);
				Material temp = Material.getMaterial(id);
				if(temp != null){
					if(add){
						if(ItemMail.config.addItemToBlacklist(id)){
							//TODO tell admin item is blacklisted
						}else{
							//TODO tell admin item is already in blacklist
						}
					}else{
						if(ItemMail.config.removeItemFromBlacklist(id)){
							//TODO tell admin item was removed from blacklist
						}else{
							//TODO tell admin item is not in item blacklist
						}
					}
				}
			}
		}
	}
	
}
