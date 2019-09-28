package Modele.Action;

import java.util.Collection;

import Modele.GamePlayer;
import Utils.EG_Exception;

public class ActionMessage extends ActionInGame{
	
	String message;
	
	public ActionMessage(String str) throws EG_Exception {
		// TODO Auto-generated constructor stub
		fromString(str);
	}

	@Override
	public void applyAction(Collection<GamePlayer> list) throws EG_Exception {
		// TODO Auto-generated method stub
		
		for(GamePlayer gp : list) {
			gp.getP().sendMessage(message);
		}
		
	}

	@Override
	public void fromString(String str) throws EG_Exception {
		// TODO Auto-generated method stub
		if(str.indexOf("msg")!=-1) {
		message = str.substring(3);
		
		message = message.replaceAll("£", "§");
		
		}else {
			throw new EG_Exception("Ce nest pas un tp syntaxe corect : msg <message>");
		}
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "msg "+message;
	}

}
