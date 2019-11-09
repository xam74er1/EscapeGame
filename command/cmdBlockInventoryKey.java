package command;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import Modele.BlockInventoryKey;
import Modele.GamePlayer;
import Modele.PorteAClef;
import Utils.EG_Exception;
import fr.xam74er1.spigot.Main;
import net.md_5.bungee.api.ChatColor;

public class cmdBlockInventoryKey implements CommandExecutor {

	@Override
	public boolean  onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		return action(sender,cmd,label,args);
	}
	
	public static boolean action(CommandSender sender, Command cmd, String label, String[] args) {
			
			if(sender instanceof Player) {
				Player p = (Player) sender;
				
				if(p.hasPermission("gameMaster")||p.isOp()) {
						
					GamePlayer gp = Main.getGame().getPlayer(p.getName());
				
					
					if(gp.getSelectBlock()!=null) {
						Block b = gp.getSelectBlock();
					
							if(args.length>=1) {
								
								String keyName ="";
								
								for(String s : args) {
									keyName+=s+" ";
								}
								keyName = keyName.trim();
								
								try {
									new BlockInventoryKey(b, keyName, false);
									
									ItemStack it = new ItemStack(Material.TRIPWIRE_HOOK,1);
									ItemMeta itm = it.getItemMeta();
									
									itm.setDisplayName(keyName);
									it.setItemMeta(itm);
									
									p.getInventory().addItem(it);
									
									p.sendMessage(ChatColor.GREEN+"Block inventaire cree avec sucee : La clef vous a ete donne dans votre inventaire");
									
								} catch ( Exception e) {
									// TODO Auto-generated catch block
									p.sendMessage(ChatColor.RED+e.getMessage());
								
									e.printStackTrace();
									
								}
								
							}else {
								p.sendMessage(ChatColor.RED+"/setBlockInventoryKey keyName");
							}
							
					
						
					}else {
						p.sendMessage(ChatColor.RED+"Aucun block selectione");
					}
				}
				
			}
			return false;
			
		 
		
	}

}
