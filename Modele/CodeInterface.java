package Modele;

import org.bukkit.block.Block;

import Utils.EG_Exception;

public interface CodeInterface {
	
	String code_juste="";
	String code_Actuelle ="";
	
	public String getCode_Actuelle() ;
	public void setCode_Actuelle(String code) ;
	
	public void setCode_juste(String code_juste) throws EG_Exception;
	public String getCode_juste();
	
	public void setB(Block b);
	public Block getB();
	
	public EG_Loccation getLoc();
	public void setLoc(EG_Loccation loc);
	
	public void setOppen(boolean isOppen);
	public void getOppen();
	
	public boolean code_corect();
	

}
