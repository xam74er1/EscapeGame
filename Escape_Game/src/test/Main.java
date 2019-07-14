package test;

import BaseDeDonne.DAO;
import BaseDeDonne.DAO_ini;
import Modele.EG_Loccation;

public class Main {
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		boolean res ;
		String path = "test.db";
		
		DAO_ini dao = new DAO_ini();
		
		
		res = dao.createDB(path);
		
		if(!res) {
			dao.connect(path);
		}
		
		dao.ini(dao.getConnextion());
		
		EG_Loccation loc = new EG_Loccation(1, 2, 3, "test");
	}

}
