package command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.boss.KeyedBossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Modele.Game;
import Modele.GamePlayer;
import Utils.Log;
import Utils.StringUtility;
import fr.xam74er1.spigot.Main;
import net.md_5.bungee.api.ChatColor;

public class cmdGameManger implements CommandExecutor {
	Player p ;
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(sender instanceof Player) {
			p = (Player) sender;

			if(p.hasPermission("gameMaster")||p.isOp()) {

				if(cmd.getName().equalsIgnoreCase("gameStart")) {
					gameStart();
				}else if(cmd.getName().equalsIgnoreCase("gameinfo")) {
					info();
				}else{
					if(args.length==0) {
						help();
					}else if(args.length>=1) {

						if(args[0].equalsIgnoreCase("start")) {

							gameStart();
						}else if(args[0].equalsIgnoreCase("stop")) {
							Log.displaySucesse(p, "Fin de la game");
							gameStop();
						}else if(args[0].equalsIgnoreCase("pause")) {
							Log.displaySucesse(p, "Mise en pose de la game");
							gamePause();
						}else if(args[0].equalsIgnoreCase("debug")) {

							debug();
						}else if(args[0].equalsIgnoreCase("name")) {
							if(args.length>1) {
								name(args[1]);
							}else {
								Log.displayError(p, "Vous avez oublie de donne un nom a la game");
							}
						}else if(args[0].equalsIgnoreCase("party")) {

							Log.displayWarning(p, "use /party");
						}else if(args[0].equalsIgnoreCase("time")) {
							time();
						}else if(args[0].equalsIgnoreCase("info")){
							info();
						}else {

							help();
						}

					}
				}

			}
		}
		// TODO Auto-generated method stub
		return false;
	}

	public void info() {
		p.sendMessage(ChatColor.AQUA+"----------------------------------");
		p.sendMessage(ChatColor.GOLD+"Information sur la game : ");
		Game g = Main.getGame();
		p.sendMessage(ChatColor.DARK_GREEN+"- Nom : "+g.getName()+ChatColor.BLUE+" - Description : \" "+g.getDescription()+"\""+ChatColor.DARK_PURPLE+" - dure : "+StringUtility.sec2TimeUnite(g.getTime()));
		g.displayAction(p);

		HashMap<String, GamePlayer> list =	g.getListParty();
		if(list==null||list.isEmpty()) {
			p.sendMessage(ChatColor.DARK_RED+"\n La party ne contien pas de joeur ");
		}else {
			p.sendMessage(ChatColor.GOLD+"List des joeur de la game : ");
			int i = 0;
			ChatColor color;
			for(GamePlayer gp : list.values()) {
				if(i%2==0) {
					color = ChatColor.GREEN;
				}else {
					color = ChatColor.DARK_GREEN;
				}
				i++;
				p.sendMessage(color+"- "+gp.getP().getName());
			}
		}
		p.sendMessage(ChatColor.AQUA+"----------------------------------");
	}

	public void name(String name) {
		Main.getGame().setName(name);
	}


	public void time() {
		String s = "";
		int time = Main.getGame().getTime();

		Log.displayWarning(p, "La dure de la game est de "+StringUtility.sec2TimeUnite(time)+"");
		p.sendMessage(ChatColor.GRAY+"Pour changer la dure de la game fait /gametime");
	}

	public void debug() {

		Iterator<KeyedBossBar> it = 	Bukkit.getBossBars();

		while(it.hasNext()) {

			KeyedBossBar kbb = it.next();
			kbb.setVisible(false);
			kbb.removeAll();

		}

	}

	private void gamePause() {
		// TODO Auto-generated method stub
		Main.getGame().pause();
	}

	public void help() {
		Log.displayError(p, "/gameManager {start|stop|pause|time|name|info}");
	}

	public void gameStart() {
		Log.print("start");
		if(Main.getGame().getListParty().size()>0) {
			if(Main.getGame().getTime()>0) {
				Main.getGame().start();
				Log.displaySucesse(p, "Lancement de la game");
			}else {
				Log.displayError(p, "Vottre partie a un temps de 0s vous devriez lui mettre un temp (/setGameTime h:m:s ou /gameManager time h:m:s");
			}
		}else {
			Log.displayError(p, "Vous n ' avez pas de player dans votre party , rajoute en pour commence la game ");
			Log.displayWarning(p,"Pour rajoute des player a une party faite /party add <player_name>");
		}
	}

	public void gameStop() {
		Log.print("stop");
		Main.getGame().stop();
	}

}
