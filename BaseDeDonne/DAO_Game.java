package BaseDeDonne;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Modele.Game;
import Modele.PorteAClef;
import Utils.EG_Exception;
import Utils.Log;

public class DAO_Game extends DAO<Game> {

	public static String requetteCreate = "CREATE TABLE IF NOT EXISTS game ("
			+"	id integer PRIMARY KEY,"
			+"	name TEXT UNIQUE,"
			+ "	time integer ,"
			+ "	description TEXT"
			+ "	,start TEXT"
			+ "	,selected INTEGER"
			+ ");";

	String tableName="game";
	@Override
	public int insert(Game obj) throws Exception {

		int max = getMaxID(tableName);
		// TODO Auto-generated method stub
		int selected = 0;
		if(obj.isSelected()) {
			selected = 1;
		}

		int tmp =  exeUpdate("INSERT INTO "+tableName+" VALUES ("+(max+1)+",\""+obj.getName()+"\","+obj.getTime()+",\""+obj.getDescription()+"\",\""+obj.actionToString()+"\","+selected+")");



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
		int selected = 0;
		if(obj.isSelected()) {
			selected = 1;
			try {
				//On deselectione toute les game 
				exeUpdate("UPDATE "+tableName+" SET selected="+0+ " WHERE selected= "+1+"");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			int nb = exeUpdate("UPDATE "+tableName+" SET name= \""+obj.getName()+"\" , time = "+obj.getTime()+", description=\""+
					obj.getDescription()+"\",start=\""+obj.actionToString()+"\",selected="+selected+ " WHERE id = "+obj.getId()+"");

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


		try {
			ResultSet rs = querry("Select * from "+tableName+" WHERE id = "+id );

			if(rs.isClosed())
				return null;

			Game game;
			game = new Game(rs.getInt("id"),rs.getString("name"),rs.getInt("time"),rs.getString("description"),rs.getString("start"),rs.getInt("selected"));
			rs.close();
			return game;



		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return null;


	}




	public Game findSelectGame() {
		// TODO Auto-generated method stub


		try {
			ResultSet rs = querry("Select * from "+tableName+" WHERE selected = "+1 );

			if(rs.isClosed())
				return null;

			Game game;
			game = new Game(rs.getInt("id"),rs.getString("name"),rs.getInt("time"),rs.getString("description"),rs.getString("start"),rs.getInt("selected"));
			rs.close();
			return game;



		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return null;


	}

	public ArrayList<Game> findAll(){
		ArrayList<Game> list = new ArrayList<Game>();


		ResultSet rs;
		try {
			rs = querry("Select * from "+tableName+"");
			if(rs.isClosed())
				return null;

			while(rs.next()) {
				Game game;
				game = new Game(rs.getInt("id"),rs.getString("name"),rs.getInt("time"),rs.getString("description"),rs.getString("start"),rs.getInt("selected"));
				list.add(game);

			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		return list;
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
			game = new Game(rs.getInt("id"),rs.getString("name"),rs.getInt("time"),rs.getString("description"),rs.getString("start"),rs.getInt("selected"));
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

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}



}
