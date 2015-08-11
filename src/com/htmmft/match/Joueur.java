package com.htmmft.match;

import java.util.ArrayList;

import com.htmmft.playMatch.playMatch;

public abstract class Joueur {
	private int id;
	protected int x_tactique;
	protected int y_tactique;

	private int age; 
	private int xp;
	private int talent=0;;
	private int tactique=0;;
	private int technique=0;; 
	protected int physique=0;;
	private int vitesse =0;
	private int mental=0;;
	private int off;
	private int def;
	private int drt;
	private int ctr;
	private int gch;
	private int cond;
	private int blessure;
	private int moral;
	
	
	private boolean destinataire_passe = false;
	private boolean gardien = false;
	private boolean possede_ballon =false;
	private boolean enpause = false;
	private boolean changement = true;
	
	public int[] distance_coequipier = new int[11];
	public int[] distance_adversaire = new int[11];
	
	protected Equipe mon_equipe;
	private Equipe adversaire;
	

	private ArrayList<Move> move;
	private Move current_move;
	
	//???
	private float tps_programme;
	private float tps_effectif;
	private float tps_pause;
	private int tps_attente=-1;

	public Joueur(int id, int x, int y, Equipe equipe) {
		this.setId(id);
		this.x_tactique = x;
		this.y_tactique = y;
		current_move = new Move(x,y);
		mon_equipe =equipe;
		move = new ArrayList<Move>();
	}
	/**
	 * 
	 * 
	 * ****************************** COUP D'ENVOI ****************************************
	 * 
	 * 
	 * 
	 * *************************************************************************************/


	public void coupEnvoiE1() {

		boolean ok =true;
		do{
			x_tactique = (int) (x_tactique * 0.9);
			if (y_tactique <  playMatch.mid_y + playMatch.min_y -50 || y_tactique >  playMatch.mid_y + playMatch.min_y + 50 ){
				if (x_tactique < playMatch.mid_x + playMatch.min_x){
					ok = false;
				}
			}
			else {
				if (x_tactique < playMatch.mid_x + playMatch.min_x + 50){
					ok = false;
				}
			}
			if(x_tactique < playMatch.min_x){
				x_tactique = playMatch.min_x;
				ok =false;		
			}
		}while(ok);
		//current_move = new Move (x_tactique,y_tactique);
		//move.add(new Move (x_tactique,y_tactique));
		current_move = new Move (x_tactique,y_tactique);
		current_move.setDestMove(x_tactique, y_tactique, 0);
		//move.add(new Move (x_tactique,y_tactique));
		move.add(current_move);
	}

	
	/************ ENGO ************/

	public void coupEnvoiE2() {

		x_tactique =retournex(x_tactique);
		y_tactique=retourney(y_tactique);

		boolean ok =true;
		do{
			x_tactique = (int) (x_tactique * 1.05);
			if (y_tactique <  playMatch.mid_y +  playMatch.min_y - 50 || y_tactique >  playMatch.mid_y + playMatch.min_y + 50 ){
				if (x_tactique > playMatch.mid_x + playMatch.min_x){
					 //	//System.out.println(x + "," + y  + " / " + (playMatch.mid_x + playMatch.min_x) + ", " + (playMatch.mid_y +  playMatch.min_y )  );
					ok = false;
				}
			}
			else {
				if (x_tactique > playMatch.mid_x + playMatch.min_x + 70){
					ok = false;
				}
			}
			if(x_tactique > playMatch.max_x){
				x_tactique = playMatch.max_x - 10;
				ok =false;		
			}
		}while(ok);
		//current_move = new Move (x_tactique,y_tactique,0);
		//move.add(new Move (x_tactique,y_tactique,0));
		current_move = new Move (x_tactique,y_tactique);
		current_move.setDestMove(x_tactique, y_tactique, 0);
		//move.add(new Move (x_tactique,y_tactique));
		move.add(current_move);
	}

	private int  retournex(int x){
		return playMatch.max_x -(x -  playMatch.min_x);
	}

	private int  retourney(int y){
		return  playMatch.max_y - (y - playMatch.min_y);
	}
	
	//Placement du joueur pour l'engament
	public void coupEnvoiSpecial1() {

		x_tactique=playMatch.mid_x + playMatch.min_x;
		y_tactique=playMatch.mid_y - 10;
		setPossede_ballon(true);
		mon_equipe.setPossede_ballon(true);
		current_move = new Move (x_tactique,y_tactique);
		current_move.setDestMove(x_tactique, y_tactique, 0);
		//move.add(new Move (x_tactique,y_tactique));
		move.add(current_move);

	}
	//Placement du joueur pour l'engament
	public void coupEnvoiSpecial2() {
		x_tactique=playMatch.mid_x + playMatch.min_x;
		y_tactique=playMatch.mid_y + 20;
		current_move = new Move (x_tactique,y_tactique);
		current_move.setDestMove(x_tactique, y_tactique, 0);
		//move.add(new Move (x_tactique,y_tactique));
		move.add(current_move);
	}

	
	/** ******************************************** FONCTION DE JEU *******************************************************************/



	/*** @param i *********************************/

	public void jouerMatch(int seconde)
	{	

		if (isGardien())
			return ;
		//System.out.println("=== Joueur ID : " + id  + " / changementEquipe: " + changement + " / possedeEquipe: " + possede_ballon + " / changementJoueur: " + this.changement + " / possedeJoueur: " + this.possede_ballon +" / closer : " + closer + " / dest : " + dest + " /en pause : " +enpause +" =============");
				
				if (mon_equipe.isPossede_ballon())
					mouvement_attaque();
				else
					mouvement_defense();
	}

	private void mouvement_defense() {
		//System.out.println(" Defense");

		int vitesse = (int)(50 + Math.random() * (100 + this.vitesse/2) );
		//System.out.println (mon_equipe.getMatch().getX_balle() + "," + mon_equipe.getMatch().getY_balle() + " / " + x +"," +y); 
		if(DansLeCercle(mon_equipe.getMatch().getX_balle(), mon_equipe.getMatch().getY_balle(), 50)){		
			if(DansLeCercle(mon_equipe.getMatch().getX_balle(), mon_equipe.getMatch().getY_balle(), 2)){
				setPossede_ballon(true);
				mon_equipe.setPossede_ballon(true);
				adversaire.setPossede_ballon(false);
				mon_equipe.getMatch().setChangement(true);
				//System.out.println(" #### CHANGEMENT #######");
				//mouvement_attaque(false);
			}
			else{
				//if (closer){
				if(true){
					//System.out.println("Deplacement vers le  ballon de " + x +"," + y + " vers " + mon_equipe.getMatch().getX_balle() +"," +mon_equipe.getMatch().getY_balle());	
					programme_deplacement(mon_equipe.getMatch().getX_balle(), mon_equipe.getMatch().getY_balle(), vitesse);
				}
				else
					deplacement_sans_ballon_defensif(vitesse);
			}
		}
		else
			deplacement_sans_ballon_defensif(vitesse);	
	}

	private void mouvement_attaque() {
		System.out.println("Mvt_attaque");
		int vitesse = (int)(50 + Math.random() * ( this.vitesse/2) );
		
		if (destinataire_passe){
		
			if (DansLeCercle(mon_equipe.getMatch().getBallonCurentMove().getX_current(), mon_equipe.getMatch().getBallonCurentMove().getY_current(), 40)){
				System.out.println(id + " Dest passe1");
				possede_ballon = true;
				destinataire_passe =false;
				mon_equipe.getMatch().finishMoveBallon();
			}
			else{
				if(mon_equipe.getMatch().getBallonCurentMove().getX_dest() == current_move.getX_current() && mon_equipe.getMatch().getBallonCurentMove().getY_dest() == current_move.getY_current() ){
					System.out.println(id + "Dest passe2 ");
				    current_move.stopMove();
					move.add(current_move);
				    int x = current_move.getX_current();
				    int y = current_move.getY_current();
				    //current_move = new Move (x,y,'W');
				    current_move = new Move (x,y);
					current_move.setDestMove(x,y, vitesse);
					//current_move.setType('W');
					possede_ballon =true;
					destinataire_passe =false;
				}
				else{
					System.out.println(id + "Dest passe3 " +mon_equipe.getMatch().getBallonCurentMove().getX_dest() + " / " + mon_equipe.getMatch().getBallonCurentMove().getY_dest() );
					current_move.setDestMove(mon_equipe.getMatch().getBallonCurentMove().getX_dest(),mon_equipe.getMatch().getBallonCurentMove().getY_dest(), vitesse);
				}
				
			}
			
			return;
		}
		
		if (possede_ballon){
			System.out.println(id + " ballon");

			deplacement_avec_ballon(vitesse);
		}
		else{
			System.out.println(id + " offensif");
			deplacement_sans_ballon_offensif(vitesse);
		}
		
		
//			if (mon_equipe.getMatch().isFirstmove()){
//				int closer = getCloserEquipier();
//				//System.out.println("Passe de " + id + " à "  + mon_equipe.getJoueur().get(closer).getId() );
//				int x =mon_equipe.getJoueur().get(closer).getX() + mon_equipe.getJoueur().get(closer).decal_x;
//				int y = mon_equipe.getJoueur().get(closer).getY() + mon_equipe.getJoueur().get(closer).decal_y;
//				mon_equipe.getJoueur().get(closer).programme_deplacement(x, y, vitesse);
//				//mon_equipe.destinatairePasse = closer;
//				mon_equipe.getMatch().programme_deplacement(x, y, 100);
//				this.setPossede_ballon(false);
//				//mon_equipe.setPossede_ballon(false);
//				mon_equipe.getMatch().setChangement(true);
//				//System.out.println(" #### CHANGEMENT #######");
//				deplacement_sans_ballon(vitesse);
//				mon_equipe.getMatch().setFirstmove(false);
//
//			}
//			else{
//				//if (has_interacted_with_ball){
//				if(true){
//					deplacement_sans_ballon(vitesse);
//				}
//				else
//					deplacement_avec_ballon(vitesse);
//			}			
//		}
//		else{
//			//if (dest){
//			if(true){
//				//System.out.println("Deplacement vers le  ballon de " + x +"," + y + " vers " + mon_equipe.getMatch().getX_balle() +"," +mon_equipe.getMatch().getY_balle());	
//				programme_deplacement(mon_equipe.getMatch().getX_balle(), mon_equipe.getMatch().getY_balle(), vitesse);
//			}
//			else
//				deplacement_sans_ballon(vitesse);
//			//mouvement_defense();
//		}
	}


	private void deplacement_avec_ballon(int vitesse){

		
		//System.out.println("Attaque avec Ballon " + obstacle + " " + x + "," + y);
		//System.out.println("x >: "+ (playMatch.max_x - (playMatch.mid_x/2)) + " y > " + (playMatch.min_y + (playMatch.mid_y/2)) + " < " + (playMatch.max_y - (playMatch.mid_y/2)));
		if (isPositiondeTir()){
			tirauBut();
			System.out.println(id + " TAB");
		}
		else{
			if (doit_faire_une_passe()){
				System.out.println(id + " passe");
				passeBalle(vitesse);
			}
			else{
				System.out.println(id + " conduite");
				conduiteBalle(vitesse);
			}
		}
	}

	public void finishMove() {
		move.add(current_move);
		current_move = new Move (current_move.getX_dest(),current_move.getY_dest());
	}
	
	private boolean doit_faire_une_passe(){
		int obstacle= getJoueurAdverseDevant();
		
		return false;//TODO
		
//		if (obstacle >= 2){
//			return true;
//		}
//		else{
//			return false;
//		}
	}
	
	private void conduiteBalle(int vitesse) {
		//System.out.println("Conduite");

		//int x = current_move.getX_orig() +50;
		//int y = current_move.getY_orig();
		//int x = current_move.getX_dest() +50;
		//int y = current_move.getY_dest();
		int x = current_move.getX_current() +50;
		int y = current_move.getY_current();
		current_move.setDestMove(x,y, vitesse/2);
		//move.add(current_move);
		finishMove();
		mon_equipe.getMatch().getBallonCurentMove().setDestMove(x, y, vitesse/2);
		mon_equipe.getMatch().finishMoveBallon();
		
		
//		int max_x = getCloserAdversePlayer_X();
//		int y_=0;
//		int y_decal =(int)( Math.random() * (1 + (50 - tactique/2)));
//		double piece =  Math.random() ;
////		if (piece > 0.5){
////			y_=y_ori-y_decal;
////		}
////		else{
////			y_=y_ori+y_decal;
////		}
//		programme_deplacement(max_x,y_,vitesse);
//		mon_equipe.getMatch().programme_deplacement(max_x, y_, vitesse);
	}

	private void passeBalle(int vitesse) {

		int index_receveur = selection_receveur();
		if (index_receveur != -1){
			System.out.println("Passe de " + id + " à "  + mon_equipe.getJoueur().get(index_receveur).getId() );
			//int x =mon_equipe.getJoueur().get(index_receveur).getX() + mon_equipe.getJoueur().get(index_receveur).decal_x;
			//int y = mon_equipe.getJoueur().get(index_receveur).getY() + mon_equipe.getJoueur().get(index_receveur).decal_y;

			//float dt =  mon_equipe.getJoueur().get(index_receveur).tps_effectif + 1;

			int x =(int) (mon_equipe.getJoueur().get(index_receveur).x_tactique +  mon_equipe.getJoueur().get(index_receveur).getCurentMove().getOffsetX());
			int y = (int) (mon_equipe.getJoueur().get(index_receveur).y_tactique + mon_equipe.getJoueur().get(index_receveur).getCurentMove().getOffsetY());


			//mon_equipe.getJoueur().get(index_receveur).programme_deplacement(x, y, vitesse);
			//mon_equipe.getJoueur().get(index_receveur).nechangerien = true;
			
			//mon_equipe.destinatairePasse = index_receveur;
			mon_equipe.getMatch().programme_deplacement(x, y, 100);
			this.setPossede_ballon(false);
			//mon_equipe.setPossede_ballon(false);
			mon_equipe.getMatch().setChangement(true);
			//System.out.println(" #### CHANGEMENT #######");
			//deplacement_sans_ballon(vitesse);
			pause(10);
			}
		else{
			conduiteBalle(vitesse);
		}
	}

	public Move getCurentMove() {
		return current_move;
	}
	private void pause(int i) {
		//enpause =true;
		tps_attente = i;
		//move.add(new Move (0,0,i/10,'W'));
		//System.out.println("------>Enregistre Pause Joueur de " + i);
	}
	
	private int selection_receveur() {
		int x_max=0;
		int index = -1;
		int i=0;
		for (Joueur joueur : mon_equipe.getJoueur()){
			if( joueur.getId() != this.id){
				//System.out.println(i + " demarque " + joueur.isDemarque() + " eloigne "+ !joueur.DansLeCercle(x,y,50) + " devant " + !joueur.isDevant(x_max));
				if (joueur.isDemarque() && !joueur.DansLeCercle(x_tactique,y_tactique,50)){
					if(index == -1 || !joueur.isDevant(x_max)){
						x_max = joueur.getX();
						index = i; 
					}
				}
			}
			i++;
		}
		return index;
	}


	private void deplacement_sans_ballon_offensif(int vitesse){
		
		int x = current_move.getX_orig() +100;
		int y = current_move.getY_orig() +10;
		
		//current_move.setDestMove(x,y, vitesse);
		
		
//		int y_;
//		int x_ =  x_ori;
//		int x_decal;
//			int y_decal =(int) (50+  (Math.random() * (1 + ( tactique/2))));
//			if (y_tactique > playMatch.mid_y){
//				y_=y_ori+y_decal;
//			}
//			else{
//				y_=y_ori-y_decal;
//			}			
//			if ( x_tactique < x_ori){
//				x_ = x_ori;
//			}
//			else{
//				if (mon_equipe.isBallonDansleCampsAdverse()){
//					//System.out.println("OFF off");
//					x_decal = mon_equipe.getMatch().getX_balle() - (playMatch.mid_x );
//					x_=(int) ( x_ori + x_decal + Math.random()* (playMatch.mid_x/2))  ;
//				}
//				else{
//					//System.out.println("OFF def");
//					//if (x - (playMatch.mid_x /2) >mon_equipe.getMatch().getX_balle())
//					x_=(int) ( x_ori  + Math.random()* (playMatch.mid_x/2))  ;
//				}
//			}
//		}
//		y_= corrige_deplacement(x_,y_);
//		//System.out.println("Deplacement sans ballon de " + x +"," + y + " vers " +x_ + "," + y_);	
//
//		programme_deplacement(x_, y_, vitesse);

	}
	
	
	private void deplacement_sans_ballon_defensif(int vitesse){
//		int y_decal =(int) (Math.random() * (1 + (50 - tactique/2)));
//		if (mon_equipe.getMatch().getY_balle()> playMatch.mid_y){
//			y_=y_ori+y_decal;
//		}
//		else{
//			y_=y_ori-y_decal;
//		}
//		if(x_tactique > x_ori){
//			x_ = x_ori;
//		}
//		else {
//			if (mon_equipe.isBallonDansleCampsAdverse()){
//				x_decal = mon_equipe.getMatch().getX_balle() - (playMatch.mid_x );
//				x_=(int) ( x_ori + x_decal);
//			}
//			else{
//				x_decal = mon_equipe.getMatch().getX_balle() - (playMatch.mid_x );
//				x_= x_ori + x_decal;
//			}
//		}			
	}


	/**
	 * 
	 * 
	 * 
	 * ******************************************** UTILITAIRE DE JEU **********************************************
	 * 
	 * 
	 * **/
	private int getCloserEquipier() {
		int diff=800;
		int index=0;
		int i=0;
		for (Joueur joueur : mon_equipe.getJoueur()){
			if(joueur.DansLeCercle(x_tactique,y_tactique, diff) && joueur.getId()!= this.id){
				index = i;
				diff = Math.abs(joueur.getX() - x_tactique);
			}
			i++;
		}
		return index;
	}

	private int getCloserAdversePlayer_X() {
		int diff=800;
		int x_return=playMatch.max_x;
		for (Joueur joueur : adversaire.getJoueur()){
			if(isDevant(joueur.getX()) && joueur.DansLeCercle(x_tactique,y_tactique, diff)){
				x_return = joueur.getX() ;
				diff = Math.abs(joueur.getX() - x_tactique);
			}
		}
		return x_return;
	}


	private boolean isDemarque() {//TODO
		boolean isdemarque = true;
		for (Joueur joueur : adversaire.getJoueur()){
			//isdemarque = isdemarque || !this.DansLeCercle(joueur.getX(),joueur.getY(), 15);
			//if (this.DansLeCercle(joueur.getX(),joueur.getY(), 30))
			if (joueur.DansLeCercle(this.getCurentMove().getX_current(),this.getCurentMove().getY_current(), 50))
				isdemarque = false;					
		}
		return isdemarque;
	}

	private int getJoueurAdverseDevant() {//TODO abstract
		int count =0;
		for (Joueur joueur : adversaire.getJoueur()){
			if(isDevant(joueur.getX()) && this.DansLeCercle(joueur.getX(),joueur.getY(), 100 )){
				count ++;
			}
		}
		//System.out.println("Count : " + count);
		return count;
	}


	
	public int calculDistance (int x, int y){//TODO
		int distance = 0;
		int offsetX = getCurentMove().getX_current() - x;
		int offsetY = getCurentMove().getY_current() - y;
		distance = (int) Math.sqrt((offsetX * offsetX) + (offsetY * offsetY) );		
		return distance;
	}
	
	
	/**
	 * renvoie false si param x,y sont en dehors du cercle de rayon dist et de centre this x,y 
	 * renvoie true si param x,y sont dans le cercle de rayon dist et de centre this x,y
	 * @param x_tactique
	 * @param y
	 * @return
	 */
	public boolean DansLeCercle(int x_centre, int y_centre,int rayon) {
		int diffx = Math.abs(this.x_tactique - x_centre);
		int diffy = Math.abs(this.y_tactique - y_centre);
		if (diffx < rayon && diffy < rayon)
			return true;
		else
			return false;
	}


	/**
	 * Pour eviter 2 joueurs au même endroit
	 * @param x_
	 * @param y_
	 * @return
	 */
	private int corrige_deplacement(int x_, int y_) {
		boolean sameplace=false;
		int y_return = y_;
		for (Joueur joueur:mon_equipe.getJoueur()){
			if (joueur.id != this.id){
				sameplace = DansLeCercle(joueur.getCurentMove().getX_current(), joueur.getCurentMove().getY_current(), 40);
			}
		}
		if (sameplace){
			//System.out.println("Correction de Y");
			y_return = y_tactique+1;
		//	changement =true;
		}
		return y_return;
	}

	private int checkY(int y) {
		if (y < playMatch.min_y)
			return playMatch.min_y;
		if (y > playMatch.max_y)
			return playMatch.max_y;
		return y;
	}

	private int checkX(int x) {
		if (x < playMatch.min_x)
			return playMatch.min_x;
		if (x > playMatch.max_x)
			return playMatch.max_x;
		return x;
	}

	private float CalculNextMove(int vitesse) {
//		int diffx = x_dest -x_tactique;
//		int diffy = y_dest-y_tactique;
//		int dist = (int) Math.sqrt((diffx*diffx)+(diffy*diffy));
//
//		// Une vitesse de 0 à 100.
//		//à 100 on fait 600 pixel en 10 secondes (10 000 ms)
//		// on fait 100 pixel en 1,6 seconde
//		double pix_par_seconde = vitesse * 0.6;
//		int prev_mov = next_move;
//		float tps = (float) (dist / pix_par_seconde );
//		next_move += tps*10;
//
//		if (tps != 0){
//			decal_x = (int) (diffx / (tps*10));
//			decal_y = (int) (diffy / (tps*10));
//			tps_programme = tps * 10;
//		}
//		else{
//			decal_x = diffx;
//			decal_y = diffy;
//			tps_programme = 1;
//		}
//
//		if (next_move - prev_mov > 60000){
//			//System.out.println("ERRRRRREEEEEEUUUUURR " + prev_mov + " " + next_move + " " + dist + " " + vitesse);
//		}
//		return tps_programme;
		
		return 0;
	}

	private void programme_deplacement(int x, int y,int vitesse) {
//		if (tps_effectif >0){
//			move.add(new Move (this.x_tactique,this.y_tactique,tps_effectif/10,'M'));
//			//System.out.println("-----> Enregistre Deplacement Joueur vers " + this.x + "," + this.y + " en " + tps_effectif + "(" + tps_programme + ")");
//			if (enpause){
//				if (tps_pause > 0){
//					move.add(new Move (0,0,tps_pause/10,'W')); //faux ça!
//					//System.out.println("------>Enregistre Pause Joueur de " + tps_pause);
//				}
//				enpause=false;
//			}
//		}
//		x_dest=x;
//		y_dest=y;
//		x_dest = checkX(x_dest);
//		y_dest = checkY(y_dest);
//		float tps = CalculNextMove(vitesse);
//		tps_effectif = 0;
//		tps_pause = 0;
//		//move.add(new Move (x,y,tps,'M'));
//		//System.out.println("Deplacement Joueur programme de " + x_prec +"," + y_prec +" vers " + x + "," + y + " en " + tps_programme);
//		x_prec = x_dest;
//		y_prec= y_dest;

	}

	private void avance(boolean closer){
//		//System.out.println("Avance DEBUT  Joueur "+ x +"," + y + " != " + x_dest + " tps_effectif " + tps_effectif + " tps_programme " + tps_programme);
//
//		int vitesse = (int)(50 + Math.random() * (100 + this.vitesse/2) );
//		
//		if (enpause){
//			tps_pause++;
//			if (tps_attente != -1){
//				if(tps_pause > tps_attente){
//					tps_attente = -1;
//					enpause = false; 
//					programme_deplacement(x_tactique, y_tactique, vitesse);
//					tps_pause = 0;
//				}
//			}
//			else{
//				enpause = false;
//			}
//			//System.out.println("Avance Joueur en pause, tps_pause : " + tps_pause + " / tps_attente : " + tps_attente);
//
//			return;
//		}
//		
//		if (closer && !mon_equipe.getMatch().isFirstmove()){
//			if(DansLeCercle(mon_equipe.getMatch().getX_balle(), mon_equipe.getMatch().getY_balle(), 20) ){
//				if(possede_ballon){
//					if(tps_effectif <= tps_programme){
//						x_tactique+=decal_x;
//						y_tactique+=decal_y;
//						x_tactique = checkX(x_tactique);
//						y_tactique = checkY(y_tactique);
//						//	//System.out.println("Avance Joueur "+ x + "," +y);
//						tps_effectif++;
//					}
//					else{
//						tps_pause++;
//						enpause = true;
//					}			
//				}
//				else{
//					setPossede_ballon(mon_equipe.setPossede_ballon(true));
//					adversaire.setPossede_ballon(false);
//					mon_equipe.getMatch().setChangement(true);
//					//System.out.println(" #### CHANGEMENT #######");
//					//mon_equipe.destinatairePasse = -1;
//					programme_deplacement(x_tactique, y_tactique, vitesse);
//					mon_equipe.getMatch().programme_deplacement(x_tactique, y_tactique, 100);
//					pause(5);
//					//mouvement_attaque(false);
//
//				}
//			}
//			else{
//				if(tps_effectif <= tps_programme){
//					x_tactique+=decal_x;
//					y_tactique+=decal_y;
//					x_tactique = checkX(x_tactique);
//					y_tactique = checkY(y_tactique);
//					//	//System.out.println("Avance Joueur "+ x + "," +y);
//					tps_effectif++;
//				}
//				else{
//					tps_pause++;
//					enpause = true;
//				}
//			}
//			//System.out.println("Avance Joueur closer");
//			return;
//		}
//		
//		if (mon_equipe.getMatch().isFirstmove()){
//			if(tps_effectif <= tps_programme){
//				x_tactique+=decal_x;
//				y_tactique+=decal_y;
//				x_tactique = checkX(x_tactique);
//				y_tactique = checkY(y_tactique);
//				//	//System.out.println("Avance Joueur "+ x + "," +y);
//				tps_effectif++;
//			}
//			else{
//				tps_pause++;
//				enpause = true;
//			}
//			//System.out.println("Avance Joueur FirstMove");
//			return;
//		}
//		
//		if(DansLeCercle(mon_equipe.getMatch().getX_balle(), mon_equipe.getMatch().getY_balle(), 50)){		
//			if(DansLeCercle(mon_equipe.getMatch().getX_balle(), mon_equipe.getMatch().getY_balle(), 20) ){
//				if(possede_ballon){
//					if(tps_effectif <= tps_programme){
//						x_tactique+=decal_x;
//						y_tactique+=decal_y;
//						x_tactique = checkX(x_tactique);
//						y_tactique = checkY(y_tactique);
//						//	//System.out.println("Avance Joueur "+ x + "," +y);
//						tps_effectif++;
//					}
//					else{
//						tps_pause++;
//						enpause = true;
//					}			
//				}
//				else{
//					setPossede_ballon(mon_equipe.setPossede_ballon(true));
//					//mon_equipe.setPossede_ballon(true);
//					adversaire.setPossede_ballon(false);
//					mon_equipe.getMatch().setChangement(true);
//					//System.out.println(" #### CHANGEMENT #######");
//					//mon_equipe.destinatairePasse = -1;
//					programme_deplacement(x_tactique, y_tactique, vitesse);
//					mon_equipe.getMatch().programme_deplacement(x_tactique, y_tactique, 100);
//					//mouvement_attaque(false);
//					pause(5);
//				}
//			}
//			else{ // entre 20 et 50
//				if(possede_ballon){
//					if(tps_effectif <= tps_programme){
//						x_tactique+=decal_x;
//						y_tactique+=decal_y;
//						x_tactique = checkX(x_tactique);
//						y_tactique = checkY(y_tactique);
//						//	//System.out.println("Avance Joueur "+ x + "," +y);
//						tps_effectif++;
//
//					}
//					else{
//						tps_pause++;
//						enpause = true;
//					}
//				}
//				else{
//					//System.out.println("Deplacement vers le  ballon de " + x +"," + y + " vers " + mon_equipe.getMatch().getX_balle() +"," +mon_equipe.getMatch().getY_balle());	
//					programme_deplacement(mon_equipe.getMatch().getX_balle(), mon_equipe.getMatch().getY_balle(), vitesse);
//				}				
//			}
//		}
//		else{// au dela de 50
//			if(tps_effectif <= tps_programme){
//				x_tactique+=decal_x;
//				y_tactique+=decal_y;
//				x_tactique = checkX(x_tactique);
//				y_tactique = checkY(y_tactique);
//				//	//System.out.println("Avance Joueur "+ x + "," +y);
//				tps_effectif++;
//			}
//			else{
//				tps_pause++;
//				enpause = true;
//			}
//		}
		//System.out.println("Avance Joueur  ");

	}

	/*
	 * Verifie si le param x est devant le this. x dans le sens du jeux
	 */
	public abstract boolean isDevant(int x);

	protected abstract boolean isPositiondeTir();

	protected abstract void tirauBut();


	
	
	
	
	
	
	
	
	
	
	/******************* ACCESSOR *****************/
	
			/**
			 * @return the x
			 */
			public int getX() {
				return x_tactique;
			}
			/**
			 * @param x the x to set
			 */
			public void setX(int x) {
				this.x_tactique = x;
			}
			/**
			 * @return the y
			 */
			public int getY() {
				return y_tactique;
			}
			/**
			 * @param y the y to set
			 */
			public void setY(int y) {
				this.y_tactique = y;
			}
			/**
			 * @return the age
			 */
			public int getAge() {
				return age;
			}
			/**
			 * @param age the age to set
			 */
			public void setAge(int age) {
				this.age = age;
			}
			/**
			 * @return the xp
			 */
			public int getXp() {
				return xp;
			}
			/**
			 * @param xp the xp to set
			 */
			public void setXp(int xp) {
				this.xp = xp;
			}
			/**
			 * @return the talent
			 */
			public int getTalent() {
				return talent;
			}
			/**
			 * @param talent the talent to set
			 */
			public void setTalent(int talent) {
				this.talent = talent;
			}
			/**
			 * @return the tactique
			 */
			public int getTactique() {
				return tactique;
			}
			/**
			 * @param tactique the tactique to set
			 */
			public void setTactique(int tactique) {
				this.tactique = tactique;
			}
			/**
			 * @return the physique
			 */
			public int getPhysique() {
				return physique;
			}
			/**
			 * @param physique the physique to set
			 */
			public void setPhysique(int physique) {
				this.physique = physique;
			}
			/**
			 * @return the moral
			 */
			public int getMoral() {
				return moral;
			}
			/**
			 * @param moral the moral to set
			 */
			public void setMoral(int moral) {
				this.moral = moral;
			}
			/**
			 * @return the blessure
			 */
			public int getBlessure() {
				return blessure;
			}
			/**
			 * @param blessure the blessure to set
			 */
			public void setBlessure(int blessure) {
				this.blessure = blessure;
			}
			/**
			 * @return the cond
			 */
			public int getCond() {
				return cond;
			}
			/**
			 * @param cond the cond to set
			 */
			public void setCond(int cond) {
				this.cond = cond;
			}
			/**
			 * @return the gch
			 */
			public int getGch() {
				return gch;
			}
			/**
			 * @param gch the gch to set
			 */
			public void setGch(int gch) {
				this.gch = gch;
			}
			/**
			 * @return the ctr
			 */
			public int getCtr() {
				return ctr;
			}
			/**
			 * @param ctr the ctr to set
			 */
			public void setCtr(int ctr) {
				this.ctr = ctr;
			}
			/**
			 * @return the drt
			 */
			public int getDrt() {
				return drt;
			}
			/**
			 * @param drt the drt to set
			 */
			public void setDrt(int drt) {
				this.drt = drt;
			}
			/**
			 * @return the off
			 */
			public int getOff() {
				return off;
			}
			/**
			 * @param off the off to set
			 */
			public void setOff(int off) {
				this.off = off;
			}
			/**
			 * @return the def
			 */
			public int getDef() {
				return def;
			}
			/**
			 * @param def the def to set
			 */
			public void setDef(int def) {
				this.def = def;
			}
			/**
			 * @return the mental
			 */
			public int getMental() {
				return mental;
			}
			/**
			 * @param mental the mental to set
			 */
			public void setMental(int mental) {
				this.mental = mental;
			}
			/**
			 * @return the vitesse
			 */
			public int getVitesse() {
				return vitesse;
			}
			/**
			 * @param vitesse the vitesse to set
			 */
			public void setVitesse(int vitesse) {
				this.vitesse = vitesse;
			}
			/**
			 * @return the technique
			 */
			public int getTechnique() {
				return technique;
			}
			/**
			 * @param technique the technique to set
			 */
			public void setTechnique(int technique) {
				this.technique = technique;
			}
			public int getId() {
				return id;
			}
			public void setId(int id) {
				this.id = id;
			}
	
			
			/**
			 * @return the gardien
			 */
			public boolean isGardien() {
				return gardien;
			}
			/**
			 * @param gardien the gardien to set
			 */
			public void setGardien(boolean gardien) {
				this.gardien = gardien;
			}
			/**
			 * @return the possede_ballon
			 */
			public boolean isPossede_ballon() {
				return possede_ballon;
			}
			/**
			 * @param possede_ballon the possede_ballon to set
			 */
			public void setPossede_ballon(boolean possede_ballon) {
				this.possede_ballon = possede_ballon;
			}
			/**
			 * @return the adversaire
			 */
			public Equipe getAdversaire() {
				return adversaire;
			}
			/**
			 * @param adversaire the adversaire to set
			 */
			public void setAdversaire(Equipe adversaire) {
				this.adversaire = adversaire;
			}
			/**
			 * @return the moves
			 */
			public ArrayList<Move> getMove() {
				return move;
			}
			/**
			 * @param move the moves to set
			 */
			public void setMove(ArrayList<Move> move) {
				this.move = move;
			}
			public boolean getChangement() {
				return changement;
			}
			public void checkChangement() {
				current_move.avance();
				changement =  current_move.isFinish();
				if (changement){
					move.add(current_move);
					//current_move = new Move(current_move.getX_dest(), current_move.getY_dest());
					current_move = new Move(current_move.getX_current(), current_move.getY_current());
					if (possede_ballon){
					//ballon
						mon_equipe.getMatch().finishMoveBallon();
					}
					
				}
				
			}
			
			/**
			 * Le joueur engo1 fait la passe à engo2
			 */
			public void jouerCoupEnvoi() {
				int x_dest = mon_equipe.engo2.getCurentMove().getX_dest();
				int y_dest = mon_equipe.engo2.getCurentMove().getY_dest();
				mon_equipe.engo2.destinataire_passe=true;
				this.possede_ballon = false;
				mon_equipe.getMatch().finishMoveBallon();
				mon_equipe.getMatch().getBallonCurentMove().setDestMove(x_dest, y_dest, 75);
				
				
	
			}
			
}
