package taco.im.cmd;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import taco.im.request.Request;

public class RequestSubCommand {

	public RequestSubCommand(Player player, String receiver, ItemStack items){
		Request req = new Request(player.getName(), receiver, items);
		try {
			req.send();
		} catch (Exception e) {
			player.sendMessage(e.getMessage());
		}
	}
	
}
