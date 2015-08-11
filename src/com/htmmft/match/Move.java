package com.htmmft.match;

public class Move {
	
	private int x_orig;
	private int y_orig;
	private int x_dest;
	private int y_dest;
	private int offsetX;
	private int offsetY;
	private int x_current;
	private int y_current;
	private int offsetX_per_dizseconde;
	private int offsetY_per_dizseconde;
	
	private char type;
	private float time;
	
	public Move( int x, int y, int time) {
		this.x_dest = x;
		this.y_dest=y;
		this.time =time;
	}
	public Move( int x, int y, float tps_effectif, char move) {
		this.x_dest = x;
		this.y_dest=y;
		this.time =tps_effectif;
		type = move;
	}
	public Move(int x, int y) {
		x_orig = x;
		y_orig = y;
		
		x_current = x;
		y_current = y;

	}
	
	/**
	 * @return the type
	 */
	public char getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(char type) {
		this.type = type;
	}
	/**
	 * @return the time
	 */
	public float getTime() {
		return time;
	}
	
	
	/**
	 * @return the offsetX
	 */
	public int getOffsetX() {
		return offsetX;
	}
	
	/**
	 * @return the offsetY
	 */
	public int getOffsetY() {
		return offsetY;
	}
	
	/**
	 * @return the offsetX_per_dizseconde
	 */
	public int getOffsetX_per_dizseconde() {
		return offsetX_per_dizseconde;
	}
	/**
	 * @return the offsetY_per_dizseconde
	 */
	public int getOffsetY_per_dizseconde() {
		return offsetY_per_dizseconde;
	}
	/**
	 * @return the x_current
	 */
	public int getX_current() {
		return x_current;
	}
	
	/**
	 * @return the y_current
	 */
	public int getY_current() {
		return y_current;
	}
		
	/**
	 * @return the x_orig
	 */
	public int getX_orig() {
		return x_orig;
	}
	
	/**
	 * @return the y_orig
	 */
	public int getY_orig() {
		return y_orig;
	}
	
	
	
	public void setDestMove(int x, int y, int vitesse) {
			x_dest = x;
			y_dest = y;
			offsetX = x - x_orig;
			offsetY = y - y_orig;
			int distance = (int) Math.sqrt((offsetX*offsetX)+(offsetY*offsetY));
			// Une vitesse de 0 à 100.
//			//à 100 on fait 600 pixel en 10 secondes (10 000 ms)
//			// on fait 100 pixel en 1,6 seconde
			double pix_par_seconde = vitesse * 0.6;
			float tps = (float) (distance / pix_par_seconde );
			//time = tps*10;
			time = (float) (Math.round(tps*10.0)/10.0);
			type = 'M';
			offsetX_per_dizseconde = (int) (offsetX / time);
			offsetY_per_dizseconde = (int) (offsetY / time);
		
	}
	/**
	 * @return the x_dest
	 */
	public int getX_dest() {
		return x_dest;
	}
	
	/**
	 * @return the x_dest
	 */
	public int getY_dest() {
		return y_dest;
	}
	public boolean isFinish() {//TODO avec le sens pour l'equipe 2
		return ( x_current >= x_dest  && y_current >= y_dest);
	}
	public void avance() {
		x_current += offsetX_per_dizseconde;
		y_current += offsetY_per_dizseconde;
	
		
	}
	public void stopMove() {
		x_dest = x_current;
		y_dest= y_current;
		
	}
}
