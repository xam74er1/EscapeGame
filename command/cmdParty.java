package command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Utils.Log;
import fr.xam74er1.spigot.Main;

public class cmdParty implements CommandExecutor {
	Player p;
	@Override
	public boolean  onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(sender instanceof Player) {
			p = (Player) sender;
			// TODO Auto-generated method stub
			if(p.hasPermission("gameMaster")||p.isOp()) {

				if(cmd.getName().equalsIgnoreCase("partyadd") ) {

					if(args.length>0) {
						partyAdd(args[0]);
					}else {
						partyAddHelp();
					}

				}else if((cmd.getName().equalsIgnoreCase("party")&&args.length>0&&args[0].equalsIgnoreCase("add"))) {
					if(args.length>1) {
						partyAdd(args[1]);
					}else {
						partyAddHelp();
					}
				}else 	if(cmd.getName().equalsIgnoreCase("partyremove") ) {

					if(args.length>0) {
						partyRemouve(args[0]);
					}else {
						partyRemouveHelp();
					}

				}else if((cmd.getName().equalsIgnoreCase("party")&&args.length>0&&args[0].equalsIgnoreCase("remove"))) {
					if(args.length>1) {
						partyRemouve(args[1]);
					}else {
						partyRemouveHelp();
					}


				}else 	if(cmd.getName().equalsIgnoreCase("partydisband") ) {

					partyDisband();

				}else if((cmd.getName().equalsIgnoreCase("party")&&args.length>0&&args[0].equalsIgnoreCase("disband"))) {
					partyDisband();


				}
				else if((cmd.getName().equalsIgnoreCase("party")&&args.length>0&&args[0].equalsIgnoreCase("list"))) {
				partyList();


				}else {
					help();
				}
			}

		}
		return false;
	}

	public void partyAdd(String name) {
		boolean res = Main.getGame().addPlayerParty(name);

		if(res) {
			if(name.equalsIgnoreCase("all")) {
				Log.displaySucesse(p, "Ajout de tout les joeur present a la partie (meme vous ) ");
			}else {
			Log.displaySucesse(p, "Ajoute de "+name+" a la partie");
			}
		}else {
			Log.displayError(p, "Ce player n'existe pas ou ne cest pas connecte ");
		}
	}

	public void partyRemouve(String name) {
		boolean res = Main.getGame().remouvePlayerParty(name);

		if(res) {
			Log.displaySucesse(p, "Retrais de "+name+" a la partie");
		}else {
			Log.displayError(p, "Ce player n'existe pas ou ne cest pas connecte ");
		}
	}

	public void partyList() {
		p.sendMessage(ChatColor.DARK_GREEN+"Menbre de la partie : ");
		for(String s :Main.getGame().getListParty().keySet()) {
			p.sendMessage(ChatColor.YELLOW+"- "+s);
		}
	}
	
	public void partyAddHelp() {
		Log.displayError(p, "PartyAdd <playerName> : ajoute un joeur a la partie");
		Log.displayError(p, "PartyAdd all : ajoute de tout les joeur a la partie");
	}

	public void partyRemouveHelp() {
		Log.displayError(p, "PartyRemove <playerName> : enleve un joeur a la party");
	}

	public void partyDisband() {
		Main.getGame().clearParty();
	}
	
	public void partyDisbandHelp() {
		Log.displayError(p, "partydisband : enleve tout les joeur de la partie");
	}

	public void help() {
		partyAddHelp();
		partyRemouveHelp();
		partyDisbandHelp();
		Log.displayError(p, "party list : list de tout les joeur de la partie");
	}

}
