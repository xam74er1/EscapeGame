package command;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Utils.EG_Exception;
import Utils.Log;
import fr.xam74er1.spigot.Main;


public class cmdStartSetting implements CommandExecutor {
	Player p;


	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(sender instanceof Player) {
			p = (Player) sender;

			if(p.hasPermission("gameMaster")||p.isOp()) {

				if(args.length>0) {

					if(args[0].equalsIgnoreCase("add")) {
						add(args);
					}else if(args[0].equalsIgnoreCase("remove")) {
						remouve(args);
					}else if(args[0].equalsIgnoreCase("display")||args[0].equalsIgnoreCase("list")) {
						Main.getGame().displayAction(p);
					}else if(args[0].equalsIgnoreCase("clear")) {
						Main.getGame().clearAction();
						Log.displaySucesse(p, "Toute les action on ete retire");
					}else {
						help();
					}

				}else {
					help();
				}
			}

		}
		// TODO Auto-generated method stub
		return false;
	}
	
	public void remouve(String[] args) {


		try {
			int index = Integer.parseInt(args[1]);
			try {
				Main.getGame().remouveAction(index);
				Log.displaySucesse(p, "L'action a bien ete suprime");
				Log.displayWarning(p, "Attention les index on changer , nouvelle position : ");
			} catch (EG_Exception e) {
				// TODO Auto-generated catch block
				Log.displayError(p, e.getMessage());
			}

			Main.getGame().displayAction(p);
		}catch(Error e) {
			Log.displayError(p, "StartSetting remove <index> : enleve le setting a la postion <index>");
		}

	
	}
	
	public void add(String[] args) {


		if(args.length>1) {
			try {
				if(args[1].equalsIgnoreCase("tp")) {
					if(args.length==6) {

						Main.getGame().addAction("tp "+args[3]+" "+args[4]+" "+args[5]+" "+p.getWorld().getName());
						Log.displaySucesse(p, "Ajout d'un depare en "+args[3]+" "+args[4]+" "+args[5]+" dans le monde "+p.getWorld().getName());
					}else {
						Location loc = p.getLocation();
						Main.getGame().addAction("tp "+loc.getBlockX()+" "+loc.getBlockY()+" "+loc.getBlockZ()+" "+loc.getWorld().getName());
						Log.displaySucesse(p, "Ajout d 'un depare en "+loc.getBlockX()+" "+loc.getBlockY()+" "+loc.getBlockZ()+" dans le monde "+loc.getWorld().getName());
					}


				}else if(args[1].equalsIgnoreCase("msg")) {
					if(args.length>2) {
						if(args[2].equalsIgnoreCase("help")||args[2].equalsIgnoreCase("color")) {
							p.sendMessage("Vous pouvez utilise des couleur dans vos message . \n Pour cela tapper £ suivit du numerot de la couleur voulus \n  Les couleur son :");
							
							for(ChatColor cc : ChatColor.values()) {
								p.sendMessage(cc+" £"+cc.getChar()+" ");
							}
							
							p.sendMessage(net.md_5.bungee.api.ChatColor.GREEN+"ex : "+net.md_5.bungee.api.ChatColor.RESET+"£1 hello $4 world => §1 hello §2 world");
						}else {
							String msg = "";

							for(int i = 1;i<args.length;i++) {
								msg+= args[i]+" ";
							}

							Main.getGame().addAction(msg);
							Log.displaySucesse(p, "Message rajoute au debut");
						}
					}else {
						helpMsg();
					}

				}else {
					helpTp();
					helpMsg();
				}

			} catch (EG_Exception e) {

				help();
				// TODO Auto-generated catch block
				Log.displayError(p, e.getMessage()+"\n \n ");


			}
		}else {
			help();
		}

	
	}

	public void helpTp() {
		Log.displayError(p, "StartSetting add tp \n "
				+ "StartSetting add tp <x> <y> <z> : teleporte tout les joeur de la partie au debut de la game \n\n");
	}


	public void helpMsg() {
		Log.displayError(p, "StartSetting add msg <message> : ajoute un message au lancement de la game \n\n");
		Log.displayError(p, "StartSetting add msg help : donne des information sur les couleur \n\n");
	}


	public void help() {

		helpTp();

		helpMsg();

		Log.displayError(p, "StartSetting remove <index> : enleve le setting a la postion <index> \n\n");
		Log.displayError(p, "StartSetting display | list : affiche tout les parametre lance au debut de la game \n\n");
		Log.displayError(p, "StartSetting clear : enleve toute les action au debut de la game \n\n");

	}

}
