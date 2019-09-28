package BaseDeDonne;

import java.sql.ResultSet;
import java.sql.SQLException;

import Modele.Game;
import Modele.PorteAClef;
import Utils.EG_Exception;
import Utils.Log;

public class DAO_Game extends DAO<Game> {
	
	public static String requetteCreate = "CREATE TABLE IF NOT EXISTS game ("
            +"	id integer PRIMARY KEY,"
			+"	name TEXT ,"
            + "	time integer UNIQUE,"
            + "	description TEXT"
            + "	,start TEXT"
            + ");";

	String tableName="game";
	@Override
	public int insert(Game obj) throws Exception {
		
		int max = getMaxID(tableName);
		// TODO Auto-generated method stub
	//	Log.print("dao game is close :"+getConnextion().isClosed());
		int tmp =  exeUpdate("INSERT INTO "+tableName+" VALUES ("+(max+1)+",\""+obj.getName()+"\","+obj.getTime()+",\""+obj.getDescription()+"\",\""+obj.actionToString()+"\")");
		
		Log.print("dao game is close :"+getConnextion().isClosed());
		
		return tmp;	
	}

	@Override
	public boolean delete(Game obj) {
		// TODO Auto-generated method stub
		

		// TODO Auto-generated method stub
		
		
		
	
		return false;
	}

	@Override
	public int update(Game obj) {
		// TODO Auto-generated method stub
		
		try {
			int nb = exeUpdate("UPDATE "+tableName+" SET name= \""+obj.getName()+"\" , time = "+obj.getTime()+", description=\""+
		obj.getDescription()+"\",start=\""+obj.actionToString()+"\""+ " WHERE id = "+obj.getId()+"");
			
			Log.print(nb+"Ligne mise a jour");
			
			return nb;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	
	}

	@Override
	public Game find(int id) {
		// TODO Auto-generated method stub
		

		// TODO Auto-generated method stub
		

		
			try {
				ResultSet rs = querry("Select * from "+tableName+" WHERE id = "+id );
				
				if(rs.isClosed())
					return null;
				
			Game game;
				game = new Game(rs.getInt("id"),rs.getString("name"),rs.getInt("time"),rs.getString("description"),rs.getString("start"));
				rs.close();
				return game;
			
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		
		return null;
	
		
	}
	
	public Game find(String name) {
		// TODO Auto-generated method stub
		

		// TODO Auto-generated method stub
		

		ResultSet rs = null;
			try {
				 rs = querry("Select * from "+tableName+" WHERE name like \""+name+"\"" );
				
				if(rs.isClosed())
					return null;
				
			Game game;
				game = new Game(rs.getInt("id"),rs.getString("name"),rs.getInt("time"),rs.getString("description"),rs.getString("start"));
				rs.close();
				return game;
			
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				try {
					rs.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}

		
		return null;
	
		
	}

}
