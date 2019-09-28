package Modele;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.material.Door;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Openable;

import BaseDeDonne.DAO_EG_Location;
import BaseDeDonne.DAO_PorteAClef;
import Utils.EG_Exception;
import Utils.Log;

public class PorteAClef extends Ellement implements ClefInterface{
	String keyName=null;
	boolean oppen=false;
	Block b = null;
	EG_Loccation loc;
	int id=-1;




	static DAO_PorteAClef dao=null;

	public PorteAClef(Block b) throws EG_Exception {


		this.b = b;

		if(dao==null)
			dao = new DAO_PorteAClef();

		//L'iron door fait 2 block de haut il faut sassure de n'enregistre que celle du bas 
		if(b.getType()==Material.IRON_DOOR) {
			Block tmpB = b.getWorld().getBlockAt(b.getX(), b.getY()-1, b.getZ());

			if(tmpB.getType() == Material.IRON_DOOR) {
				b = tmpB;
			}

		}

		this.loc = new EG_Loccation(b.getLocation());

		PorteAClef tmp = dao.find(loc.getId());

		if(tmp!=null){

			setKeyName(tmp.getKeyName());
			this.id = tmp.id;
			this.oppen = tmp.oppen;
			//dao.update(this);
		}


	}


	//Utilise par le DAO
	public PorteAClef(int id, String keyname, boolean isopen, EG_Loccation loc) throws EG_Exception {
		super();


		this.keyName = keyname;
		this.oppen = isopen;
		this.loc = loc;
		this.id = id;
	}
	//Utilise par le DAO
	public PorteAClef(int id, String keyname, boolean isopen, int loc_id) throws EG_Exception {
		super();


		this.keyName = keyname;
		this.oppen = isopen;
		this.loc = new DAO_EG_Location().find(loc_id);
		this.id = id;
	}



	public PorteAClef(String keyname, Block b) throws EG_Exception {
		super();

		if(dao==null)
			dao = new DAO_PorteAClef();


		//L'iron door fait 2 block de haut il faut sassure de n'enregistre que celle du bas 
		if(b.getType()==Material.IRON_DOOR) {
			Block tmpB = b.getWorld().getBlockAt(b.getX(), b.getY()-1, b.getZ());

			if(tmpB.getType() == Material.IRON_DOOR) {
				b = tmpB;
			}

		}


		this.loc = new EG_Loccation(b.getLocation());

		PorteAClef tmp = dao.find(loc.getId());

		if(tmp!=null) {
			Log.print("Existe deja on Update");


			setKeyName(keyname);

			this.id = tmp.id;
			dao.update(this);
		}else {
			Log.print("Existe PAS on cree");
			try {
				setKeyName(keyname);

				this.b = b;

				this.id = dao.getMaxID(dao.getTableName());
				int nbLine=	dao.insert(this);

				Log.print("nb instert : "+nbLine);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}




		}

	}



	public String getKeyName() {
		return keyName;
	}
	public void setKeyName(String keyName) {

		this.keyName = keyName;

		if(this.getId()!=-1)
			dao.update(this);
	}
	public boolean isOppen() {
		return oppen;
	}
	public void setOppen(boolean oppen) {
		this.oppen = oppen;


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
			openable.setOpen(oppen);
			blockState.setData((MaterialData) openable);

			blockState.update();


			//Log.print(door.isOpen()+" is oppen "+isOppen);


		}else {
			Log.print("Pas de block");
		}
	}
	public Block getB() {
		return b;
	}
	public void setB(Block b) {
		this.b = b;
	}
	public EG_Loccation getLococation() {
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


	@Override
	public boolean isCorectKey(String str) {
		// TODO Auto-generated method stub
		Log.print("key used :"+str+"| is corect"+keyName.equalsIgnoreCase(str));
		return keyName.equalsIgnoreCase(str);
	}
	public boolean exist() {
		return getId()!=-1;
	}


}
