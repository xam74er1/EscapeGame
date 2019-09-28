package Modele;

public abstract class Ellement<T> {
	int id;
	
	public void saveDB() {
	}

	public void upDate() {
		
	};
	
	public static Object load(int id) {
		return null;
	}
	
	public boolean exist() {
		return id!=-1;
	}

}
