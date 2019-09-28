package Modele;

import org.bukkit.block.Block;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class GamePlayer {

	Game game;
	Player p;
	Block selectBlock = null;
	Ellement el;
	BossBar boss;
	
	long cliquedTime = 0;
	
	
	public GamePlayer(Game game, Player p) {
		super();
		this.game = game;
		this.p = p;
	}
	
	
	/////////////////////////////
	
	
	
	public Game getGame() {
		return game;
	}
	public BossBar getBoss() {
		return boss;
	}


	public void setBoss(BossBar boss) {
		boss.addPlayer(p);
		this.boss = boss;
	}


	public void setGame(Game game) {
		this.game = game;
	}
	public Player getP() {
		return p;
	}
	public void setP(Player p) {
		this.p = p;
	}


	public Block getSelectBlock() {
		return selectBlock;
	}


	public void setSelectBlock(Block selectBlock) {
		this.selectBlock = selectBlock;
	}


	public Ellement getEllement() {
		return el;
	}


	public void setEllemment(Ellement el) {
		this.el = el;
	}


	public long getCliquedTime() {
		return cliquedTime;
	}


	public void setCliquedTime(long cliquedTime) {
		this.cliquedTime = cliquedTime;
	}
	
	
	
	
	
	
}
