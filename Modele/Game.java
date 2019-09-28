package Modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import BaseDeDonne.DAO_Game;
import Modele.Action.ActionInGame;
import Utils.EG_Exception;
import Utils.Log;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_14_R1.BossBattle.BarColor;

public class Game {

	boolean inDev = true;

	String name;
	String description;

	int time = 3600;//En seconde

	HashMap<String,GamePlayer> listPlayer ;
	HashMap<String,GamePlayer> listParty ;
	ArrayList<ActionInGame> listAction = new ArrayList<ActionInGame>();

	CronoMetre crono = null;

	String nameTmp = "";

	int id=-1;

	DAO_Game dao= null;

	boolean EnCour = false;

	public Game( String name,boolean inDev) {
		super();
		this.inDev = inDev;
		this.name = name;
		listPlayer = new HashMap<String, GamePlayer>();
		listParty = new HashMap<String, GamePlayer>();
		Log.print("Constructor in dev");
	}

	public Game(String name) {
		if(dao==null)
			dao= new DAO_Game();
		Game tmp = dao.find(name);
		if(tmp!=null) {
			this.id = tmp.id;
			this.name = tmp.name;
			this.description = tmp.description;
			this.time = tmp.time;
			this.listAction = tmp.listAction;

			listPlayer = new HashMap<String, GamePlayer>();
			listParty = new HashMap<String, GamePlayer>();

			addAllPlayer();
		}

	}

	//Pour le dao
	public Game(int id,String name,int time, String description,String listAction) {
		super();
		if(dao==null)
			dao= new DAO_Game();

		try {
			addAction(listAction);
		} catch (EG_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.id = id;
		this.name = name;
		this.description = description;
		this.time = time;
		listPlayer = new HashMap<String, GamePlayer>();
		addAllPlayer();

	}


	public Game(String name,int time, String description,String listAction) throws Exception {
		super();
		if(dao==null)
			dao= new DAO_Game();
		Game tmp = dao.find(name);
		if(tmp!=null) {

			this.name = name;
			this.description = description;
			this.time = time;
			listPlayer = new HashMap<String, GamePlayer>();
			addAllPlayer();

			this.id = tmp.getId();
			dao.update(this);

		}else {
			this.name = name;
			this.description = description;
			this.time = time;
			listPlayer = new HashMap<String, GamePlayer>();
			addAllPlayer();
			dao.insert(this);
		}
		try {
			addAction(listAction);
		}catch(Error e) {
			e.printStackTrace();
		}


	}


	/////////////// PARTY AND PLAYER LIST ////////////////////
	public void clearParty() {

		//On notifie tout les player puis on les enleve

		for(GamePlayer gp : listParty.values()) {
			remouvePlayerParty(gp.getP().getName());
		}

	}

	public boolean addPlayerParty(String name) {
		if(name.equalsIgnoreCase("all")) {

			listParty.putAll(listPlayer);
			Bukkit.broadcastMessage("Vous ave ete rajoute a la partie");
			return true;
		}else {
			if(listPlayer.containsKey(name)) {
				GamePlayer gp = listPlayer.get(name);
				listParty.put(name,gp);

				Log.displaySucesse(	listPlayer.get(name).getP(), "Vous avez ete ajoute a une partie");
				return true;
			}else {
				return false;
			}
		}

	}

	public GamePlayer getPlayerParty(String name) {
		return listParty.get(name);
	}

	public boolean remouvePlayerParty(String name) {
		if(listParty.containsKey(name)) {
			listParty.remove(name);
			Log.displayError(	listPlayer.get(name).getP(), ChatColor.GREEN+"Vous avez retirez d' une partie");
			return true;
		}else {
			return false;
		}
	}


	public void addPlayer(Player p) {
		if(!listPlayer.containsKey(p.getName())) {
			listPlayer.put(p.getName(), new GamePlayer(this, p));
			Log.print("Ajout de "+p.getName());
		}
	}


	public GamePlayer getPlayer(String name) {
		return listPlayer.get(name);
	}


	public void addAllPlayer() {

		for(Player p :Bukkit.getServer().getOnlinePlayers() ) {
			addPlayer(p);
		}
	}


	//////////////// CRONO ///////////////::

	public void start() {
		EnCour = true;
		if(crono != null) {
			stop();
		}
		crono = new CronoMetre(time, this);
		crono.start();
		runAllAction();
	}

	public void stop() {
		if(crono!=null) {
			crono.stop();
			EnCour = false;
		}
	}

	public void pause() {
		if(crono!=null) {
			crono.pause();
		}
	}

	public boolean existe() {
		return id!=-1;
	}


	public void setTime(int time) {
		this.time = time;
		if(id!=-1) {
			dao.update(this);
		}else {
			Log.print("Id :"+id);
		}
	}


	///////////// LIST ACTION ////////////////::


	public void addAction(String str) throws EG_Exception {

		if(str !=null) {

			String tabAc[] = str.split("\n");
			for(String s :tabAc){
				ActionInGame ac = ActionInGame.createAction(s);



				if(ac!=null) {
					addAction(ac);
				}else {
					if(id!=-1)
						throw new EG_Exception("Format invalide pour une action \n format valide : msg <message> | ou | tp <x> <y> <z> ");
				}

			}
		}

	}

	public void addAction(ActionInGame action) {
		listAction.add(action);

		if(id!=-1) {
			dao.update(this);
		}
	}

	public void displayAction(Player p) {
		p.sendMessage(ChatColor.GOLD+" \n List des action : ");
		int i  = 0;
		for(ActionInGame ac : listAction) {
			p.sendMessage(ChatColor.GOLD+""+i+ChatColor.RESET+" : "+ac.toString());
			i++;
		}
	}

	public void remouveAction(int index) throws EG_Exception {
		if(listAction.size()>0){
			listAction.remove(index);
			if(id!=-1) {
				dao.update(this);
			}
		}else {
			throw new EG_Exception("Il n'existe pas d'action "+index);
		}
	}

	public void clearAction() {
		listAction.clear();
		if(id!=-1) {
			dao.update(this);
		}
	}

	public String actionToString() {
		String str ="";
		for(ActionInGame ac : listAction) {
			str+=ac.toString()+"\n";
		}

		return str;
	}

	public void createActionFromString(String str) {
		if(str!=null) {
			String tab [] = str.split("\n");

			for(String s : tab) {
				try {
					ActionInGame ac = 	ActionInGame.createAction(s);

					if(ac!=null) {
						listAction.add(ac);
					}

				} catch (EG_Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void runAllAction() {
		for(ActionInGame ac : listAction) {
			try {
				ac.applyAction(listParty.values());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Bukkit.broadcastMessage(ChatColor.RED+"Une erreur est survenue lors du lancement de la partie");
				e.printStackTrace();
			}
		}
	}

	//////////////////////////////////////////////

	public boolean isInDev() {
		return inDev;
	}

	public void setInDev(boolean inDev) {
		this.inDev = inDev;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		if(id!=-1) {
			dao.update(this);
		}
	}

	public HashMap<String, GamePlayer> getListPlayer() {
		return listPlayer;
	}

	public void setListPlayer(HashMap<String, GamePlayer> listPlayer) {
		this.listPlayer = listPlayer;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
		if(id!=-1) {
			dao.update(this);
		}
	}


	public int getTime() {
		return time;
	}




	public CronoMetre getCrono() {
		return crono;
	}




	public void setCrono(CronoMetre crono) {
		this.crono = crono;
	}




	public String getNameTmp() {
		return nameTmp;
	}




	public void setNameTmp(String nameTmp) {
		this.nameTmp = nameTmp;
	}




	public int getId() {
		return id;
	}




	public void setId(int id) {
		this.id = id;
	}

	public HashMap<String, GamePlayer> getListParty() {
		return listParty;
	}

	public void setListParty(HashMap<String, GamePlayer> listParty) {
		this.listParty = listParty;
	}

	public boolean isEnCour() {
		return EnCour;
	}

	public void setEnCour(boolean enCour) {
		EnCour = enCour;
	}

	public ArrayList<ActionInGame> getListAction() {
		return listAction;
	}

	public void setListAction(ArrayList<ActionInGame> listAction) {
		this.listAction = listAction;
	}



}
