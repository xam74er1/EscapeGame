package Listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import Modele.BlockInventory;
import Utils.Log;
import Vue.BlockInventoryGUI;
import Vue.CodeGUI;

public class OnInventoryMoveItem implements Listener{
	
//	@EventHandler
//	public void onMove(InventoryMoveItemEvent e){
//	Log.print("add item inv putin");
//
//		
//	}
	
	@EventHandler
	public void onClose(InventoryCloseEvent e){
		Log.print("Close inventory");

		
		Player p = (Player) e.getPlayer();


		Inventory inv = e.getInventory();

		InventoryView w = e.getView();
		if(inv.getType()==InventoryType.CHEST&&  w.getTitle().equalsIgnoreCase(CodeGUI.getName())) {
			//CodeGUI.onInventoryClick(e);
		}else if(inv.getType()==InventoryType.CHEST&&  w.getTitle().equalsIgnoreCase(BlockInventory.getInvName())){
		

			BlockInventoryGUI.onClose(e);
		}else {
			Log.print(w.getTitle());
		}
	

			
		}
	
	@EventHandler
	public void onMoveB(InventoryEvent e){
	Log.print("Inventory envent");

		
	}
}
