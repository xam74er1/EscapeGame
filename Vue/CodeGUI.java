package Vue;


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import Listener.OnJoin;
import Modele.CodeInterface;
import Modele.EG_Loccation;
import Modele.Ellement;
import Modele.GamePlayer;
import Modele.PorteACode;
import Utils.EG_Exception;
import Utils.HeadList;
import Utils.Log;
import fr.xam74er1.spigot.Main;
import net.md_5.bungee.api.ChatColor;


public class CodeGUI implements InventoryHolder{

	Player p;
	CodeInterface code;



	Inventory inv = null;
	static String name = "Porte a code";
	int size = 27;

	public CodeGUI(Player p, CodeInterface code) {
		super();
		this.p = p;
		this.code = code;
		createInventory();
		fillInventory(inv);
		showView(p);

		//Bukkit.getServer().getPluginManager().registerEvents(this,Bukkit.);
	}


	public CodeGUI() {

	};


	public void showView(Player p) {
		p.openInventory(inv);
	}

	public void createInventory() {
		inv = Bukkit.createInventory(this, size,name);


	}

	public void fillInventory(Inventory inv) {

		ItemStack black_glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE,1);

		for(int i = 0;i<size;i++) {
			inv.setItem(i,black_glass);
		}

		String actuel_code = code.getCode_Actuelle();
		String code_Juste = code.getCode_juste();
		
		//On verifie que le code actuelle nest pas plus grand que le code juste
		Log.print("actuale code size "+actuel_code.length()+" code juste size "+code_Juste.length()+" code actuelle "+actuel_code+" code juste "+code_Juste);
		if(actuel_code.length()>code_Juste.length()) {
		
			actuel_code ="";
			for(int i = 0;i <code_Juste.length() ;i++ ) {
				actuel_code += code.getCode_Actuelle().charAt(i);
			}
		}else if (actuel_code.length()<code_Juste.length()){
			
			int dif =  code_Juste.length() - actuel_code.length();
			Log.print("dif = "+dif);
			String zero = "";
			for(int i = 0;i<dif;i++) {
				zero+="0";
			}
			
			actuel_code = zero + actuel_code;
			Log.print("acutalCode "+actuel_code);
			
		}

		//On comence au slot 12
		int index = 11;

		int count = 0;
		char[] list = actuel_code.toCharArray();
		for(char s : list) {

			ItemStack head = HeadList.getHead(s+"");
			inv.setItem(index, head);

			head = HeadList.getHead(HeadList.UP.getName());
			inv.setItem(index-9, head);

			head = HeadList.getHead(HeadList.DOWN.getName());
			inv.setItem(index+9, head);


			index++;
			count++;
			if(count>3) {
				count++;
				break;
			}

		}

		inv.setItem(25, HeadList.getHead("VALIDER"));

	}


	@Override
	public Inventory getInventory() {
		// TODO Auto-generated method stub
		return inv;
	}


	/*
	 * 
	 * Lorsque lon clique dans licentaire du player , est reference dans la main pour le listener 
	 * 
	 */

	
	public static void onInventoryClick(InventoryClickEvent e) throws EG_Exception {
		Player p = (Player) e.getWhoClicked();
		ItemStack it = e.getCurrentItem();

		Inventory inv = e.getInventory();

		InventoryView w = e.getView();

		//SI on a bien cliquer sur un cheast et que lon est sur une porte

		if(inv.getType()==InventoryType.CHEST&&  w.getTitle().equalsIgnoreCase(name)) {

			e.setCancelled(true);
			if(it!=null) {

				//Si on clique sur une tete de plauyer

				if(it.getType()==Material.PLAYER_HEAD) {

					Log.print("Clique sur une tete");
					//On recure le nom et tout les ellment 
					String name = it.getItemMeta().getDisplayName();
					int indexSlot = e.getSlot();
					GamePlayer gp = Main.getGame().getPlayer(p.getName());
					//On recure lobject a code 
					Ellement el = gp.getEllement();

					//Si cela nest pas une porte a code qui est selectione 
					if(!(el instanceof CodeInterface)) {
						Log.print("Erreur de classe "+el.getClass()+"");
						throw new EG_Exception("Ce si nest pas une porte a code");
					}

					//On convertis en porte a code 
					CodeInterface pac = (CodeInterface) el;



					//on verifie quelle type de tete a ete cliquer
					if(name.equalsIgnoreCase(HeadList.UP.getName())) {
						applyCode(inv,p,pac,indexSlot,1);
					}else if(name.equalsIgnoreCase(HeadList.DOWN.getName())) {
						applyCode(inv,p,pac,indexSlot,-1);

					}else if(name.equalsIgnoreCase(HeadList.VALIDER.getName())) {
						if(pac.code_corect()) {
							p.closeInventory();
							pac.action_if_correct(p);
							
						}else {

							pac.action_if_not_corect(p);
						}

				
					}

				}
			}
		}


		return;

	}

	public static void applyCode(Inventory inv,Player p ,CodeInterface pac,int indexSlot,int incrementation) {

		try {
			Log.print("Index slot : "+indexSlot);
			String itemName = inv.getItem(indexSlot+9*incrementation).getItemMeta().getDisplayName();
			int num = Integer.parseInt(itemName);

			//postion dans la string

			int position = (indexSlot%9)-2;
			String nvCode = pac.getCode_Actuelle();

			Log.print("Code actuelle"+nvCode);

			num+=incrementation;

			if(num>=10) {
				num= 0;
			}else if(num<0) {
				num = 9;
			}



			if(position==nvCode.length()) {
				nvCode = nvCode.substring(0, position)+num;
			}else if(position==0){
				nvCode = num+nvCode.substring(position+1);
			}else {
				nvCode = nvCode.substring(0, position)+num+nvCode.substring(position+1);
			}

			pac.setCode_Actuelle(nvCode);

			ItemStack newHead = HeadList.getHead(num+"");

			inv.setItem(indexSlot+9*incrementation, newHead);



			//Update
			p.openInventory(inv);

			Log.print("nv code ="+nvCode);

		}catch(Exception e3){
			e3.printStackTrace();
		}




	}


	public static String getName() {
		return name;
	}


	public static void setName(String name) {
		CodeGUI.name = name;
	}
	
	

}
