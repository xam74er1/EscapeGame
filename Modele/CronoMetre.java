package Modele;

import java.util.concurrent.atomic.AtomicBoolean;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;

import Utils.Log;
import Utils.StringUtility;
import net.md_5.bungee.api.ChatColor;

public class CronoMetre implements Runnable{

	int ini = 0;
	int actual = 0;
	Game game;
	BossBar bossBare = null;

	boolean pause = false;

	Thread t;

	int interal = 1000;
	private final AtomicBoolean running = new AtomicBoolean(true);

	//Ini = sec 
	public CronoMetre(int ini,Game game) {
		super();
		this.ini = ini;
		this.actual = ini;
		this.game = game;
	}



	//Bosse Bare
	public void displayBossBar(String s) {
		Log.print("display boss bare"+bossBare);
		if(bossBare==null) {
			CreateBossBare();
		}

		for(GamePlayer gp : game.getListParty().values()) {
			gp.setBoss(bossBare);
		}

		ActualiseBosseBar(s);

	}

	public void CreateBossBare() {

		if(bossBare!=null) {

			bossBare.removeAll();
		}

		bossBare = Bukkit.createBossBar("Creation", org.bukkit.boss.BarColor.BLUE, BarStyle.SOLID,BarFlag.PLAY_BOSS_MUSIC);
		bossBare.removeFlag(BarFlag.PLAY_BOSS_MUSIC);
		bossBare.setVisible(true);
	}

	public void ActualiseBosseBar(String s) {
		double temp = 1-(actual/ini);
		if(temp>0) {

			bossBare.setProgress(temp);
		}


		bossBare.setTitle(ChatColor.GOLD+game.getName()+" | "+ChatColor.DARK_RED+s);
		//bossBare.setVisible(true);

	}

	public void ActualiseBosseBar(int sec) {
		int div = ini;
	//	double temp = 1-(sec/ini);
		double temp =  ((double )sec/ (double) ini);
		if(temp>0&&temp<=1) {

			bossBare.setProgress(temp);
		}

		String time = StringUtility.sec2Time(sec);
	//	bossBare.setTitle(ChatColor.GOLD+game.getName()+" | "+ChatColor.DARK_RED+time);
		bossBare.setTitle(ChatColor.DARK_RED+" | "+ChatColor.UNDERLINE+ChatColor.GOLD+"Temps restants:"+ChatColor.RESET+ChatColor.RED+ChatColor.BOLD+time+ChatColor.DARK_RED+" | ");
		//bossBare.setVisible(true);

	}


	@Override
	public void run() {
		// TODO Auto-generated method stub

		running.set(true);
		//boucle 
		while (running.get()) {
			actual --;

			ActualiseBosseBar(actual);

			if(actual<0) {
				game.stop();
			}

			try {
				Thread.sleep(interal);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		}

	}


	public void start() {
		displayBossBar("Debut");
		this.actual = ini;
		t = new Thread(this);
		t.start();

	}

	public void stop() {
		running.set(false);
		ActualiseBosseBar("Fin de la partie");
		bossBare.removeAll();
		bossBare.setVisible(false);

	}

	public void pause() {
		try {
			if(pause) {
				t.notifyAll();
			}else {
				t.wait();
			}

			pause = !pause;

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void unpause() {
		t.notifyAll();
		pause = false;
	}

	public void reset() {
		actual = ini;
	}
	////////////////////////////////////////::
	public int getIni() {
		return ini;
	}

	public void setIni(int ini) {
		this.ini = ini;
	}

	public int getActual() {
		return actual;
	}

	public void setActual(int actual) {
		this.actual = actual;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public BossBar getBossBare() {
		return bossBare;
	}

	public void setBossBare(BossBar bossBare) {
		this.bossBare = bossBare;
	}

	public boolean isPause() {
		return pause;
	}

	public void setPause(boolean pause) {
		this.pause = pause;
	}



	/////////////////////////////////



}
