package command;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import BaseDeDonne.DAO_EG_Location;
import Modele.BlockInventoryKey;
import Modele.EG_Loccation;
import Modele.GamePlayer;
import Utils.Log;
import fr.xam74er1.spigot.Main;
import net.md_5.bungee.api.ChatColor;

public class cmdDeleteBlock implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		return action(sender,cmd,label,args);
	}

	public static boolean action(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;

			if(p.hasPermission("gameMaster")||p.isOp()) {
				
				GamePlayer gp = Main.getGame().getPlayer(p.getName());
			
				
				if(gp.getSelectBlock()!=null) {
					Block b = gp.getSelectBlock();
				
				EG_Loccation loc = new DAO_EG_Location().find(b.getLocation());
				
				if(loc!=null) {
					boolean res = new DAO_EG_Location().delete(loc);
					if(res) {
						Log.displaySucesse(p, "Le block a bien ete enleve");
					}
				}else {
					Log.displayError(p, "Aucun block pour les escape game n'existe as cette endrois la ");
				}
					
				}else {
					p.sendMessage(ChatColor.RED+"Aucun block selectione");
				}

			}
		}
		return true;
	}

}
