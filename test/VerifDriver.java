package test;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VerifDriver {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("verifDriver");
		 Connexion connexion = new Connexion("C:\\Users\\xam74\\Documents\\test.db");
	        connexion.connect();
	        
	        ResultSet resultSet = connexion.query("SELECT * FROM test");
	        try {
	            while (resultSet.next()) {
	                System.out.println("Nom : "+resultSet.getString("nom"));
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        
	        connexion.close();
	}

}
