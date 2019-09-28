package test;

import BaseDeDonne.DAO;
import BaseDeDonne.DAO_ini;
import BaseDeDonne.FillDataBase;
import Modele.EG_Loccation;

public class Main {
	
	
	static boolean res ;
	static String path = "C:\\Users\\xam74\\Desktop\\Serveur\\test.db";
	public static DAO_ini dao;
	public static void main(String[] args) {
		
		setupDataBase();
		
		/*
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
		
		loc.saveDB();
		
		EG_Loccation test = EG_Loccation.load(1);
		
		System.out.println(test.toString());
		*/
	}
	
	
	
	  public static void setupDataBase() {

			
			 dao = new DAO_ini();
			
			
			res = dao.createDB(path);
			
			if(!res) {
				dao.connect(path);
			}
			
			dao.ini(dao.getConnextion());
			
			FillDataBase.fill();
			FillDataBase.fill();
	    }

}
