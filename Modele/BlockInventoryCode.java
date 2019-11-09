package Modele;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import BaseDeDonne.DAO_BlockInventory;
import BaseDeDonne.DAO_BlockInventoryCode;
import BaseDeDonne.DAO_BlockInventoryKey;
import Utils.EG_Exception;
import Utils.Log;
import net.md_5.bungee.api.ChatColor;

public class BlockInventoryCode extends BlockInventory implements CodeInterface{

	boolean oppen = false;
	DAO_BlockInventoryCode dao = new DAO_BlockInventoryCode();

	String code_juste="";
	String code_Actuelle ="";
	static int MAXSIZE = 4;

	public BlockInventoryCode(Block b) throws EG_Exception {
		super(b);
		// TODO Auto-generated constructor stub
		this.type = 1;
	}

	public BlockInventoryCode(BlockInventory binv) throws EG_Exception {
		// On remplis liventaire parent 
		super(binv.getId(),binv.getInv(),1,binv.getLocation().getId());
		//On verifife si cette inventaire est bien un inv key
		BlockInventoryCode bic = dao.find(binv);
		//Si cest le cas on remplis avec les info recupere , si non on set son id a -1 pour dire quil n'existe pas
		if(bic!=null) {
			this.code_juste = bic.getCode_juste();
			this.code_Actuelle = bic.getCode_Actuelle();
			this.oppen = bic.isOppen();
		}else {
			this.id = -1;
		}

	}


	public BlockInventoryCode(Block b ,String code_juste, String code_actuelle,boolean isOppen) throws Exception {

		super(1,b);

		setCode_juste(code_juste);
		setCode_Actuelle(code_actuelle);
		this.oppen = isOppen;
		//Si il existe
		BlockInventoryCode bik = dao.find(this);
		if(bik!=null) {
			dao.update(this);
		}else {
			dao.insert(this);
		}

	}


	public BlockInventoryCode(BlockInventory binv , String code_actuelle,String code_juste,boolean isOppen) throws EG_Exception {
		//int id, Inventory inv,int type, int loc_id
		super(binv.getId(),binv.getInv(),1,binv.getLocation().getId());
		setCode_juste(code_juste);
		setCode_Actuelle(code_actuelle);

		this.oppen = isOppen;

		this.type = 2;

	}




	public boolean isOppen() {
		// TODO Auto-generated method stub
		return this.oppen;
	}

	@Override
	public void setOppen(boolean oppen) {
		this.oppen = oppen;
		if(exist()) {
			dao.update(this);
		}

	}




	public void create() throws Exception {
		dao.insert(this);
	}


	//Met le bon code avec des retour derreur en cas de pbr
	public void setCode_juste(String code_juste) throws EG_Exception {

		try {

			int code = Integer.parseInt(code_juste);

			if(code<0) {
				throw new EG_Exception("Le code dois etre plus grand ou egale a 0");
			}

			if (code>Math.pow(10, MAXSIZE)) {
				throw new EG_Exception("Taille du code est superrieur a "+Math.pow(10, MAXSIZE));
			}

			this.code_juste = code_juste;
			if(this.getId()!=-1)
				dao.update(this);
		}catch(java.lang.NumberFormatException e) {
			throw new EG_Exception("Le code dois etre un nombre entre 0 et 9999");
		}
	}



	public boolean exist() {
		return getId()!=-1;
	}

	public String getCode_Actuelle() {
		return code_Actuelle;
	}


	public void setCode_Actuelle(String code_Actuelle) {
		//Log.print("set code actuelle original "+this.code_Actuelle+" nv "+code_Actuelle);

		//Si le code actelle est plus cour que le code final on gade que le debut du code actuelle 
		if(code_Actuelle.length()>code_juste.length()&&exist()) {
			String tmp = "";

			for(int i = 0;i<code_juste.length();i++) {
				tmp += code_Actuelle.charAt(i);
			}
			code_Actuelle = tmp;
		}else if(code_Actuelle.length()<code_juste.length()) {

			Log.print("ya un pbr");

		}
		this.code_Actuelle = code_Actuelle;
		Log.print("id : "+this.id);
		if(this.getId()!=-1)
			dao.update(this);
	}


	public boolean code_corect() {
		//On aplique une transofmation si le code acyuelle est tros grand 
		if(code_Actuelle.length()!=code_juste.length()) {
			setCode_Actuelle(code_Actuelle);
		}

		return code_juste.equalsIgnoreCase(code_Actuelle);
	}


	@Override
	public EG_Loccation getLoc() {
		// TODO Auto-generated method stub
		return location;
	}

	@Override
	public void setLoc(EG_Loccation loc) {
		// TODO Auto-generated method stub
		this.location = loc;
	}



	@Override
	public String getCode_juste() {
		// TODO Auto-generated method stub
		return code_juste;
	}

	@Override
	public void action_if_correct(Player p) {
		// TODO Auto-generated method stub
		p.sendMessage(ChatColor.GREEN+"Le code est correcte");
		p.openInventory(this.getInventory());
	}


	@Override
	public void action_if_not_corect(Player p) {
		// TODO Auto-generated method stub
		p.sendMessage(ChatColor.RED+"Le code est incorecte");
		
	}

	@Override
	public void setB(Block b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Block getB() {
		// TODO Auto-generated method stub
		return null;
	}



}
