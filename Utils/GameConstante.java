package Utils;

import org.bukkit.Material;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public class GameConstante {
	
	public static String masterWhantName = "§b§lMaster Whant";
	
	public static ItemStack masterWhant() {
		ItemStack it = new ItemStack(Material.BLAZE_ROD, 1);
		
		
		ItemMeta im = it.getItemMeta();
		
		im.setDisplayName(masterWhantName);
		
		it.setItemMeta(im);

		
	
		
		
		return it;
	}

}
