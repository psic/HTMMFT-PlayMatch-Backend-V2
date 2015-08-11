package com.htmmft.match;

import java.util.ArrayList;

import com.htmmft.playMatch.playMatch;

public class Equipe {
	private ArrayList<Joueur> joueurs;
	private int tactique_id;
	private boolean possede_ballon;
	private boolean sens; // true pour E1, false pour E2
	private Equipe adversaire;
	private Match match;
	public Joueur engo1 =null;
	public Joueur engo2 = null;
	
	public Equipe(int tac_id, boolean sens, Match match) {
		joueurs = new ArrayList<Joueur>();
		this.sens = sens;
		possede_ballon =sens;
		setTactique_id(tac_id);
		this.setMatch(match);
	}

	/**
	 * @return the tactique_id
	 */
	public int getTactique_id() {
		return tactique_id;
	}

	/**
	 * @param tactique_id the tactique_id to set
	 */
	public void setTactique_id(int tactique_id) {
		this.tactique_id = tactique_id;
	}

	public void addJoueur(Joueur joueur) {
		joueurs.add(joueur);

	}

	public void coupEnvoi() {
		if (sens){
			int maxX1 = joueurs.get(1).getX();
			int maxX2 = joueurs.get(1).getX();
			int indexmaxX1 = 1;
			int indexmaxX2 = 1;
			int index =0;

			for (Joueur joueur :joueurs){

				if (joueur.getX() > maxX1){
					maxX1 = joueur.getX();
					indexmaxX1 = index;
				}
				index++;
			}
			index = 0;
			for (Joueur joueur :joueurs){

				if (joueur.getX() > maxX2 && indexmaxX1 != index){
					maxX2 = joueur.getX();
					indexmaxX2 = index;
				}
				index++;
			}
			index = 0;
			for (Joueur joueur :joueurs){

				if (index != indexmaxX1 && indexmaxX2 != index)
					joueur.coupEnvoiE1();
				index++;
			}

			joueurs.get(indexmaxX1).coupEnvoiSpecial1();
			joueurs.get(indexmaxX2).coupEnvoiSpecial2();
			
			engo1=joueurs.get(indexmaxX1);
			engo2=joueurs.get(indexmaxX2);
		}
		else
		{
			for (Joueur joueur :joueurs){
				joueur.coupEnvoiE2();
			}	
		}
	}

	/**
	 * @return the sens
	 */
	public boolean isSens() {
		return sens;
	}

	/**
	 * @param sens the sens to set
	 */
	public void setSens(boolean sens) {
		this.sens = sens;
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
	public boolean setPossede_ballon(boolean possede_ballon) {
		this.possede_ballon = possede_ballon;
		if (possede_ballon){
			int trouve = 0;
			for(Joueur joueur : joueurs){
				if (joueur.isPossede_ballon())
					trouve ++;
			}
			/**if (trouve == 0){
				//System.out.println("ERROR : Pas de joueur avec le ballon");
				return false;
			}**/
			if (trouve >= 1){
				//System.out.println("ERROR : plusieur joueurs avec le baloon");
				return false;
			}
		}
		else{
			for(Joueur joueur : joueurs){
				joueur.setPossede_ballon(false);
			}
		}
		return true;
	}

	public void jouerMatch(int seconde) {

		for (Joueur joueur :joueurs){
			//Joueur joueur = joueurs.get(5);
		
						
			//System.out.println("	Changement : " + seconde + " Possede :" + possede_ballon );
			//if (joueur.getChangement() || getMatch().isChangement()){
				joueur.jouerMatch(seconde);
		//	}
			
		//	joueur.checkChangement();

		}
		
	}

	public void trouveGardien() {
		int x_gardien = joueurs.get(1).getX();
		int index_gardien =1;
		int index =0;
		for (Joueur joueur :joueurs){
			if (sens){
				if (x_gardien < joueur.getX()){
					x_gardien = joueur.getX();
					index_gardien = index;
				}
			}
			else{
				if (x_gardien > joueur.getX()){
					x_gardien = joueur.getX();
					index_gardien = index;
				}
			}
		}
		joueurs.get(index_gardien).setGardien(true);
	}

	public void setAdversaire(Equipe equipe) {
		adversaire = equipe;
		for (Joueur joueur : joueurs){
			joueur.setAdversaire(equipe);
		}
	}

	public ArrayList<Joueur> getJoueur(){
		return joueurs;
	}

	/**
	 * @return the match
	 */
	public Match getMatch() {
		return match;
	}

	/**
	 * @param match the match to set
	 */
	public void setMatch(Match match) {
		this.match = match;
	}
	
	public boolean isBallonDansleCampsAdverse(){
		if (sens)
			return (getMatch().getX_balle() > playMatch.mid_x);
		else
			return (getMatch().getX_balle() < playMatch.mid_x);
	}
	
	public int getCloserPlayerFromBall(){
		int diff=800;
		int index=0;
		int i=0;
		for (Joueur joueur : joueurs){
			if(joueur.DansLeCercle(getMatch().getX_balle(),getMatch().getY_balle(), diff)){
				index = i ;
				int diffx = getMatch().getX_balle() -joueur.getX();
				int diffy = getMatch().getY_balle() -joueur.getY();;
				diff = (int) Math.sqrt((diffx*diffx)+(diffy*diffy));
			}
			i++;
		}
		return index;
		
	}
	

	public void calculDistances() {
		calculDistanceCoequipiers();
		calculDistanceAdversaire();
		
	}

	private void calculDistanceAdversaire() {
		int i =0;
		for(Joueur j : joueurs){
			for (Joueur adv : adversaire.joueurs){
				j.distance_adversaire[i] = j.calculDistance(adv.getCurentMove().getX_current(), adv.getCurentMove().getY_current());
			}
			i++;
		}
	}
	
	private void calculDistanceCoequipiers() {
		int i =0;
		for(Joueur j : joueurs){
			for (Joueur coequipe : joueurs){
				j.distance_coequipier[i] = j.calculDistance(coequipe.getCurentMove().getX_current(), coequipe.getCurentMove().getY_current());
			}
			i++;
		}
	}

}
