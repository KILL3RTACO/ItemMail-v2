package taco.im.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemUtils {

	public ItemStack getItemStackFromString(String material, String amount){
		String[] m = material.split(":");
		if(m.length == 1){
			Material mat = null;
			if(isNum(material)){
				if(Integer.parseInt(material) < 0) return null;
				else mat = Material.getMaterial(Integer.parseInt(material));
			}
			else mat = Material.getMaterial(material.toUpperCase());
			
			if(mat != null){
				if(isNum(amount)){
					return new ItemStack(mat, Integer.parseInt(amount));
				}else{
					return null;
				}
			}else{
				return null;
			}
		}else if(m.length == 2){
			Material mat = null;
			int a = 0;
			int d = 0;
			if(isNum(m[0])) mat = Material.getMaterial(Integer.parseInt(m[0]));
			else mat = Material.getMaterial(m[0]);
			if(isNum(amount)) a = Integer.parseInt(amount);
			else return null;
			if(isNum(m[1])) return new ItemStack(mat, a, (short)d);
			else return null;
		}else{
			return null;
		}
	}
	
	private boolean isNum(String s){
		try{
			Integer.parseInt(s);
			return true;
		}catch(NumberFormatException e){
			return false;
		}
	}
	
}
