package taco.im.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum ItemNames {

	OAK_WOOD(5, 0),
	BRICH_WOOD(5,1),
	PINE_WOOD(5, 2),
	JUNGLE_WOOD(5, 3),
	OAK_SAPLING(6, 0),
	BIRCH_SAPLING(6, 1),
	PINE_SAPLING(6, 2),
	JUNGLE_SAPLING(6, 3),
	OAK_LOG(17, 0),
	BIRCH_LOG(17, 1),
	PINE_LOG(17, 2),
	JUNGLE_LOG(17, 3),
	OAK_LEAVES(18, 0),
	BIRCH_LEAVES(18, 1),
	PINE_LEAVES(18, 2),
	JUNGLE_LEAVES(18, 3),
	SANDSTONE(24, 0),
	CHISELED_SANDSTONE(24, 1),
	SMOOTH_SANDSTONE(24, 2),
	WHITE_WOOL(35, 0),
	ORANGE_WOOL(35, 1),
	MAGENTA_WOOL(35, 2),
	LIGHT_BLUE_WOOL(35, 3),
	YELLOW_WOOL(35, 4),
	LIGHT_GREEN_WOOL(35, 5),
	PINK_WOOL(35, 6),
	GRAY_WOOL(35, 7),
	LIGHT_GRAY_WOOL(35, 8),
	CYAN_WOOL(35, 9),
	PURPLE_WOOL(35, 10),
	BLUE_WOOL(35, 11),
	BROWN_WOOL(35, 12),
	GREEN_WOOL(35, 13),
	RED_WOOL(35, 14),
	BLACK_WOOL(35, 15),
	STONE_STEP(44, 0),
	SANDSTONE_STEP(44, 1),
	COBBLESTONE_STEP(44, 3),
	BRICK_STEP(44, 4),
	STONE_BRICK_STEP(44, 5),
	STONE_BRICK(98, 0),
	MOSSY_STONE_BRICK(98, 1),
	CRACKED_STONE_BRICK(98, 2),
	CHISELED_STONE_BRICK(98, 3),
	OAK_WOOD_SLAB(126, 0),
	BIRCH_WOOD_SLAB(126, 1),
	PINE_WOOD_SLAB(126, 2),
	JUNGLE_WOOD_SLAB(126, 3),
	INK_SACK(351, 0),
	RED_DYE(351, 1),
	GREEN_DYE(351, 2),
	COCOA_BEANS(351, 3),
	LAPIS_LAZULI(351, 4),
	PURPLE_DYE(351, 5),
	CYAN_DYE(351, 6),
	LIGHT_GRAY_DYE(351, 7),
	GRAY_DYE(351, 8),
	PINK_DYE(351, 9),
	LIGHT_GREEN(351, 10),
	YELLOW(351, 11),
	LIGHT_BLUE(351, 12),
	MAGENTA(351, 13),
	ORANGE(351, 14),
	BONEMEAL(351, 15);
	
	private int id;
	private int damage;
	
	private ItemNames(int id, int damage){
		this.id = id;
		this.damage = damage;
	}
	
	public int getId(){
		return this.id;
	}
	
	public int getDamage(){
		return this.damage;
	}
	
	public static String getDisplayName(ItemStack items){
		for(ItemNames i : ItemNames.values()){
			if(i.getId() == items.getTypeId() && i.getDamage() == items.getDurability()){
				return i.name();
			}
		}
		return items.getType().toString();
	}
	
	//Get an ItemStack from a name like listed above or from the format item:damage
	public static ItemStack getItemStackFromString(String material, String amount){
		String[] m = material.split(":");
		int id = 0;
		int d = 0;
		if(m.length == 1){
			if(isNum(amount)){
				for(ItemNames i : ItemNames.values()){
					if(i.name().equalsIgnoreCase(material)){
						id = i.getId();
						d = i.getDamage();
						return new ItemStack(id, Integer.parseInt(amount), (short)d);
					}
				}
				//If no name didn't match any listed above
				Material mat = null;
				if(isNum(material)) mat = Material.getMaterial(Integer.parseInt(material));
				else mat = Material.getMaterial(material);
				if(mat == null){
					return null;
				}else{
					return new ItemStack(mat, Integer.parseInt(amount));
				}
			}else{
				return null;
			}
			
		}else if(m.length == 2){
			if(isNum(amount)){
				Material mat = null;
				d = 0;
				if(isNum(m[0])) mat = Material.getMaterial(Integer.parseInt(m[0]));
				else mat = Material.getMaterial(m[0]);
				if(isNum(m[1])){
					 d = Integer.parseInt(m[1]);
					 return new ItemStack(mat, Integer.parseInt(amount), (short)d);
				}else{ 
					return null;
				}
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	
	//Test if a String is an Integer
	private static boolean isNum(String s){
		try{
			Integer.parseInt(s);
			return true;
		}catch(NumberFormatException e){
			return false;
		}
	}
	
}
