package Modele;

import org.bukkit.block.Block;

public interface ClefInterface {
	
	String keyName=null;
	boolean oppen=false;
	Block b = null;
	
	public String getKeyName() ;
	public void setKeyName(String keyName) ;
	public boolean isOppen();
	public void setOppen(boolean oppen) ;
	public Block getB() ;
	public void setB(Block b) ;
	public boolean isCorectKey(String str);

}
