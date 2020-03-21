package command;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import BaseDeDonne.DAO_Game;
import Modele.Game;
import Utils.StringUtility;

public class cmdGameList implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		ArrayList<Game> list = new DAO_Game().findAll();
		if(sender instanceof Player) {
			Player p = (Player) sender;
			
			if(p.hasPermission("gameMaster")||p.isOp()) {
			
				p.sendMessage(ChatColor.YELLOW+"List des game possible : ");
				for(Game g : list) {
					p.sendMessage(ChatColor.DARK_GREEN+"- Nom : "+g.getName()+ChatColor.BLUE+" - Description : \" "+g.getDescription()+"\""+ChatColor.DARK_PURPLE+" - dure : "+StringUtility.sec2TimeUnite(g.getTime()));
				}
			}
		}else {
			System.out.println(ChatColor.YELLOW+"List des game possible : ");
			for(Game g : list) {
				System.out.println(ChatColor.DARK_GREEN+"- Nom : "+g.getName()+ChatColor.BLUE+" - Description : \" "+g.getDescription()+"\""+ChatColor.DARK_PURPLE+" - dure : "+StringUtility.sec2TimeUnite(g.getTime()));
			}
		}
		// TODO Auto-generated method stub
		return false;
	}

}
