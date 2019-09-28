package BaseDeDonne;

import Modele.EG_Loccation;

public class FillDataBase {

	public static void fill() {
		EG_Loccation loc = new EG_Loccation(1, 2, 3, "test");

		loc.saveDB();
		
		loc.getDao().resetConnextion();
		
	}

}
