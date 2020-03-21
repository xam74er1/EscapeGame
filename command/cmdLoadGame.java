package command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import BaseDeDonne.DAO_Game;
import Modele.Game;
import Utils.Log;
import Utils.StringUtility;
import fr.xam74er1.spigot.Main;

public class cmdLoadGame implements CommandExecutor {
	Player p = null;
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(sender instanceof Player) {
			 p = (Player) sender;
			
			if(p.hasPermission("gameMaster")||p.isOp()) {
				if(args.length > 0 ) {
					load(args[0]);
				}
			}
		}else {
			if(args.length > 0 ) {
				load(args[0]);
			}
		}
		// TODO Auto-generated method stub
		return false;
	}

	
	public void load(String name) {
		Game game = new DAO_Game().find(name);
		
		if(game!=null) {
		  Main.setGame(game);
		  Log.displaySucesse(p, "Une nouvelle game a bien ete charge : ");
		  Game g = Main.getGame();
		  p.sendMessage(ChatColor.DARK_GREEN+"- Nom : "+g.getName()+ChatColor.BLUE+" - Description : \" "+g.getDescription()+"\""+ChatColor.DARK_PURPLE+" - dure : "+StringUtility.sec2TimeUnite(g.getTime()));
		}else {
			help();
		}
	}
	
	public void help() {
	
		if(p!=null) {
			Log.displayError(p, "/loadgame <nom> ");
			Log.displayError(p, "Aucune game avec ce nom existe ");
			Log.displayWarning(p, "Faite /gamelist pour avoir le nom de tout les game disponible  ");
		}else {
			System.out.println("Aucune game avec ce nom existe ");
			System.out.println("Faite /gamelist pour avoir le nom de tout les game disponible  ");
			
		}
	}
	
}
