package Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.xam74er1.spigot.Main;

public class OnJoin implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Main.getGame().addPlayer(e.getPlayer());
	}

}
