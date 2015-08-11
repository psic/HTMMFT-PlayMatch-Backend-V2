package com.htmmft.match.visu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JPanel;

import com.htmmft.match.Equipe;
import com.htmmft.match.Joueur;
import com.htmmft.match.Move;
import com.htmmft.playMatch.ChronoDrawable;

import java.awt.Color;
import java.awt.Graphics;


public class JCanvas extends JPanel {

	private List<JoueurDrawable> equipe1 = new LinkedList<JoueurDrawable>();
	private List<JoueurDrawable> equipe2 = new LinkedList<JoueurDrawable>();
	private BallonDrawable ballon;
	private TerrainDrawable terrain; 
	private ChronoDrawable chrono = new ChronoDrawable();
	private int time;
	
	public JCanvas(Equipe equipe1, Equipe equipe2, ArrayList<Move> move_ballon, int minx, int miny, int h, int w) {
		terrain= new TerrainDrawable(minx, miny, h, w);
		int i=1;
		for (Joueur joueur : equipe1.getJoueur()){
			this.equipe1.add(new JoueurDrawable(joueur, Color.red,i));
			i++;
		}
			i=1;
		for (Joueur joueur : equipe2.getJoueur()){
			this.equipe2.add(new JoueurDrawable(joueur, Color.blue,i));
			i++;
		}
		
		ballon = new BallonDrawable(move_ballon);
	}

	public void paint(Graphics g) {
		g.setColor(Color.white);
		//On le dessine de sorte qu'il occupe toute la surface
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		terrain.paint(g);
		/** le chrono**/
		int sec = 0, min = 0;
		sec = time /10;
		min = sec /60;
		String ch =  String.valueOf(min)+":"+String.valueOf(sec%60);
		chrono.paint(g,ch);
		
		for (Iterator<JoueurDrawable> iter = equipe1.iterator(); iter.hasNext();) {
			JoueurDrawable d = (JoueurDrawable) iter.next();
			d.paint(g,time);	
		}
		
		for (Iterator<JoueurDrawable> iter = equipe2.iterator(); iter.hasNext();) {
			JoueurDrawable d = (JoueurDrawable) iter.next();
			d.paint(g,time);	
		}
		
		ballon.paint(g,time);
	}
	
	public void Timer(){
		time++;
	}
}
