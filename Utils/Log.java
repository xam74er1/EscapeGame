package Utils;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class Log {
	
	static boolean log = true;
	
	public static void print(String s) {
		if(log) {
			System.out.println(s);
		}
	}
	
	public static void displayError(Player p,String s) {
		try {
		p.sendMessage(ChatColor.RED+s);
		}catch(Error e) {
			e.printStackTrace();
		}
	}
	
	public static void displaySucesse(Player p,String s) {
		try {
		p.sendMessage(ChatColor.GREEN+s);
		}catch(Error e) {
			e.printStackTrace();
		}
	}
	
	public static void displayWarning(Player p,String s) {
		try {
		p.sendMessage(ChatColor.YELLOW+s);
		}catch(Error e) {
			e.printStackTrace();
		}
	}

}
