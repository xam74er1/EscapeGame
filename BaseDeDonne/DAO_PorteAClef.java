package BaseDeDonne;

import java.sql.ResultSet;
import java.sql.SQLException;

import Modele.PorteAClef;
import Modele.PorteACode;
import Utils.EG_Exception;
import Utils.Log;

public class DAO_PorteAClef extends DAO<PorteAClef>{
	
	public static String requetteCreate = "CREATE TABLE IF NOT EXISTS porteaclef ("
            + "	id integer PRIMARY KEY,"
            + "	locID integer UNIQUE,"
            + "	key_name TEXT,"
            +"	isOppen boolean,"
            +"FOREIGN KEY (locID) REFERENCES Location(id)"
            + ");";

	String tableName = "porteaclef";

	@Override
	public int insert(PorteAClef obj) throws Exception {
		int max = getMaxID(tableName);


		return exeUpdate("INSERT INTO "+tableName+" VALUES ("+(max+1)+","+obj.getLococation().getId()+",\""+obj.getKeyName()+"\","+obj.isOppen()+");"
				);
	}

	@Override
	public boolean delete(PorteAClef obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int update(PorteAClef obj) {
		// TODO Auto-generated method stub
		
		
		try {
			int nb = exeUpdate("UPDATE "+tableName+" SET key_name= \""+obj.getKeyName()+"\" , isOppen = "+obj.isOppen()+""
					+ " WHERE id = "+obj.getId());
			
			Log.print(nb+"Ligne mise a jour");
			
			return nb;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public PorteAClef find(int id) {
		// TODO Auto-generated method stub
		

		
			try {
				ResultSet rs = querry("Select * from "+tableName+" WHERE locID = "+id );
				
				if(rs.isClosed())
					return null;
				
				PorteAClef pac;
				try {
					pac = new PorteAClef(rs.getInt("id"),rs.getString("key_name"),rs.getBoolean("isOppen"),rs.getInt("locID"));
					rs.close();
					return pac;
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

	public static String getRequetteCreate() {
		return requetteCreate;
	}

	public static void setRequetteCreate(String requetteCreate) {
		DAO_PorteACode.requetteCreate = requetteCreate;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

}
