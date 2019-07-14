package Modele;

import BaseDeDonne.DAO_EG_Location;

public class EG_Loccation {
	
	int x,y,z,id=-1;
	String worldName ="";
	static DAO_EG_Location dao = null;
	public EG_Loccation(int x, int y, int z, String worldName) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.worldName = worldName;
		if(dao==null)
			dao = new DAO_EG_Location();
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
