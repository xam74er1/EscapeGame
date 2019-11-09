package Vue;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import Modele.BlockInventory;
import Modele.CodeInterface;
import Modele.Ellement;
import Modele.GamePlayer;
import Utils.EG_Exception;
import Utils.Log;
import fr.xam74er1.spigot.Main;

public class BlockInventoryGUI {

	public BlockInventoryGUI(Player p,BlockInventory binv) {
		Log.print("BlockInventoryGUI");
		//On recupere parmis tt les inventaire qui existe linventory en cour 
		//cela evite de duplique les object et permet de connecte les inventaire entre eux 
		BlockInventory enCour = Main.getGame().findOrAdd(binv);
		// TODO Auto-generated constructor stub
		Log.print("Ouverture de bliock inventory GUI pour le block d'id "+enCour.getId());
		p.openInventory(enCour.getInventory());
	}

	public static void onInventoryClick(InventoryClickEvent e) throws EG_Exception {
		Player p = (Player) e.getWhoClicked();

		GamePlayer gp = Main.getGame().getPlayer(p.getName());
		//On recure la paorte a code
		Ellement el = gp.getEllement();

		Inventory inv = e.getInventory();



		//Si cela nest pas une porte a code qui est selectione 
		if(!(el instanceof BlockInventory)) {
			Log.print("Erreur de classe "+el.getClass()+"");
			throw new EG_Exception("Ce nest pas un block inventaire");
		}

		//On recupere le block inventory en question 
		BlockInventory binv = Main.getGame().findOrAdd((BlockInventory) el);


		ItemStack it = e.getCurrentItem();



		if(it==null) {


			binv.setInv(inv);


		}else {
			Inventory tmp = Bukkit.createInventory(null, inv.getSize(),e.getView().getTitle());

			tmp.setContents(inv.getContents());
			int slot = e.getSlot();


			tmp.setItem(slot, it);
			binv.setInv(tmp);
		}

		Update(binv.getInv(),p);


	}

	public static void onClose(InventoryCloseEvent e){

		Player p = (Player) e.getPlayer();

		GamePlayer gp = Main.getGame().getPlayer(p.getName());
		//On recure la paorte a code
		Ellement el = gp.getEllement();

		Inventory inv = e.getInventory();



		//Si cela nest pas une porte a code qui est selectione 
		if(!(el instanceof BlockInventory)) {
			Log.print("Erreur de classe "+el.getClass()+"");
			try {
				throw new EG_Exception("Ce nest pas un block inventaire");
			} catch (EG_Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		BlockInventory binv = (BlockInventory) el;






		binv.setInv(inv);

		Update(binv.getInv(),p);

		





	}

	public static void Update(Inventory inv,Player p) {
		List<HumanEntity> list =	inv.getViewers();

		if(list.size()>1) {
			for(HumanEntity he: list) {
			
		
						he.closeInventory();
						he.openInventory(inv);
					
			}
		}
	}

	public static boolean isSame(Inventory a,Inventory b) {

		for(int i =0;i<a.getSize();i++) {

			Log.print(i+" a : "+a.getItem(i)+" b : "+b.getItem(i));

			if(a.getItem(i)!=null ) {
				if(!a.getItem(i).isSimilar(b.getItem(i))) {
					return false;
				}
			}else if(!(a.getItem(i)==null && b.getItem(i)==null)){
				return false;
			}
		}

		return true;
	}

}
