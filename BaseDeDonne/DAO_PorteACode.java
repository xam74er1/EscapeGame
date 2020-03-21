package BaseDeDonne;

import java.sql.ResultSet;
import java.sql.SQLException;

import Modele.EG_Loccation;
import Modele.PorteACode;
import Utils.EG_Exception;
import Utils.Log;

public class DAO_PorteACode  extends DAO<PorteACode>{
	
	public static String requetteCreate = "CREATE TABLE IF NOT EXISTS porteacode ("
            + "	id integer PRIMARY KEY,"
            + "	locID integer UNIQUE,"
            + "	code_juste TEXT,"
            + "	code_actuele TEXT,"
            +"	isOppen boolean,"
            +"FOREIGN KEY (locID) REFERENCES Location(id)"
            + ");";

	String tableName = "porteacode";

	@Override
	public int insert(PorteACode obj) throws Exception {
		int max = getMaxID(tableName);


		return exeUpdate("INSERT INTO "+tableName+" VALUES ("+(max+1)+","+obj.getLococation().getId()+","+obj.getCode_juste()+","+obj.getCode_Actuelle()+","+obj.isOppen()+");"
				);
	}

	@Override
	public boolean delete(PorteACode obj) {

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
	
	public boolean delete(EG_Loccation obj) {
		int rs;
		try {
			rs = exeUpdate("DELETE FROM "+tableName+" WHERE LocID = "+obj.getId() );
			if(rs==0) {
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
	public int update(PorteACode obj) {
		// TODO Auto-generated method stub
		
		
		try {
			int nb = exeUpdate("UPDATE "+tableName+" SET code_juste = \""+obj.getCode_juste()+"\" , code_actuele = \""+obj.getCode_Actuelle()+"\" , isOppen = "+obj.isOppen()+""
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
	public PorteACode find(int id) {
		// TODO Auto-generated method stub
		

		
			try {
				ResultSet rs = querry("Select * from "+tableName+" WHERE locID = "+id );
				
				if(rs.isClosed())
					return null;
				
				PorteACode pac = new PorteACode(rs.getInt("id"),rs.getString("code_juste"),rs.getString("code_actuele"),rs.getBoolean("isOppen"),rs.getInt("locID"));
				rs.close();
				
				return pac;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (EG_Exception e) {
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
