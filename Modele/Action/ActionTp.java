package Modele.Action;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import Modele.GamePlayer;
import Utils.EG_Exception;
import Utils.Log;

public class ActionTp extends ActionInGame{
	
	int x , y ,z ;
	String worldName;
	
	public ActionTp(Location loc) {
		// TODO Auto-generated constructor stub
		worldName = loc.getWorld().getName();
		x = loc.getBlockX();
		y = loc.getBlockZ();
		z = loc.getBlockZ();
	}
	
	public ActionTp(String str) throws EG_Exception {
		// TODO Auto-generated constructor stub
		fromString(str);
		this.str = str;
	}





	@Override
	public void fromString(String str) throws EG_Exception {
		// TODO Auto-generated method stub
		if(str.indexOf("tp")!=-1) {
			
			String[] tab = str.split(" ");
			
			if(tab.length==5) {
				x = Integer.parseInt(tab[1]);
				y = Integer.parseInt(tab[2]);
				z = Integer.parseInt(tab[3]);
				worldName = tab[4];
			}else {
				throw new EG_Exception("Le message ne contien pas les bonne coordone syntaxe corect : tp x y z worldName");
			}
			
		}else {
			throw new EG_Exception("Ce nest pas un tp syntaxe corect : tp x y z worldName");
		}
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "tp "+x+" "+y+" "+z+" "+worldName;
	}





	@Override
	public void applyAction(Collection<GamePlayer> list) throws EG_Exception{
		// TODO Auto-generated method stub
		
		World w = Bukkit.getWorld(worldName.trim());
	
		Location loc = new Location(w, x, y, z);
		
		for(GamePlayer gp : list) {
			gp.getP().teleport(loc);
		}
		
	}

}
