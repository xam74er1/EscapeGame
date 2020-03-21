package BaseDeDonne;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.inventory.Inventory;

import Modele.BlockInventory;
import Modele.BlockInventoryKey;
import Utils.EG_Exception;
import Utils.Log;

public class DAO_BlockInventoryKey extends  DAO_BlockInventory{
	
	public static String requetteCreate = "CREATE TABLE IF NOT EXISTS BlockInventoryKey ("
			+ "	id integer PRIMARY KEY,"
			+" key TEXT,"
			+"isOppen boolean,"
			+"FOREIGN KEY (id) REFERENCES blockInventory(id)"
			+ ");";


	String tableName = "BlockInventoryKey";



	@Override
	public int insert(BlockInventory obj) throws Exception {
		// TODO Auto-generated method stub
		//int id = getMaxID(super.tableName)+1;
	//	super.insert(obj);
		
		BlockInventoryKey bik = (BlockInventoryKey) obj;
		
		return exeUpdate("INSERT INTO "+tableName+" VALUES ("+obj.getId()+",\""+bik.getKeyName()+"\","+bik.isOppen()+");"
				);
	}

	@Override
	public boolean delete(BlockInventory obj) {	

		int  rs;
		try {
			rs = exeUpdate("DELETE FROM "+tableName+" WHERE id = "+obj.getId() );
			if(rs ==0) {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	@Override
	public int update(BlockInventory obj) {
		// TODO Auto-generated method stub
		try {
			BlockInventoryKey bik = (BlockInventoryKey) obj;

	

		
			int nb = exeUpdate("UPDATE "+tableName+" SET key= \""+bik.getKeyName()+"\",isOppen = "+bik.isOppen()+""
					+ " WHERE id = "+obj.getId());

			Log.print(nb+"Ligne mise a jour");

			return nb;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	
	}

	
	public BlockInventoryKey find(int id) {
		// TODO Auto-generated method stub
		BlockInventory biv = super.find(id);
		if(biv==null) return null;
		try {
			ResultSet rs = querry("Select * from "+tableName+" WHERE id = "+biv.getId() );
			
			if(rs.isClosed())
				return null;

			try {
				
				BlockInventoryKey bik = new BlockInventoryKey(biv,rs.getString("key"),rs.getBoolean("isOppen"));
				rs.close();
				return bik;
			} catch (EG_Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public BlockInventoryKey find(BlockInventory biv) {
	
		if(biv==null) 
			return null;
		
		try {
			ResultSet rs = querry("Select * from "+tableName+" WHERE id = "+biv.getId() );
			
		
			if(rs.isClosed())
				return null;

			try {
				
				BlockInventoryKey bik = new BlockInventoryKey(biv,rs.getString("key"),rs.getBoolean("isOppen"));
				rs.close();
				return bik;
			} catch (EG_Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
