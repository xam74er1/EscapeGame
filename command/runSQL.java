package command;

import java.io.Console;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.swing.text.StyledEditorKit.BoldAction;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Modele.EG_Loccation;
import fr.xam74er1.spigot.Main;
import net.md_5.bungee.api.ChatColor;

public class runSQL implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		boolean right = true;
		Player p =null;
		String space = "    ";
		if(sender instanceof Player) {
			p = (Player) sender;

			System.out.println(p.hasPermission("runSQL"));
			right = p.hasPermission("runSQL")||p.isOp();
		}

		if(right) {

			String querry ="";

			for(String s : args) {
				querry +=" "+s;
			}

			try {
				//POur le select on a une metode spetaile 
				if(args[0].equalsIgnoreCase("Select")) {
				ResultSet rs =	Main.dao.querry(querry);



				ResultSetMetaData	rsmt = rs.getMetaData();

				int nb = rsmt.getColumnCount();
				String tmp = "";

				String[] collName = new String[nb];

				for(int i =1;i<=nb;i++) {
					tmp+=rsmt.getColumnName(i)+"|"+space;
					collName[i-1] = rsmt.getColumnName(i);
				}

				if(p != null) {
					p.sendMessage(ChatColor.DARK_PURPLE+tmp);
				}else {
					System.out.println(tmp);
				}


				boolean ping = true;
				ChatColor color = ChatColor.GREEN;

				while(rs.next()) {
					tmp ="";
					for(String col : collName) {
						tmp += rs.getString(col)+"|"+space;
					}

					if(ping) {
						color = ChatColor.GREEN;
					}else {
						color = ChatColor.DARK_GREEN;
					}

					ping = !ping;

					if(p != null) {
						p.sendMessage(color+tmp);

					}else {
						System.out.println(tmp);
					}

				}
				
				rs.close();
				//POur le reste on a une autre metode 
				}else {
					
					int nb = Main.dao.exeUpdate(querry);
					if(p != null) {
						p.sendMessage(ChatColor.DARK_GREEN+" "+nb+" ligne mis a jour");

					}else {
						System.out.println(nb+" ligne mis a jour");
					}
					
					
				}

			}catch(Exception e) {
				if(p != null) {
					p.sendMessage(ChatColor.RED+"La command n'as pas pus aboutire "+e.toString());
				}

				System.out.println(e);
				
			}
		}
		// TODO Auto-generated method stub
		return false;
	}

}
