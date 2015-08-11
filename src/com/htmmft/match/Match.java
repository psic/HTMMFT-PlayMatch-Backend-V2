package com.htmmft.match;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.htmmft.playMatch.GenerateurJson;
import com.htmmft.playMatch.playMatch;

public class Match{

	private Equipe equipe1;
	private Equipe equipe2;
	private int id;
	private int num_journee;
	private FileWriter fichier;
	
	private boolean changement= false;
	
	private ArrayList<Move> move_ballon;
	private Move ballon_current_move;
	
	private int x_balle;
	private int y_balle;
	
//	private int x_balle_prec;
//	private int y_balle_prec;
//	private int x_dest;
//	private int y_dest;
//	private int decal_x;
//	private int decal_y;
	
	private float tps_programme;
	private float tps_effectif;
	private float tps_pause;
	
	private boolean enpause = false;
	private boolean firstmove = true;

	
	
	public Match(int id, int numjournee) {
		this.id =id;
		this.setNum_journee(numjournee);
		x_balle = playMatch.mid_x + playMatch.min_x;
		y_balle = playMatch.mid_y + playMatch.min_y;
		ballon_current_move = new Move (x_balle,y_balle,0);
		//save_coord();
		move_ballon = new ArrayList<Move>();
		move_ballon.add(ballon_current_move);
	}
	
	public Match() {
		x_balle = playMatch.mid_x + playMatch.min_x;
		y_balle = playMatch.mid_y + playMatch.min_y;
		ballon_current_move = new Move (x_balle,y_balle,0);
		//save_coord();
		move_ballon = new ArrayList<Move>();
		move_ballon.add(new Move (x_balle,y_balle,0));
		//move_ballon.add(ballon_current_move);
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the equipe1
	 */
	public Equipe getEquipe1() {
		return equipe1;
	}
	/**
	 * @param equipe1 the equipe1 to set
	 */
	public void setEquipe1(Equipe equipe1) {
		this.equipe1 = equipe1;
	}
	/**
	 * @return the equipe2
	 */
	public Equipe getEquipe2() {
		return equipe2;
	}
	/**
	 * @param equipe2 the equipe2 to set
	 */
	public void setEquipe2(Equipe equipe2) {
		this.equipe2 = equipe2;
	}
	public int getNum_journee() {
		return num_journee;
	}
	public void setNum_journee(int num_journee) {
		this.num_journee = num_journee;
	}
	/**
	 * @return the fichier
	 */
	public FileWriter getFichier() {
		return fichier;
	}
	/**
	 * @param fichier the fichier to set
	 */
	public void setFichier(FileWriter fichier) {
		this.fichier = fichier;
	}



	/**
	 * 
	 * @throws IOException
	 */
	public void jouer() throws IOException {
		equipe1.engo1.jouerCoupEnvoi();
		
		//Pour chaque dixieme de seconde
		for (int i = 2; i < (10 * 60  * 10) -1 ; i++){
			 
			equipe1.calculDistances();
			equipe2.calculDistances();
			
			 if (equipe1.isPossede_ballon()){
				 equipe1.jouerMatch(i);
				 //equipe2.jouerMatch(i,changement_param);
			 }
			 else if (equipe2.isPossede_ballon()){
				 //equipe2.jouerMatch(i,changement_param);
				 equipe1.jouerMatch(i);

			 }
			 else{
				 equipe1.jouerMatch(i);
				 //equipe2.jouerMatch(i,changement_param);
			 }
			 avance();
		}
		
		GenerateurJson generateur = new GenerateurJson(fichier, move_ballon, equipe1, equipe2);
		generateur.GenerateJson();
		
	}
	
	public void trouveGardiens() {
		equipe1.trouveGardien();
		equipe2.trouveGardien();

	}
	/**
	 * @return the y_balle
	 */
	public int getY_balle() {
		return y_balle;
	}
	/**
	 * @param y_balle the y_balle to set
	 */
	public void setY_balle(int y_balle) {
		this.y_balle = y_balle;
	}
	/**
	 * @return the x_balle
	 */
	public int getX_balle() {
		return x_balle;
	}
	/**
	 * @param x_balle the x_balle to set
	 */
	public void setX_balle(int x_balle) {
		this.x_balle = x_balle;
	}
	public String getBallonMove(int i) {
		String json_result = "\n { \n \"id\":0,\n \"x\":" + x_balle+", \n \"y\":"+ y_balle +"\n},";
		return json_result;
	}
	public void BallonCoupEnvoi(){
		move_ballon.add(new Move(x_balle,y_balle,0));
	}

	public float CalculNextMove(int vitesse) {
//		//int diffx = Math.abs(x_balle -x_balle_prec);
//		//int diffy = Math.abs(y_balle-y_balle_prec);
//		
//		//int diffx = Math.abs(x_dest -x_balle);
//		//int diffy = Math.abs(y_dest-y_balle);
//		int diffx = x_dest -x_balle;
//		int diffy = y_dest-y_balle;
//		int dist = (int) Math.sqrt((diffx*diffx)+(diffy*diffy));
//
//		// Une vitesse de 0 à 100.
//		//à 100 on fait 600 pixel en 10 secondes (10 000 ms)
//		// on fait 100 pixel en 1,6 seconde
//		double pix_par_seconde = vitesse * 0.6;
//		float tps =  (float) (dist / pix_par_seconde );
//		if (tps != 0){
//			decal_x = (int) (diffx / (tps*10));
//			decal_y = (int) (diffy / (tps*10));
//			tps_programme = tps*10;
//		}
//		else{
//			decal_x = diffx;
//			decal_y = diffy;
//			tps_programme = 1;
//		}
//		
//		//tps_balle = tps;
//		return tps_programme;
		return 0;
	}

	public void save_coord(){

	//	x_balle_prec = x_balle;
		//y_balle_prec = y_balle;
	}
	/**
	 * @return the tps_balle
	 */
	public float getTps_balle() {
		return tps_effectif;
	}
	/**
	 * @param tps_balle the tps_balle to set
	 */
	public void setTps_balle(int tps_balle) {
		this.tps_effectif = tps_balle;
	}
	/**
	 * @return the changement
	 */
	public boolean isChangement() {
		return changement;
	}
	/**
	 * @param changement the changement to set
	 */
	public void setChangement(boolean changement) {
		this.changement = changement;
	}
	/**
	 * @return the move_ballon
	 */
	public ArrayList<Move> getMove_ballon() {
		return move_ballon;
	}
	/**
	 * @param move_ballon the move_ballon to set
	 */
	public void setMove_ballon(ArrayList<Move> move_ballon) {
		this.move_ballon = move_ballon;
	}
	
	public void programme_deplacement(int x, int y,int vitesse) {
//		//move_ballon.add(new Move (x_balle_prec,y_balle_prec,tps_programme,'M'));
//		if (tps_effectif >0){
//			
//				move_ballon.add(new Move (this.x_balle,this.y_balle,tps_effectif/10,'M'));
//				//System.out.println("----> Enregistre Deplacement Ballon vers " + x_balle + "," + y_balle + " en " + tps_effectif + "(" + tps_programme + ")");
//			if (enpause){
//				move_ballon.add(new Move (0,0,tps_pause/10,'W'));
//				//System.out.println("----> Enregistre Pause Ballon de" + tps_pause);
//				enpause=false;
//			}
//		}
//		x_dest=x;
//		y_dest=y;
//		//x_dest = checkX(x_dest);
//		//y_dest = checkY(y_dest);
//		float tps = CalculNextMove(vitesse);
//		tps_effectif = 0;
//		tps_pause = 0;
//		changement=true;
//		//System.out.println(" #### CHANGEMENT #######");
//		//move_ballon.add(new Move (x,y,tps,'M'));
//		//CalculNextMove(vitesse);
//		//System.out.println("Deplacement ballon programme de " + x_balle_prec +"," + y_balle_prec +" vers " + x + "," + y + " en " + tps_programme );
//		x_balle_prec = x_dest;
//		y_balle_prec= y_dest;
		
	}
	
	private void avance(){
//	//	//System.out.println("avance balle "+ x_balle +"," + y_balle + " != " + x_dest + " tps_effectif " + tps_effectif + " tps_programme " + tps_programme);
//		if(x_balle> playMatch.max_x || x_balle < playMatch.min_x || y_balle > playMatch.max_y || y_balle < playMatch.min_y){
//			//la balle est sortie.
//			//move_ballon.add(new Move (this.x_balle,this.y_balle,tps_effectif,'M'));
//			//System.out.println("Le ballon est sortie");
//			//programme_deplacement(200, 300, 150);
//			move_ballon.add(new Move (this.x_balle,this.y_balle,tps_effectif/10,'M'));
//	//		//System.out.println("Ajout Deplacement Ballon vers " + x_balle + "," + y_balle + " en " + tps_effectif + "(" + tps_programme + ")");
//			x_balle =300;
//			y_balle=200;
//			changement=true;
//			//System.out.println(" #### CHANGEMENT #######");
//			//move_ballon.add(new Move (this.x_balle,this.y_balle,0,'M'));
//		}
//		else
//		{
//			if(tps_effectif < tps_programme){
//				x_balle+=decal_x;
//				y_balle+=decal_y;
//				tps_effectif++;
//	//			//System.out.println("avance balle "+ x_balle +"," + y_balle);
//				//x_balle = checkX(x_balle);
//				//y_balle = checkY(y_balle);
//			}
//		else{
//			tps_pause++;
//			enpause = true;
//	
//			}
//		}
//		//	changement =true;
//			

			
	}
	/**
	 * @return the firstmove
	 */
	public boolean isFirstmove() {
		return firstmove;
	}
	/**
	 * @param firstmove the firstmove to set
	 */
	public void setFirstmove(boolean firstmove) {
		this.firstmove = firstmove;
	}
	
	public Move getBallonCurentMove(){
		return ballon_current_move;
	}
	public void finishMoveBallon() {
		move_ballon.add(ballon_current_move);
		ballon_current_move = new Move (ballon_current_move.getX_dest(),ballon_current_move.getY_dest());
	}
}