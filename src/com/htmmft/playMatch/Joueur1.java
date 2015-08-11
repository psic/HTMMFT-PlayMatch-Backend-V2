package com.htmmft.playMatch;

import com.htmmft.match.Equipe;
import com.htmmft.match.Joueur;

public class Joueur1 extends Joueur {

	public Joueur1(int id, int x, int y, Equipe equipe) {
		super(id, x, y, equipe);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isDevant(int x) {
		return (x > this.x_tactique);
	}
	
	protected boolean isPositiondeTir(){
		//return (x_tactique > playMatch.max_x - (playMatch.mid_x/2) && getCurentMove().getY_current() >  playMatch.min_y +(playMatch.mid_y/2) && getCurentMove().getY_current() < playMatch.max_y - (playMatch.mid_y/2) );
		return false;
	}
	
	protected void tirauBut() {
		
		this.setPossede_ballon(false);
		int vitesse = (int)(100 + Math.random() * physique);
		int y_ = (int)(playMatch.mid_x - (playMatch.mid_y/2) + ( Math.random() * playMatch.mid_y/2) );
		//System.out.println("Tir au but vers " + (playMatch.max_x +5 )+ "," +y_ );
		mon_equipe.getMatch().programme_deplacement(playMatch.max_x +5,y_ , vitesse);		
	}

}
