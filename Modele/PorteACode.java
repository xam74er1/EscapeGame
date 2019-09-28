package Modele;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;

import org.bukkit.material.Door;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Openable;

import BaseDeDonne.DAO_EG_Location;
import BaseDeDonne.DAO_PorteACode;
import Utils.EG_Exception;
import Utils.Log;

public class PorteACode extends Ellement implements CodeInterface{

	String code_juste="";
	String code_Actuelle ="";
	boolean isOppen=false;
	Block b = null;
	EG_Loccation loc;
	int id=-1;

	//Taille du code 
	static int MAXSIZE = 4;
	static DAO_PorteACode dao=null;

	public PorteACode(Block b) throws EG_Exception {

		
		this.b = b;

		if(dao==null)
			dao = new DAO_PorteACode();

		//L'iron door fait 2 block de haut il faut sassure de n'enregistre que celle du bas 
		if(b.getType()==Material.IRON_DOOR) {
			Block tmpB = b.getWorld().getBlockAt(b.getX(), b.getY()-1, b.getZ());

			if(tmpB.getType() == Material.IRON_DOOR) {
				b = tmpB;
			}

		}

		this.loc = new EG_Loccation(b.getLocation());

		PorteACode tmp = dao.find(loc.getId());

		if(tmp!=null){
			setCode_Actuelle( tmp.code_Actuelle);
			setCode_juste( tmp.code_juste);
			this.id = tmp.id;
			this.isOppen = tmp.isOppen;
			//dao.update(this);
		}


	}


//Utilise par le DAO
	public PorteACode(int id, String code_juste, String code_Actuelle, boolean isOppen, EG_Loccation loc) throws EG_Exception {
		super();
		this.code_juste =  code_juste;
		this.code_Actuelle = code_Actuelle;
		this.isOppen = isOppen;
		this.loc = loc;
	}
//Utilise par le DAO
	public PorteACode(int id,String code_juste, String code_Actuelle, boolean isOppen, int loc_id) throws EG_Exception {
		super();
		Log.print(code_juste);
		this.code_juste=  code_juste;
		this.code_Actuelle = code_Actuelle;
		this.isOppen = isOppen;
		this.loc = new DAO_EG_Location().find(loc_id);
		this.id = id;
	}



	public PorteACode(String code_juste, String code_Actuelle, Block b) throws EG_Exception {
		super();

		if(dao==null)
			dao = new DAO_PorteACode();


		//L'iron door fait 2 block de haut il faut sassure de n'enregistre que celle du bas 
		if(b.getType()==Material.IRON_DOOR) {
			Block tmpB = b.getWorld().getBlockAt(b.getX(), b.getY()-1, b.getZ());

			if(tmpB.getType() == Material.IRON_DOOR) {
				b = tmpB;
			}

		}


		this.loc = new EG_Loccation(b.getLocation());

		PorteACode tmp = dao.find(loc.getId());

		if(tmp!=null) {
			Log.print("Existe deja on Update");
			
			this.code_Actuelle = code_Actuelle;
			setCode_juste( code_juste);
			
			this.id = tmp.id;
			dao.update(this);
		}else {
			Log.print("Existe PAS on cree");
			try {
				setCode_juste( code_juste);
				this.code_Actuelle = code_Actuelle;
				this.b = b;

				this.id = dao.getMaxID(dao.getTableName());
				dao.insert(this);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				if(e instanceof EG_Exception)
					throw (EG_Exception) e;
			}




		}

	}




	public void load(EG_Loccation loc) {
		// TODO Auto-generated method stub

	}


	public String getCode_juste() {
		return code_juste;
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

	public boolean isOppen() {
		return isOppen;
	}

//Set a oubrire la porte ainsi que une save en DB
	public void setOppen(boolean isOppen) {
		this.isOppen = isOppen;
		dao.update(this);
		
		
			if(b!=null) {
//				Door door = (Door) b.getState().getData();
//				
//				door.setOpen(isOppen);
//			
//				b.getState().update();
				
				Block tmpB = b.getWorld().getBlockAt(b.getX(), b.getY()-1, b.getZ());

				if(tmpB.getType() == Material.IRON_DOOR) {
					b = tmpB;
				}
				
				BlockState blockState = b.getState();
				if(((Door) blockState.getData()).isTopHalf()){
				    blockState = b.getRelative(BlockFace.DOWN).getState();
				}

			//	Door door = (Door) blockState.getData();
				
				Openable openable = (Openable) blockState.getData();
				openable.setOpen(isOppen);
				blockState.setData((MaterialData) openable);

				blockState.update();
				
				
				//Log.print(door.isOpen()+" is oppen "+isOppen);
			
			
		}else {
			Log.print("Pas de block");
		}
	}



	public Block getBlock() {
		return b;
	}



	public void setBlock(Block b) {
		this.b = b;
	}



	public EG_Loccation getLococation() {
		return loc;
	}



	public void setLocation(EG_Loccation loc) {
		this.loc = loc;
	}



	public Block getB() {
		return b;
	}



	public void setB(Block b) {
		this.b = b;
	}



	public EG_Loccation getLoc() {
		return loc;
	}



	public void setLoc(EG_Loccation loc) {
		this.loc = loc;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public static int getMAXSIZE() {
		return MAXSIZE;
	}



	public static void setMAXSIZE(int mAXSIZE) {
		MAXSIZE = mAXSIZE;
	}


	@Override
	public void getOppen() {
		// TODO Auto-generated method stub
		
	}



}
