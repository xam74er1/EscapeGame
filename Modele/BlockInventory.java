package Modele;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import BaseDeDonne.DAO_BlockInventory;
import BaseDeDonne.DAO_EG_Location;
import BaseDeDonne.DAO_PorteAClef;
import Utils.EG_Exception;
import Utils.Log;

public class BlockInventory extends Ellement implements InventoryHolder{

	Inventory inv=null;
	EG_Loccation location=null;
	int id = -1;

	// 0 = normal ; 1 = code ; 2 = key
	int type = 0;

	static DAO_BlockInventory dao;
	static String invName ="Block inventaire";


	public BlockInventory(Block b) throws EG_Exception{
		// TODO Auto-generated constructor stub
		if(dao==null)
			dao = new DAO_BlockInventory();

		this.location = new EG_Loccation(b.getLocation());

		BlockInventory binv = dao.find(location.getId());

		if(binv!=null) {

			//On cree un nvll inventaire pour lui mettre le contenus 
			Inventory newInventory = Bukkit.createInventory(null, binv.getInv().getSize(), invName); //owner can be "null" or a player
			newInventory.setContents(binv.getInv().getContents());
			this.inv = newInventory;
			this.id = binv.getId();
			this.type = binv.getType();
		}

	}

	//Utilise par le DAO
	public BlockInventory( Inventory inv,int type, EG_Loccation loc) throws EG_Exception {
		super();

		if(dao==null)
			dao = new DAO_BlockInventory();
		this.inv = inv;
		this.location = loc;
		this.id = id;
		this.type=type;
	}
	//Utilise par le DAO
	public BlockInventory(int id, Inventory inv,int type, int loc_id) throws EG_Exception {
		super();

		if(dao==null)
			dao = new DAO_BlockInventory();
		this.inv = inv;
		this.location = new DAO_EG_Location().find(loc_id);
		this.id = id;
		this.type=type;
	}

	public BlockInventory(int type,Block b) {
		if(dao==null)
			dao = new DAO_BlockInventory();
		this.location = new EG_Loccation(b.getLocation());

		BlockInventory binv = 	dao.find(location.getId());

		if(binv!=null) {
			
			//On remplus l'enventaire avec l'ancien 
			Inventory newInventory = Bukkit.createInventory(null, binv.getInv().getSize(), invName); //owner can be "null" or a player
			newInventory.setContents(binv.getInv().getContents());
			this.inv = newInventory;
			this.id = binv.getId();
			this.type = type;
			dao.update(this);
		}else {
			try {
				this.inv = CreateInv();
				this.type=type;
				this.id = dao.getMaxID(dao.getTableName())+1;
				dao.insert(this);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public EG_Loccation getLocation() {
		return location;
	}

	public void setLocation(EG_Loccation location) {
		this.location = location;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Inventory getInv() {
		return inv;
	}

	public void setInv(Inventory inv) {



		this.inv = inv;
		if(this.id!=-1) {
			dao.update(this);
		}
	}

	public Inventory CreateInv() {
		Inventory inv = Bukkit.createInventory(this, 27,invName );

		return inv;	
	}

	@Override
	public Inventory getInventory() {
		// TODO Auto-generated method stub
		return inv;
	}

	public static String getInvName() {
		return invName;
	}

	public static void setInvName(String invName) {
		BlockInventory.invName = invName;
	}


	@Override
	public boolean exist() {
		// TODO Auto-generated method stub
		return id!=-1;
	}

	

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return (obj instanceof BlockInventory)&&this.id==((BlockInventory)obj).id;
	}
	
}
