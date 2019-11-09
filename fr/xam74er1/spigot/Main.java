package fr.xam74er1.spigot;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import BaseDeDonne.DAO_ini;
import BaseDeDonne.FillDataBase;
import Listener.OnClick;
import Listener.OnInventoryClick;
import Listener.OnInventoryMoveItem;
import Listener.OnJoin;
import Modele.Game;
import Utils.Log;
import Vue.CodeGUI;
import command.*;


public class Main extends JavaPlugin{
	boolean res ;
	String path = "test.db";
	static Game game;
	public static DAO_ini dao;
	 @Override
	    public void onEnable() {
		 
		 //utilitaire
		 this.getCommand("runSQL").setExecutor(new runSQL());
		
		 //cmd de creation 
		 this.getCommand("giveMasterWhant").setExecutor(new giveMasterWhant());
		 this.getCommand("setCodeDoor").setExecutor(new setCodeDoor());
		 this.getCommand("setKeyDoor").setExecutor(new setKeyDoor());
		 this.getCommand("blockInventory").setExecutor(new cmdBlockInventory());
		 this.getCommand("setBlockInventoryKey").setExecutor(new cmdBlockInventoryKey());
		 this.getCommand("setBlockInventoryCode").setExecutor(new cmdBlockInventoryCode());
		 
		 //cmd de gestion de la game
		 this.getCommand("newGame").setExecutor(new cmdNewGame());
		 this.getCommand("setGameOption").setExecutor(new cmdSetGameOption());
		 this.getCommand("setGameTime").setExecutor(new cmdsetGameTime());
		 this.getCommand("GameList").setExecutor(new cmdGameList());
		 this.getCommand("loadGame").setExecutor(new cmdLoadGame());
		 this.getCommand("gamemanager").setExecutor(new cmdGameManger());
		 this.getCommand("gameStart").setExecutor(new cmdGameManger());
		 
		 //cmd gestion partie
		 this.getCommand("partyadd").setExecutor(new cmdParty());
		 this.getCommand("party").setExecutor(new cmdParty());
		 this.getCommand("partyremove").setExecutor(new cmdParty());
		 this.getCommand("partydisband").setExecutor(new cmdParty());
		 
		 this.getCommand("startsetting").setExecutor(new cmdStartSetting());
		 
		
		 
		 //listener
		 getServer().getPluginManager().registerEvents(new OnClick(), this);
		 getServer().getPluginManager().registerEvents(new OnJoin(), this);
		 getServer().getPluginManager().registerEvents(new OnInventoryClick(), this);
		 getServer().getPluginManager().registerEvents(new OnInventoryMoveItem(), this);
		
		 
		 setupDataBase();
		 
		 game = new Game("test");
		 if(!game.existe()) {
			 try {
				 Log.print("la game existe pas on en cree une ");
				game = new Game("test",60,"in dev",null);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				 game = new Game("test",true);
				e.printStackTrace();
			}
		 }
		 
		 for(Player p :  getServer().getOnlinePlayers()) {
			 game.addPlayer(p);
		 }
		 
		 Log.print("Reload fini pour EG");
		
		 
	    }
	    // Fired when plugin is disabled
	    @Override
	    public void onDisable() {
	    	game.stop();
	    }
	    
	    public void setupDataBase() {

			
			 dao = new DAO_ini();
			
			
			res = dao.createDB(path);
			
			if(!res) {
				dao.connect(path);
			}
			
			dao.ini(dao.getConnextion());
			
			FillDataBase.fill();
	    }
	    
	    /*
	     * TODO LIST 
	     * 
	     * Create config file
	     * verife que la db ce cree bien lors de lisntalation du pl si ya pas de db
	     * Pense a fair tout les remouve
	     * Pense a remouve les game
	     * changer lendrois de la db
	     * fair les inv avec code et clef 
	     * **************
	     * Lorsque lon ferme la perote marque porte ferme :OK
	     * Code avec uniquement des chiffre fair une erreur si le cas contraire : OK
	     * pense a mettre le 0 pour la porte a code en tete  : OK
	     * cancle event when break wis master whant :Ok
	     * Si on set une key desative le code pour les door
	     * Si clique gauche avec la master whant sur une porte a clef ou a code donne le code ou re give la clef : Ok 
	     * 
	     * Si une perssone est dans inventrie empche les autre dy allez
	     * 
	     * bug party list : OK 
	     * 
	     * dire a tout les joeur quand la game fini
	     * 
	     * mettre tout les chiffre sur 2 nombre : Ok
	     * 
	     * bug avec le settime qui met systemtiqument une erreur : Non reporductible sur mon serveur a voir 
	     * 
	     * command /indice
	     * 
	     * command compatible command block
	     * 
	     * pour la binvKey et binvCode rajoute une commande a binv avec les option key ou code :OK
	     * 
	     * 
	     * BUG : Si on set un blockInventoryKey (ou code) , puis que lon redefini un block inventory normale il est de type block inventoryKey(ou code) , cela est du a la double table a gere 
	     * 
	     * Pense a bien teste le /blockinventory code|key voir si il reagis bien 
	     * 
	     * Fair un delete des item
	     * 
	     */
	    public static Game getGame() {
	    	return game;
	    }
	    
	

}
