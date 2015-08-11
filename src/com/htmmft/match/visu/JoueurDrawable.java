package com.htmmft.match.visu;	

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import com.htmmft.match.Joueur;
import com.htmmft.match.Move;

public class JoueurDrawable {
	private int x;
	private int y;
	private Color col;
	private String num;
	private ArrayList<Move> move;

	public JoueurDrawable(int x, int y, Color c, int num){
		this.x=x;
		this.y=y;
		this.col=c;
		this.num = String.valueOf(num);
	}
	
	public JoueurDrawable(Joueur joueur, Color red, int i) {
		col =red;
		num = String.valueOf(i);
		//System.out.println(joueur.getMove().size());
		if (i == 10){
			col = Color.GREEN;
		}
		move = CalculMoves(joueur.getMove());
		//System.out.println(move.size());
		if (this.move.size()>0)
			System.out.println("Joueur Total seconde : " + this.move.get(move.size()-1).getTime());
		else
			System.out.println("Joueur Total seconde : 00000");
		//CalculTotalSec();
	}

	
	/**
	 * Expand the move list to get the animation effect
	 * @param move2
	 * @return
	 */
	private ArrayList<Move> CalculMoves(ArrayList<Move> move2) {
		ArrayList<Move> new_move = new ArrayList<Move>();
		float ttime = 0;
		if (move2.size() == 1){
			return move2;
		}
		for (int i=0 ;i< move2.size()-1;i++){
			Move current_move = move2.get(i);
			Move next_move = move2.get(i+1);
			if (current_move.getTime() == 0){
				ttime += 0.1;
				new_move.add(current_move);
				//ttime += current_move.getTime();
			}
			else{
				ArrayList<Move> insert_move = CalculInsertMove(current_move,next_move,ttime);
				//System.out.println(insert_move.size());
				ttime += current_move.getTime();
				for(Move current_insert_move: insert_move){
					new_move.add(current_insert_move);
				}
			}
		}		
		return new_move;
	}

	/**
	 * Calcul all the move for animate the current move
	 * @param current_move
	 * @param next_move 
	 * @param ttime
	 * @return
	 */
	private ArrayList<Move> CalculInsertMove(Move current_move, Move next_move, float ttime) {
		ArrayList<Move> insert_moves= new ArrayList<Move>();
		
		if (current_move.getType() == 'W'){
			if (current_move.getTime() == 0){
				insert_moves.add(new Move(current_move.getX_dest(),current_move.getY_dest(),ttime ,'W'));
			}
			else{
			for (float i=0; i< current_move.getTime();i = (float) (i + 0.1))
				insert_moves.add(new Move(current_move.getX_dest(),current_move.getY_dest(),ttime ,'W'));
			}
		}
		if (current_move.getType() == 'M'){
			if (current_move.getTime() == 0){
				insert_moves.add(new Move(current_move.getX_dest(),current_move.getY_dest(),ttime ,'M'));	
				System.out.println(num);
			}
			else{
				int offsetX =   next_move.getX_dest() - current_move.getX_dest();
				int offsetY =   next_move.getY_dest() - current_move.getY_dest();
				int offsetX_per_move = (int) (offsetX / (current_move.getTime()*1));
				int offsetY_per_move = (int) (offsetY / (current_move.getTime()*1));
				for (float i=0; i< current_move.getTime();i = (float) (i + 0.1)){
					
					insert_moves.add(new Move((int) (current_move.getX_dest() + (offsetX_per_move *(i+1) )) ,(int)(current_move.getY_dest()+ (offsetY_per_move *(i+1) )),(ttime + i),'M'));	
				}
			}
		}
		
		return insert_moves;
	}

	public void initdraw(Graphics g){
		draw(g,this.x,this.y);
	}
	
	public void paint(Graphics g, int x, int y){
		draw (g,x,y);
	}
	
	private void draw(Graphics g,int x,int y) {
		Color c = g.getColor();
		g.setColor(col);
		g.fillOval(x, y, 20, 20);
		g.setColor(Color.black);
		g.drawString(num, x+5, y+15);
		g.setColor(c);
	}

	public void paint(Graphics g, int time) {
		//draw(g, move.get(0).getX(),move.get(0).getY() );
		//System.out.println(time);
		Move draw_move = null ;
		int i =0;
		float move_total_time=0;
		if (this.move.size() > 1){
			
		do{
			i++;
			if (i< this.move.size()-1)
				move_total_time =  (this.move.get(i).getTime()*10);
			else
				break;
				
		}while(move_total_time < time);
	//	Move origin_move = this.move.get(i-1);
		if (i <= this.move.size())
			draw_move = this.move.get(i);
		else
			draw_move = this.move.get(move.size()-1);
//			Move origin_move = this.move.get(0);
//			float dizieme = (float) (time /10.0);
//			float total_time=0;
//			for (int i = 0; i < this.move.size()-1 ;i++){
//				origin_move = move.get(i);
//				draw_move = move.get(i+1);	
//				total_time += origin_move.getTime() * 10.0;
//				if (total_time > time ){
//					System.out.println(total_time + " / " + time + " :" + i );
//					break;
//				}
//			}
			
			if (draw_move != null){
			//	int time_move =  move_total_time -time;
			//	System.out.println(move_total_time + " / " + time + " :" +time_move + ":"+ i );
				
		//		int x = (int) (draw_move.getOffsetX_per_dizseconde() * time_move  + origin_move.getX_dest());
		//		int y = (int) (draw_move.getOffsetY_per_dizseconde() * time_move  + origin_move.getY_dest());
				if (draw_move.getType() == 'M')
					draw(g, draw_move.getX_dest(),draw_move.getY_dest() );
				else if(draw_move.getType() == 'W')
					draw(g, draw_move.getX_current(),draw_move.getY_current() );
				else
					draw(g, draw_move.getX_dest(),draw_move.getY_dest() );
		//		draw(g, x,y );
			}
		}
			else{
				if (move.size() ==1)
					draw(g, move.get(0).getX_dest(),move.get(0).getY_dest() );
				
			}
			
		
	}

}
