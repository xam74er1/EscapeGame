package Modele;

import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;

import BaseDeDonne.DAO_BlockInventory;
import BaseDeDonne.DAO_BlockInventoryKey;
import Utils.EG_Exception;

public class BlockInventoryKey extends BlockInventory implements ClefInterface{
	String keyName;
	boolean oppen = false;
	DAO_BlockInventoryKey dao = new DAO_BlockInventoryKey();

	public BlockInventoryKey(Block b) throws EG_Exception {
		super(b);
		// TODO Auto-generated constructor stub
		this.type = 2;
	}
	
	public BlockInventoryKey(BlockInventory binv) throws EG_Exception {
		// TODO Auto-generated constructor stub
		super(binv.getId(),binv.getInv(),2,binv.getLocation().getId());
		BlockInventoryKey bik = dao.find(binv);

			this.keyName = bik.keyName;
			this.oppen = bik.isOppen();
		
	}
	

	public BlockInventoryKey(Block b , String key,boolean isOppen) throws Exception {
		super(2,b);
		this.keyName = key;
		this.oppen = isOppen;
		//Si il existe
		BlockInventoryKey bik = dao.find(this);
		if(bik!=null) {
			dao.update(this);
		}else {
			dao.insert(this);
		}
		
	}


	public BlockInventoryKey(BlockInventory binv , String key,boolean isOppen) throws EG_Exception {
		//int id, Inventory inv,int type, int loc_id
		super(binv.getId(),binv.getInv(),2,binv.getLocation().getId());

		this.keyName = key;
		this.oppen = isOppen;

		this.type = 2;

	}

	@Override
	public String getKeyName() {
		// TODO Auto-generated method stub
		return this.keyName;
	}

	@Override
	public void setKeyName(String keyName) {
		this.keyName = keyName;
		if(exist()) {
			dao.update(this);
		}

	}

	@Override
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

	@Override
	public Block getB() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setB(Block b) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isCorectKey(String str) {
		// TODO Auto-generated method stub
		return this.keyName.equalsIgnoreCase(str);
	}
	
	public void create() throws Exception {
		dao.insert(this);
	}

}
