package BaseDeDonne;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.inventory.Inventory;

import Modele.BlockInventory;
import Modele.BlockInventoryCode;
import Utils.EG_Exception;
import Utils.Log;

public class DAO_BlockInventoryCode extends  DAO_BlockInventory{
	
	public static String requetteCreate = "CREATE TABLE IF NOT EXISTS BlockInventoryCode ("
			+ "	id integer PRIMARY KEY,"
			+" code_juste TEXT,"
			+" code_actuelle TEXT,"
			+"isOppen boolean,"
			+"FOREIGN KEY (id) REFERENCES blockInventory(id)"
			+ ");";


	String tableName = "BlockInventoryCode";



	@Override
	public int insert(BlockInventory obj) throws Exception {
		// TODO Auto-generated method stub
		//int id = getMaxID(super.tableName)+1;
	//	super.insert(obj);
		
		BlockInventoryCode bic = (BlockInventoryCode) obj;
		
		return exeUpdate("INSERT INTO "+tableName+" VALUES ("+obj.getId()+",\""+bic.getCode_juste()+"\",\""+bic.getCode_Actuelle()+"\","+bic.isOppen()+");"
				);
	}

	@Override
	public boolean delete(BlockInventory obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int update(BlockInventory obj) {
		// TODO Auto-generated method stub
		try {
			BlockInventoryCode bic = (BlockInventoryCode) obj;

	

		
			int nb = exeUpdate("UPDATE "+tableName+" SET code_juste= \""+bic.getCode_juste()+"\",code_actuelle=\""+bic.getCode_Actuelle()+"\",isOppen = "+bic.isOppen()+""
					+ " WHERE id = "+obj.getId());

			Log.print(nb+"Ligne mise a jour");

			return nb;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	
	}

	
	public BlockInventoryCode find(int id) {
		// TODO Auto-generated method stub
		BlockInventory biv = super.find(id);
		if(biv==null) return null;
		try {
			ResultSet rs = querry("Select * from "+tableName+" WHERE id = "+biv.getId() );
			
			if(rs.isClosed())
				return null;

			try {
				
				BlockInventoryCode bic = new BlockInventoryCode(biv,rs.getString("code_actuelle"),rs.getString("code_juste"),rs.getBoolean("isOppen"));
				rs.close();
				return bic;
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
	
	public BlockInventoryCode find(BlockInventory biv) {
	
		if(biv==null) 
			return null;
		
		try {
			ResultSet rs = querry("Select * from "+tableName+" WHERE id = "+biv.getId() );
			
		
			if(rs.isClosed())
				return null;

			try {
				
				BlockInventoryCode bic = new BlockInventoryCode(biv,rs.getString("code_actuelle"),rs.getString("code_juste"),rs.getBoolean("isOppen"));
				rs.close();
				return bic;
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
