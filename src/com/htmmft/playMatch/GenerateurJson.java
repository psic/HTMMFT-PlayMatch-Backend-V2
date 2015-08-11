package com.htmmft.playMatch;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.htmmft.match.Equipe;
import com.htmmft.match.Joueur;
import com.htmmft.match.Move;

public class GenerateurJson {
	private FileWriter file;
	private ArrayList<Move> move_ball;
	private Equipe equipe1;
	private Equipe equipe2;
	
	public GenerateurJson(FileWriter file, ArrayList<Move> move, Equipe equipe1, Equipe equipe2){
		this.file = file;
		this.move_ball = move;
		this.equipe1 = equipe1;
		this.equipe2 = equipe2;
	}
	
	public void GenerateJson() {
		String aecrire ="";
		float totsec=0;
		try {
			
			file.write("{\n");
			System.out.println("====================================début generation Json ================================");

			aecrire = "\"0\":\"";
			for (Move move : move_ball){
				if (move.getTime() != 0){
					if (move.getType() == 'M'){
						aecrire +="M" + move.getX_dest() + "," + move.getY_dest() +"," + move.getTime(); 
						totsec+=move.getTime();
					}
					else{
						if (move.getType() == 'W'){
							aecrire +="W" + move.getTime(); 
							totsec+=move.getTime();
						}
					}
				}
				else{
					aecrire +="M" + move.getX_dest() + "," + move.getY_dest();
				}
			}
			System.out.println("Ballon Total seconde : " + totsec);
			totsec=0;
			aecrire += "\",";
			file.write(aecrire);
			
			for (Joueur joueur : equipe1.getJoueur()){
				aecrire = "\n\"" + joueur.getId() + "\":\"";
				for(Move move : joueur.getMove()){
					if (move.getTime() != 0){
						if (move.getType() == 'M'){
							aecrire +="M" + move.getX_dest() + "," + move.getY_dest() +"," + move.getTime(); 
							totsec+=move.getTime();
						}
						else{
							if (move.getType() == 'W'){
								aecrire +="W" + move.getTime(); 
								totsec+=move.getTime();
							}
						}
					}
					else{
						aecrire +="M" + move.getX_dest() + "," + move.getY_dest();
					}
				}
				System.out.println("E1 Total seconde : " + totsec);
				totsec=0;
				aecrire += "\",";
				file.write(aecrire);
			}
			int i= 0;
			for (Joueur joueur : equipe2.getJoueur()){
				aecrire = "\n\"" + joueur.getId() + "\":\"";
				for(Move move : joueur.getMove()){
					if (move.getTime() != 0){
						if (move.getType() == 'M'){
							aecrire +="M" + move.getX_dest() + "," + move.getY_dest() +"," + move.getTime(); 
							totsec+=move.getTime();
						}
						else{
							if (move.getType() == 'W'){
								aecrire +="W" + move.getTime(); 
								totsec+=move.getTime();
							}
						}
					}
					else{
						aecrire +="M" + move.getX_dest() + "," + move.getY_dest();
					}
				}
				i++;
				System.out.println("E2 Total seconde : " + totsec);
				totsec=0;
				aecrire += "\"";
				if(i< equipe2.getJoueur().size())
					aecrire += ",";
				file.write(aecrire);
			}
			System.out.println("====================================fin generation Json ================================");
			file.write("\n}");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
