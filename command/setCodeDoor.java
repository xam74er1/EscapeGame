package command;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;

import com.mysql.jdbc.Util;

import Modele.GamePlayer;
import Modele.PorteACode;
import Utils.EG_Exception;
import Utils.GameConstante;
import Utils.Log;
import fr.xam74er1.spigot.Main;
import net.md_5.bungee.api.ChatColor;

public class setCodeDoor implements CommandExecutor {

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
							try {
								
								String CodeAcutelle ="";
								if(args.length<2) {
									CodeAcutelle += (int) 9999*Math.random();
								}else {
									CodeAcutelle = args[1];
								}
								
								new PorteACode(args[0], CodeAcutelle, b);
								p.sendMessage(ChatColor.GREEN+"Code mis avec succees : nouveau code <"+args[0]+">");
							} catch (EG_Exception e) {
								// TODO Auto-generated catch block
								p.sendMessage(ChatColor.RED+e.getMessage());
								Log.print("catch de lerreur de nbr avec affichage du msg ");
								e.printStackTrace();
								
							}catch(NumberFormatException e2) {
								p.sendMessage(ChatColor.RED+"Une erreur est survenue votre code dois etre un nombre en 0 et 9999");
								Log.print("e2 du nbr format");
								e2.printStackTrace();
							}
						}else {
							p.sendMessage(ChatColor.RED+"Usage : /setCodeDoor code_juste {code_actuelle} \n"
									+ "Le code dois etre un nombre de maximome 4 chiffre compris entre 0 et 9999");
							Utils.Log.print("Size : "+args.length);
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
