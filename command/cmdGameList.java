package command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class cmdGameList implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(sender instanceof Player) {
			Player p = (Player) sender;
			
			if(p.hasPermission("gameMaster")||p.isOp()) {
				
			}
		}
		// TODO Auto-generated method stub
		return false;
	}

}
