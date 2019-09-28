package command;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import Utils.GameConstante;




public class giveMasterWhant implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(sender instanceof Player) {
			Player p = (Player) sender;
			
			if(p.hasPermission("gameMaster")||p.isOp()) {
				p.getInventory().addItem(GameConstante.masterWhant());
			}
			
		}
		// TODO Auto-generated method stub
		return false;
	}

}
