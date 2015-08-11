package com.htmmft.match.visu;

import java.awt.*;

public  class TerrainDrawable {
	
	private int x;
	private int y;
	private int width;
	private int height;
	
	public TerrainDrawable(int x, int y, int w, int h){
		this.x =x;
		this.y=y;
		this.width = w;
		this.height = h;
	}
	
	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.black);
		g.drawRect(x,y,height,width);
		g.setColor(c);
	}
	
	
}
