package Modele.Action;

import java.util.Collection;

import Modele.GamePlayer;
import Utils.EG_Exception;

public abstract class ActionInGame {

	String str;

	public abstract void applyAction(Collection<GamePlayer> list) throws EG_Exception;
	public abstract void fromString(String str) throws EG_Exception;
	public abstract String toString();
	public String getStr() {
		return str;
	}


	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub

		if(obj instanceof ActionInGame) {

			return toString().equalsIgnoreCase(((ActionInGame) obj).toString());

		}else {
			return false;
		}

	}

	public static ActionInGame createAction(String str) throws EG_Exception {

		if(str.length()>3) {
			String debut = str.subSequence(0, 3).toString();
			if(debut.indexOf("tp")!=-1) {
				return new ActionTp(str);
			}else if(debut.indexOf("msg")!=-1) {
				return new ActionMessage(str);
			}else {
				return null;
			}
		}
		return null;
	}


}
