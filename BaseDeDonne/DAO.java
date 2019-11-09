package BaseDeDonne;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import Modele.EG_Loccation;
import Utils.Log;

public abstract class DAO<T> {
	static String path;
	static Connection connextion ;


	public DAO() {

	}

	public void connect(String path) {



		try {
			Connection connection = DriverManager.getConnection(path);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public boolean createDB(String path) {
		boolean crate = false;
		File f = new File(path);
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:"+path);
			if (conn != null) {
				//				DatabaseMetaData meta = conn.getMetaData();
				//				Log.print("The driver name is " + meta.getDriverName());
				Log.print("A new database has been created.");
				this.path = path;

				if(!f.exists()) {
					f.createNewFile();
					System.out.println("Fichier cree");
				}



				crate = true;



			}

			this.connextion = conn;

			//Creation de toute les table 

			//exeRequette(DAO_EG_Location.requetteCreate);

		} catch (SQLException | ClassNotFoundException | IOException e) {

			System.err.println(e);
		}

		return crate;
	}

	public void ini(Connection conn) {
		try {
			Statement stmt = conn.createStatement();


			stmt.execute(DAO_EG_Location.requetteCreate);
			stmt.execute(DAO_PorteACode.requetteCreate);
			stmt.execute(DAO_PorteAClef.requetteCreate);
			stmt.execute(DAO_BlockInventory.requetteCreate);
			stmt.execute(DAO_Game.requetteCreate);
			stmt.execute(DAO_BlockInventoryKey.requetteCreate);
			stmt.execute(DAO_BlockInventoryCode.requetteCreate);
			stmt.close();
			Log.print("All database has been inisilized");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void ini(Connection conn,String [] tab) {
		try {
			Statement stmt = conn.createStatement();

			for(String s : tab) {
				stmt.execute(s);
			}

			stmt.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void exeRequette(Connection conn,String requette) {
		try {
			Statement stmt = conn.createStatement();


			stmt.execute(requette);


			stmt.close();
		} catch (SQLException e) {


			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	public void resetConnextion() {
		try {



			if(!connextion.isClosed()) {
				connextion.close();
				connextion = null;
			}

			createDB(this.getPath());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean exeRequette(String requette) {
		Statement stmt = null;
		boolean tmp = false;
		try {
			Log.print(requette);
			Connection conn = this.getConnextion();



			stmt = conn.createStatement();

			try {
				tmp = stmt.execute(requette);
			}catch(SQLException e3) {

				if(e3.getMessage().contains("SQLITE_CONSTRAINT")) {
					Log.print(e3.getMessage());


				}
			}

			stmt.close();

			Log.print("stmt = "+stmt.isClosed());

			return tmp;

		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block

			if(e.getMessage().contains("SQLITE_CONSTRAINT")) {
				Log.print(e.getMessage());
				try {
					if(stmt!=null) {
						Log.print("Froce close");
						stmt.close();
						return false;
					}

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					Log.print("Un putin de pbr est survenue");
					e1.printStackTrace();
				}
			}else {
				e.printStackTrace();
			}
		}
		return false;

	}

	public ResultSet querry(String sql) throws SQLException {
		Statement stmt  = getConnextion().createStatement();
		Log.print(sql);
		ResultSet rs;
		stmt.close();
		rs = stmt.executeQuery(sql);

		return rs;
	}

	public int exeUpdate(String sql) throws SQLException {
		Statement stmt  = getConnextion().createStatement();

		Log.print(sql);

		int nb = stmt.executeUpdate(sql);

		stmt.close();

		return nb;
	}

	public int getMaxID(String tableName) throws SQLException, Exception {
		return getMax(tableName,"id");
	}

	public int getMax(String tableName,String arg) throws SQLException,Exception {
		Statement stmt  = getConnextion().createStatement();


		String sql = " Select max("+arg+") FROM "+tableName;
		ResultSet rs;
		Log.print(sql);
		rs = stmt.executeQuery(sql);

		int res =rs.getInt("max("+arg+")");

		stmt.close();
		return res;
	}

	public abstract int insert(T obj) throws Exception;

	/**
	 * Méthode pour effacer
	 * @param obj
	 * @return boolean 
	 */
	public abstract boolean delete(T obj);

	/**
	 * Méthode de mise à jour
	 * @param obj
	 * @return boolean
	 */
	public abstract int update(T obj);

	/**
	 * Méthode de recherche des informations
	 * @param id
	 * @return T
	 */
	public abstract T find(int id);

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Connection getConnextion() {
		return connextion;
	}

	public void setConnextion(Connection connextion) {
		this.connextion = connextion;
	}

}


