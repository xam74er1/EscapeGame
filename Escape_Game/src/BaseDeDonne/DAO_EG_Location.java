package BaseDeDonne;

import java.sql.SQLException;

import Modele.EG_Loccation;

public class DAO_EG_Location extends DAO<EG_Loccation>{
	
	public static String requetteCreate = "CREATE TABLE IF NOT EXISTS Location (\n"
            + "	id integer PRIMARY KEY,\n"
            + "	Worldname text NOT NULL,\n"
            + "	x integer\n"
            + "	y integer\n"
            + "	z integer\n"
            + ");";

	@Override
	public boolean create(EG_Loccation obj) throws Exception {
		// TODO Auto-generated method stub
		int max = getMaxID("Location");
		return false;
	}

	@Override
	public boolean delete(EG_Loccation obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(EG_Loccation obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public EG_Loccation find(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
