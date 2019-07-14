package BaseDeDonne;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
		try (Connection conn = DriverManager.getConnection(path)) {
			if (conn != null) {
				DatabaseMetaData meta = conn.getMetaData();
				System.out.println("The driver name is " + meta.getDriverName());
				System.out.println("A new database has been created.");
				this.path = path;
				crate = true;

			}

			this.connextion = conn;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return crate;
	}
	
	public void ini(Connection conn) {
		try {
			Statement stmt = conn.createStatement();

		
				stmt.execute(DAO_EG_Location.requetteCreate);
			

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

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void exeRequette(Connection conn,String requette) {
		try {
			Statement stmt = conn.createStatement();

			
				stmt.execute(requette);
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public int getMaxID(String tableName) throws SQLException, Exception {
		return getMax(tableName,"id");
	}
	
	public int getMax(String tableName,String arg) throws SQLException,Exception {
        Statement stmt  = getConnextion().createStatement();
        String sql = " Select MAX("+arg+") FROM TABLE "+tableName;
        ResultSet rs;
		
			rs = stmt.executeQuery(sql);
		
        return rs.getInt(arg);
	}
	  
	  public abstract boolean create(T obj) throws Exception;

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
	  public abstract boolean update(T obj);

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


