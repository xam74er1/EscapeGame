package fr.xam74er1.spigot;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import BaseDeDonne.DAO_Game;
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
		this.getCommand("setblockInventory").setExecutor(new cmdBlockInventory());
		this.getCommand("setBlockInventoryKey").setExecutor(new cmdBlockInventoryKey());
		this.getCommand("setBlockInventoryCode").setExecutor(new cmdBlockInventoryCode());
		this.getCommand("deleteBlock").setExecutor(new cmdDeleteBlock());

		//cmd de gestion de la game
		this.getCommand("newGame").setExecutor(new cmdNewGame());
		this.getCommand("setGameOption").setExecutor(new cmdSetGameOption());
		this.getCommand("setGameTime").setExecutor(new cmdsetGameTime());
		this.getCommand("GameList").setExecutor(new cmdGameList());
		this.getCommand("loadGame").setExecutor(new cmdLoadGame());
		this.getCommand("gamemanager").setExecutor(new cmdGameManger());
		this.getCommand("gameStart").setExecutor(new cmdGameManger());
		this.getCommand("gameinfo").setExecutor(new cmdGameManger());


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


			try {
				Log.print("la game existe pas on en cree une ");
				loadGame();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				game = new Game("test",true);
				e.printStackTrace();
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

	public void loadGame() throws Exception {
		Game select = new DAO_Game().findSelectGame();
		if(select!=null) {
			game = select;
			Log.print("Une nouvelle game a ete charge ");
		}else {
			Log.print("Aucune game n'existe nous en meton une par defaut ");
			game = new Game("default",60,"default game",null);
			game.setSelected(true);
		}
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
	 * Commande de resete pour commence une nvll database
	 * Cmd de save 
	 * Create config file
	 * verife que la db ce cree bien lors de lisntalation du pl si ya pas de db
	 * Pense a remouve les game
	 * changer lendrois de la db
	 * **************
	 * Si on set une key desative le code pour les door
	 * 
	 * Si une perssone est dans inventrie empche les autre dy allez : a voir si corige 
	 * 
	 * dire a tout les joeur quand la game fini
	 * 
	 * bug avec le settime qui met systemtiqument une erreur : Non reporductible sur mon serveur a voir 
	 * 
	 * command /indice
	 * 
	 * command compatible command block
	 * 
	 * 
	 * Pense a bien teste le /blockinventory code|key voir si il reagis bien 
	 * 
	 * BUG
	 * 
	 * newgame SI une game a une erreur et que le temps est de 0s fair une erreur ,meme si aucune cmd est vallide sortir une erreur par raprot au temps 
	 * 
	 * startseting : ajoute la possiblite de mettre des vague 
	 * 
	 * setcodedorr : changer le msg derreur pour quil sois plus explicite du jore : "le code dois etre entre 0 et 9999 " , si le code commence par des 0 cela pose des pbr , les regle
	 * 
	 * TRES GROS BUG QUAND PLUTIEURE PERSSONE SON DANS UN INVENTAIRE : appele recusve a un endrois quand plutire perssone clique 
	 * 
	 */
	public static Game getGame() {
		return game;
	}


	public static void setGame(Game nvgame) {
		game = nvgame;
		nvgame.setSelected(true);
	}



}
