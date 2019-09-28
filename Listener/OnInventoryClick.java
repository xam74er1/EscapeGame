package Listener;



import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import Modele.BlockInventory;
import Utils.EG_Exception;
import Utils.Log;
import Vue.BlockInventoryGUI;
import Vue.CodeGUI;

public class OnInventoryClick implements Listener{
	

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) throws EG_Exception {
		
		Player p = (Player) e.getWhoClicked();
		ItemStack it = e.getCurrentItem();

		Inventory inv = e.getInventory();
//Log.print("Cliqued ds un inventory"+System.currentTimeMillis());
		InventoryView w = e.getView();
		//Si on a un gui a code a ouvire
		if(inv.getType()==InventoryType.CHEST&&  w.getTitle().equalsIgnoreCase(CodeGUI.getName())) {
			CodeGUI.onInventoryClick(e);
		}else if(inv.getType()==InventoryType.CHEST&&  w.getTitle().equalsIgnoreCase(BlockInventory.getInvName())){
			//Si on est dans un bloque inventaire 
			e.setCancelled(false);

			BlockInventoryGUI.onInventoryClick(e);
		}else {
			Log.print(w.getTitle());
		}
	}
	
	

}
