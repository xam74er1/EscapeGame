package Modele;

import java.sql.SQLException;

import org.bukkit.Location;

import BaseDeDonne.DAO_EG_Location;
import Utils.Log;

public class EG_Loccation extends Ellement{

	int x,y,z,id=-1;
	String worldName ="";
	static DAO_EG_Location dao = null;
	public EG_Loccation(int x, int y, int z, String worldName) {
		super();

		if(dao==null)
			dao = new DAO_EG_Location();

		EG_Loccation tmp = dao.find(x,y,z,worldName);

		//Si il est null on en cree un nouveau
		if(tmp==null) {

			this.x = x;
			this.y = y;
			this.z = z;
			this.worldName = worldName;
			try {
				this.id = 	dao.getMaxID("Location")+1;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			saveDB();
		}else {
			Log.print("Exite deja");
			this.x = tmp.getX();
			this.y = tmp.getY();
			this.z = tmp.getZ();

			this.worldName = tmp.getWorldName();
			this.id = tmp.getId();
		}

	}

	public EG_Loccation(int x, int y, int z, String worldName,int id) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.worldName = worldName;
		this.id = id;
		if(dao==null)
			dao = new DAO_EG_Location();

	}

	public EG_Loccation(Location loc) {
		// TODO Auto-generated constructor stub

		if(dao==null)
			dao = new DAO_EG_Location();

		EG_Loccation tmp = dao.find(loc);

		//Si il est null on en cree un nouveau
		if(tmp==null) {

			this.x = loc.getBlockX();
			this.y = loc.getBlockY();
			this.z = loc.getBlockZ();

			this.worldName = loc.getWorld().getName();

			Log.print("Cree nouveau");
			try {
				this.id = 	dao.getMaxID("Location")+1;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			saveDB();
		}else {

			Log.print("Exite deja");
			this.x = tmp.getX();
			this.y = tmp.getY();
			this.z = tmp.getZ();

			this.worldName = tmp.getWorldName();
			this.id = tmp.getId();
		}

	}


	@Override
	public void saveDB() {
		try {
			dao.insert(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static EG_Loccation load(int id) {
		if(dao==null)
			dao = new DAO_EG_Location();
		return dao.find(id);

	}

	@Override
	public String toString() {
		return "EG_Loccation [x=" + x + ", y=" + y + ", z=" + z + ", worldName=" + worldName + "]";
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getZ() {
		return z;
	}
	public void setZ(int z) {
		this.z = z;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getWorldName() {
		return worldName;
	}
	public void setWorldName(String worldName) {
		this.worldName = worldName;
	}
	public static DAO_EG_Location getDao() {
		return dao;
	}
	public static void setDao(DAO_EG_Location dao) {
		EG_Loccation.dao = dao;
	}





	////////////////////////////////////////////




}
