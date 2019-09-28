package command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Utils.Log;
import fr.xam74er1.spigot.Main;

public class cmdsetGameTime implements CommandExecutor {
	Player p ;
	int time=0;
	boolean help = false;
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(sender instanceof Player) {
			p = (Player) sender;

			Log.print(cmd.toString()+"");
			Log.print(label);

			
			if(p.hasPermission("gameMaster")||p.isOp()) {
				if(args.length>0) {
					this.time = 0;
					String time = args[0];

					String tab[] = time.split(":");

					switch (tab.length) {
					case 1:
						decomposeTime(tab[0]);

						break;

					case 2:
						decomposeTime(tab[0]);
						decomposeTime(tab[1]);
						break;
					case 3:
						decomposeTime(tab[0]);
						decomposeTime(tab[1]);
						decomposeTime(tab[2]);
						break;

					default:
						help();
						break;
					}

					if(!help) {
						Log.displaySucesse(p, "Le nouveau temp de la game est de "+args[0]+"(soit "+this.time+" seconde )");

						Main.getGame().setTime(this.time);
					}else {
						Log.print("une erreur est survenue");
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

	public void help() {
		Log.displayError(p, "/setGameTime h:min:sec \n cette commande sert a definir le temps de la game  \n ex : /setGameTime 1h \n ex : /setGameTime 1h:42m:10s");
		help = true;
	}

	public int decomposeTime(String s) {


		try {

			char c = s.charAt(s.length()-1);
			String str = s.substring(0, s.length()-1);
	
			int tmp = Integer.parseInt(str);
			
			
			switch (c) {
			case 'h':
				time += 3600*tmp;
				
				break;

			case 'm':
				time += 60*tmp;
				
				break;
			case 's':
				time+=tmp;
				
				break;

			default:
				Log.print("default : "+s);
				help();
				break;
			}
		}catch(Error e) {
			e.printStackTrace();

			help();
		}catch(java.lang.NumberFormatException e2) {
			Log.displayError(p, "Pense a sepere les unite avec des ':'");
			help();
		}
		return 0;
	}

}
