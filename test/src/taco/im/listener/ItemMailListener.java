package taco.im.listener;


import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import taco.im.ItemMail;
import taco.im.event.mail.MailSentEvent;
import taco.im.event.request.RequestSentEvent;
import taco.im.mail.MailBox;
import taco.im.request.RequestBox;
import taco.im.util.ChatUtils;

public class ItemMailListener implements Listener {
	
	ChatUtils cu = new ChatUtils();

	@EventHandler
	public void onJoin(PlayerJoinEvent event){
		Player player =  event.getPlayer();
		MailBox mb = new MailBox(player);
		RequestBox rb = new RequestBox(player);
		if(mb.getUnreadCount() == 0) player.sendMessage(ItemMail.lang.getEmptyMailBoxMsg(player));
		else if(mb.getUnreadCount() == 1) player.sendMessage(ItemMail.lang.getSingularMailMsg(player));
		else player.sendMessage(ItemMail.lang.getPluralMailMsg(player));
		if(rb.getUnreadCount() == 0) player.sendMessage(ItemMail.lang.getEmptyRequestBoxMsg(player));
		else player.sendMessage(ItemMail.lang.getSingularRequestMsg(player));
	}
	
	@EventHandler
	public void onSignClick(PlayerInteractEvent event){
		if(event.hasBlock()){
			Player player = event.getPlayer();
			Block block = event.getClickedBlock();
			if(block.getType() == Material.SIGN_POST || block.getType() == Material.WALL_SIGN){
				Sign sign = (Sign)block.getState();
				if(sign.getLine(0) == "[ItemMail]"){
					if(sign.getLine(1) == "General"){	//General mail box
						if(event.getAction() == Action.LEFT_CLICK_BLOCK){			//Right-click for mail, left for requests
							new RequestBox(player).getElementsAtPage(1);
						}else if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
							new MailBox(player).getElementsAtPage(1);
						}
					}else if(sign.getLine(2).equalsIgnoreCase("Requests") && event.getAction() == Action.RIGHT_CLICK_BLOCK){
						new RequestBox(player).getElementsAtPage(1);
					}else if(sign.getLine(2).equalsIgnoreCase("Mail") && event.getAction() == Action.RIGHT_CLICK_BLOCK){
						new MailBox(player).getElementsAtPage(1);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent event){
		Block block = event.getBlockAgainst();
		if(block.getType() == Material.SIGN_POST || block.getType() == Material.WALL_SIGN){
			Sign sign = (Sign)block.getState();
			if(sign.getLine(0).equalsIgnoreCase("[ItemMail]")){
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onSignChange(SignChangeEvent event){
		if(event.getLine(0).equalsIgnoreCase("[ItemMail]")){
			event.setLine(0, "[ItemMail]");
			if(event.getLine(1).equalsIgnoreCase("General")){
				event.setLine(1, "General");
				event.getPlayer().sendMessage(cu.formatColors("&aSignMailbox created. Left-click for RequestBox; Right-click for MailBox"));
			}
		}
	}
	
	@EventHandler
	public void onMailSend(MailSentEvent event){
		
	}
	
	@EventHandler
	public void onRequest(RequestSentEvent event){
		
	}
	
}
