package BaseDeDonne;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.bukkit.Location;

import Modele.EG_Loccation;
import Utils.Log;

public class DAO_EG_Location extends DAO<EG_Loccation>{

	public static String requetteCreate = "CREATE TABLE IF NOT EXISTS Location ("
			+ "	id integer ,"
			+ "	Worldname text NOT NULL,"
			+ "	x integer,"
			+ "	y integer,"
			+ "	z integer,"
			+"PRIMARY KEY (Worldname,x,y,z)"
			+ ");";

	String tableName = "Location";
	@Override
	public int insert(EG_Loccation obj) throws Exception {
		// TODO Auto-generated method stub
		int max = getMaxID("Location");


		return exeUpdate("INSERT INTO Location VALUES ("+(max+1)+",\""+obj.getWorldName()+"\","+obj.getX()+","+obj.getY()+","+obj.getZ()+")"+
				";");


	}

	@Override
	public boolean delete(EG_Loccation obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int update(EG_Loccation obj) {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public EG_Loccation find(int id) {
		// TODO Auto-generated method stub
		try {
			ResultSet rs = querry("Select * from "+tableName+" WHERE id = "+id );

			EG_Loccation eg = new EG_Loccation(rs.getInt("x"), rs.getInt("y"),rs.getInt("z"), rs.getString("worldname"),rs.getInt("id"));

			rs.close();
			return eg;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public EG_Loccation find(int x,int y,int z,String WorldName) {

		try {
			ResultSet rs = querry("Select * from "+tableName+" WHERE x = "+x+" and y ="+y+" and z ="+z+" and WorldName like \""+WorldName+"\" LIMIT 1" );

			if(rs.isClosed()) {
				return null;
			}else {

				EG_Loccation eg = new EG_Loccation(rs.getInt("x"), rs.getInt("y"),rs.getInt("z"), rs.getString("worldname"),rs.getInt("id"));

				rs.close();
				return eg;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public EG_Loccation find(Location l) {
		return find(l.getBlockX(),l.getBlockY(),l.getBlockZ(),l.getWorld().getName());
	}



}
