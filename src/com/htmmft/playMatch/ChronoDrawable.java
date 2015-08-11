package com.htmmft.playMatch;

import java.awt.Color;
import java.awt.Graphics;

public class ChronoDrawable {

	public void initdraw(Graphics g){
		draw(g,"00:00");
	}
	
	public void paint(Graphics g, String chrono){
		draw (g,chrono);
	}
	
	private void draw(Graphics g,String chrono) {
		Color c = g.getColor();
		g.setColor(Color.black);
		g.drawString(chrono, 10, 10);
		g.setColor(c);
	}
	


}
