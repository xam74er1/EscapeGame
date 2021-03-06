package Listener;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Door;

import com.google.common.util.concurrent.ListenableFuture;

import Modele.BlockInventory;
import Modele.BlockInventoryCode;
import Modele.BlockInventoryKey;
import Modele.GamePlayer;
import Modele.PorteAClef;
import Modele.PorteACode;
import Utils.EG_Exception;
import Utils.GameConstante;
import Utils.Log;
import Vue.BlockInventoryGUI;
import Vue.CodeGUI;
import fr.xam74er1.spigot.Main;
import net.md_5.bungee.api.ChatColor;

public class OnClick implements Listener {

	@EventHandler
	public void onClick(PlayerInteractEvent e) {

		Player p = e.getPlayer();

		Block b = e.getClickedBlock();
		GamePlayer gp = Main.getGame().getPlayer(p.getName());

		//Si il clique sur un block
		if(e.getAction()==Action.RIGHT_CLICK_BLOCK) {



			gp.setSelectBlock(b);

			//evite les double impution , seul moyen de fixe le bug 
			if((System.currentTimeMillis()-gp.getCliquedTime())>100){
				//Si il clique avec la master whant
				if(e.getItem()!=null && e.getItem().isSimilar(GameConstante.masterWhant())) {
					Log.print("operateur il peut cliquer");


					p.sendMessage(ChatColor.DARK_GREEN+"Vous avez selectioner un(e) "+b.getType()+" au coordoner :<"+b.getLocation().getBlockX()+","+b.getLocation().getBlockY()+","+b.getLocation().getBlockZ()+">");



				}else {
					Log.print("Clique non operateur");
					//Si il clique sur une porte 

					if(e.getClickedBlock().getType()==Material.IRON_DOOR) {
						Log.print("Clique sur une iron door");

						//Sois cest une porte a code 

						try {
							gestionPorteACode(b,e,p);

						} catch (EG_Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}




						//Si il sneak pas on verifie que cest un bloque inventaire 
					}else if(!p.isSneaking()){}
					gestionBlockInventory(b,e,p);
				}
			}

			//on set le temps pour etre tanquelle 
			gp.setCliquedTime(System.currentTimeMillis());

			//Si on clique avec la master whant
		}else if(e.getAction()==Action.LEFT_CLICK_BLOCK) {
			if(e.getItem()!=null && e.getItem().isSimilar(GameConstante.masterWhant())) {
				cliqueMasterWhant(b,e,p);
			}
		}


	}
	public void gestionPorteACode(Block b,PlayerInteractEvent e,Player p) throws EG_Exception {

		PorteACode porteACode = new PorteACode(b);

		Log.print(" id : "+porteACode.getId()+" existe : "+porteACode.exist());

		//Si la port a code n'existe pas ds les image 
		if(porteACode.exist()) {
			e.setCancelled(true);

			Main.getGame().getPlayer(p.getName()).setEllemment(porteACode);
			new CodeGUI(p,porteACode);
		}else {

			//Si non cest peutre une porte a clef 
			PorteAClef porteAClef= new PorteAClef(b);


			if(porteAClef.exist()) {
				gestionPorteAClef(e, p, porteAClef);
			}else {
				Log.print("Cette porte existe : "+porteAClef.exist());
			}
		}

		//Door door = (Door) b.getState().getData();


	}
	//gstion des block inventory en fonction du type
	public void gestionBlockInventory(Block b,PlayerInteractEvent e,Player p) {
		Log.print("hello ");
		try {
			BlockInventory binv = new BlockInventory(b);
			//Si cela est bien un block inventory
			
			Log.print("Gestion block inventory pour "+binv.getId()+" de type "+binv.getType());
			if(binv.exist()) {
			
				Main.getGame().getPlayer(p.getName()).setEllemment(binv);

				//2 = block inventory key et il est gere a pare 
				if(binv.getType()==2) {
					BlockInventoryKey binvK = new BlockInventoryKey(binv);
					if(binvK.exist()) {
						gestionBlobckInventoryKey(e,p,binvK);
					}
					//1 = block inventory code et on le gere a partir u GUI 
				}else if(binv.getType()==1) {
					BlockInventoryCode binvC = new BlockInventoryCode(binv);
					if(binvC.exist()) {
						gestionBlobckInventoryCode(e,p,binvC);
					}

					//Si non est un blockInventory normale et on le gere comme telle 
				}else {

					new BlockInventoryGUI(p, binv);
				}
				Log.print("Fin Gestion block inventory "+binv.getId());
				e.setCancelled(true);
			}else {
				Log.print("Ce nest pas un block inventory");
			}
		} catch (EG_Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


	}

	private void gestionBlobckInventoryCode(PlayerInteractEvent e, Player p, BlockInventoryCode binvC) {

		e.setCancelled(true);
		Main.getGame().getPlayer(p.getName()).setEllemment(binvC);
		//Si il a cliquer avec une clef
		new CodeGUI(p,binvC);


		// TODO Auto-generated method stub

	}


	public void gestionBlobckInventoryKey(PlayerInteractEvent e,Player p,BlockInventoryKey bik) {
		e.setCancelled(true);
		Main.getGame().getPlayer(p.getName()).setEllemment(bik);
		//Si il a cliquer avec une clef

		if(e.getItem()!=null) {
			String name = e.getItem().getItemMeta().getDisplayName();

			if(bik.isCorectKey(name)) {
				new BlockInventoryGUI(p, bik);
			}else {
				p.sendMessage(ChatColor.RED+"Ce n'est pas la bonne clef");
			}
		}else {
			Log.print("Item is null");
			p.sendMessage(ChatColor.RED+"Ce n'est pas la bonne clef");
		}
	}



	public void cliqueMasterWhant(Block b,PlayerInteractEvent e,Player p) {



		Log.print("Clique sur une iron door");


		//on verifie si cest une porte a clef ou une porte a code 
		try {
			PorteACode porteACode = new PorteACode(b);



			//Si la port a code n'existe pas ds les image 
			//Si cest une porte a code on lui affiche le code juste 
			if(porteACode.exist()) {
				Log.displaySucesse(p, "Le code est "+porteACode.getCode_juste());
				e.setCancelled(true);
			}else {

				PorteAClef porteAClef = new PorteAClef(b);
				//SI cest une porte a clef on lui donne une copie de la clef
				if(porteAClef.exist()) {
					Log.displaySucesse(p, "Un exmplaire de la clef vous a ete donne ");

					ItemStack it = new ItemStack(Material.TRIPWIRE_HOOK,1);
					ItemMeta itm = it.getItemMeta();

					itm.setDisplayName(porteAClef.getKeyName());
					it.setItemMeta(itm);

					p.getInventory().addItem(it);

					e.setCancelled(true);
				}

			}

			//Door door = (Door) b.getState().getData();

		} catch (EG_Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}




	}
	//lorsque lon clique avec une porte a clef 
	public void gestionPorteAClef(PlayerInteractEvent e,Player p,PorteAClef porteAClef) {
		e.setCancelled(true);
		Main.getGame().getPlayer(p.getName()).setEllemment(porteAClef);
		//Si il a cliquer avec une clef

		if(e.getItem()!=null) {
			String name = e.getItem().getItemMeta().getDisplayName();

			if(porteAClef.isCorectKey(name)) {
				porteAClef.setOppen(!porteAClef.isOppen());
				if(porteAClef.isOppen()) {
					p.sendMessage(ChatColor.GREEN+"Porte ouverte");
				}else {
					Log.displaySucesse(p, "Porte ferme");
				}
			}else {
				p.sendMessage(ChatColor.RED+"Ce n'est pas la bonne clef");
			}
		}else {
			Log.print("Item is null");
		}
	}

}
