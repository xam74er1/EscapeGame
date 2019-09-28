package command;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import Modele.GamePlayer;
import Modele.PorteAClef;
import Modele.PorteACode;
import Utils.EG_Exception;
import fr.xam74er1.spigot.Main;
import net.md_5.bungee.api.ChatColor;

public class setKeyDoor implements CommandExecutor {

	@Override
	public boolean  onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(sender instanceof Player) {
			Player p = (Player) sender;
			
			if(p.hasPermission("gameMaster")||p.isOp()) {
					
				GamePlayer gp = Main.getGame().getPlayer(p.getName());
			
				
				if(gp.getSelectBlock()!=null) {
					Block b = gp.getSelectBlock();
					if(b.getType()==Material.IRON_DOOR) {
						if(args.length>=1) {
							
							String keyName ="";
							
							for(String s : args) {
								keyName+=s+" ";
							}
							keyName = keyName.trim();
							
							try {
								new PorteAClef(keyName, b);
								
								ItemStack it = new ItemStack(Material.TRIPWIRE_HOOK,1);
								ItemMeta itm = it.getItemMeta();
								
								itm.setDisplayName(keyName);
								it.setItemMeta(itm);
								
								p.getInventory().addItem(it);
								
								p.sendMessage(ChatColor.GREEN+"Porte a clef cree avec sucee : La clef vous a ete donne dans votre inventaire");
								
							} catch (EG_Exception e) {
								// TODO Auto-generated catch block
								p.sendMessage(ChatColor.RED+e.getMessage());
							
								e.printStackTrace();
								
							}
							
						}else {
							p.sendMessage(ChatColor.RED+"/setKeyDoor keyName");
						}
						
					}else {
						p.sendMessage(ChatColor.RED+"Le block selectione n'est pas une IRON_DOOR");
					}
					
				}else {
					p.sendMessage(ChatColor.RED+"Aucun block selectione");
				}
			}
			
		}
		return false;
		
	}

}
