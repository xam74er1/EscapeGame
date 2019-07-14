package Modele;

public class PorteACode extends Ellement{

	String code_juste="";
	String code_Actuelle ="";
	boolean isOppen;


	@Override
	public void load(EG_Loccation loc) {
		// TODO Auto-generated method stub
		super.load(loc);
	}


	public String getCode_juste() {
		return code_juste;
	}


	public void setCode_juste(String code_juste) {
		this.code_juste = code_juste;
	}


	public String getCode_Actuelle() {
		return code_Actuelle;
	}


	public void setCode_Actuelle(String code_Actuelle) {
		this.code_Actuelle = code_Actuelle;
	}


	public boolean isOppen() {
		return isOppen;
	}


	public void setOppen(boolean isOppen) {
		this.isOppen = isOppen;
	}
	
	
}
