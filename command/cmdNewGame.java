package command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Modele.Game;
import Utils.Log;
import fr.xam74er1.spigot.Main;

public class cmdNewGame implements CommandExecutor {
	int time = 3600;
	Player p;
	boolean error = false;
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(sender instanceof Player) {
			p = (Player) sender;
			Game game = null ;

			if(p.hasPermission("gameMaster")||p.isOp()) {
				if(args.length>0) {
					String name = args[0];
					String description ="";

					if(args.length>1) {
						description = args[1];

					}
					if(args.length>2) {
						gereTime(args[2]);
						try {
							game = new Game(name,description,time);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							error(p);
						}
					}else {
						try {
							game = new Game(name,description);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							error(p);
						}
					}

					if(!error&&game!=null) {
						Log.displaySucesse(p, "Une nouvelle game a bien ete ajoute ");
						Main.setGame(game);

					}

				}else {
					help();
				}
			}

		}
		return false;
	}

	public void gereTime(String str) {
		this.time = 0;
		String time = str;

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
		default :
		Log.displayError(p, "Le format de la dure dois etre de type xxh:yym:zzs");
		error = true;
		}

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
				time+=tmp;
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

	public void error(Player p) {
		Log.displayError(p, "Une game comportant ce nom la existe deja ou une erreur est survenue ");
		error = true;
	}
	public void help() {
		Log.displayError(p, "newgame <name> {description} {time}");
	}

}
