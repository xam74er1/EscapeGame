package command;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import Modele.BlockInventory;
import Modele.GamePlayer;
import Modele.PorteACode;
import Utils.EG_Exception;
import fr.xam74er1.spigot.Main;
import net.md_5.bungee.api.ChatColor;

public class cmdBlockInventory implements CommandExecutor {

	

	@Override
	public boolean  onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(sender instanceof Player) {
			Player p = (Player) sender;
			
			if(p.hasPermission("gameMaster")||p.isOp()) {
					
				GamePlayer gp = Main.getGame().getPlayer(p.getName());
				
			
				
				if(gp.getSelectBlock()!=null) {
					Block b = gp.getSelectBlock();
					
					if(args.length==0) {
						BlockInventory binv = new BlockInventory(0, b);
						p.sendMessage(ChatColor.GREEN+"Le block possede maintenan un inventaire ");
					}else if(args.length ==1) {
						
							help(p);
						
					}else if(args.length>1){
						if(args[0].equalsIgnoreCase("code")) {
							

							String CodeAcutelle ="";
							if(args.length<3) {
								CodeAcutelle += (int) 9999*Math.random();
							}else {
								CodeAcutelle = args[2];
							}
							
							String codeJuste = args[1];
							
						}else if(args[0].equalsIgnoreCase("key")) {
							
							String keyName ="";
							for(int i = 1;i<args.length;i++) {
								keyName+=args[i]+" ";
							}
							keyName = keyName.trim();
							
						}else {
							help(p);
						}
					}
					
				}else {
					p.sendMessage(ChatColor.RED+"Aucun block selectione");
				}
			}
			
		}
		return false;
		
	}

	public void printError(Player p,String msg) {
		p.sendMessage(ChatColor.RED+msg);
	}
	
	public void help(Player p) {
		String msg = "/BlockInventory : Ouvre un inventire quand on clique sur un block \n"
				+ "/BlockInventory code code_juste {code_actuelle} : Montre un code pour ouvrir l'iventaire \n"
				+ "/BlockInventory key key_name : Clique sur un bloque avec la clef pour ouvrir l'inventaire "
				+"/BlockInventory help : montre l'aide";
		
		printError(p, msg);
	}
	
	


}
