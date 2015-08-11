package com.htmmft.match.visu;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import com.htmmft.match.Move;

public class BallonDrawable {

	private int x;
	private int y;
	private ArrayList <Move> move;
	
	public BallonDrawable(int x, int y){
		this.x=x;
		this.y=y;

	}
	public BallonDrawable(ArrayList<Move> move_ballon) {
		move=move_ballon;
		
		//System.out.println(move_ballon.size());
		move = CalculMoves(move_ballon);
		//System.out.println(move.size());
		if (this.move.size()>0)
			System.out.println("Ballon Total secondes : " + this.move.get(move.size()-1).getTime());
		else
			System.out.println("Ballon Total seconde : 0");
		
	}
	public void initdraw(Graphics g){
		draw(g,this.x,this.y);
	}
	
	public void paint(Graphics g, int x, int y){
		draw (g,x,y);
	}
	
	private void draw(Graphics g,int x,int y) {
		Color c = g.getColor();
		g.setColor(Color.black);
		g.fillOval(x, y, 10, 10);
		g.setColor(c);
	}
	public void paint(Graphics g, int time) {
		
		Move draw_move = null ;
		int i =0;
		float move_total_time=0;
		if (this.move.size() > 0){
			
		do{
			i++;
			if (i < this.move.size())
				move_total_time =  (this.move.get(i).getTime()*10);	
			else
				break;
				
		}while(move_total_time < time );
		if (i < this.move.size())
			draw_move = this.move.get(i);
		else
			draw_move = this.move.get(move.size()-1);

		//draw(g, move.get(0).getX(),move.get(0).getY() );
//		Move draw_move = null ;
//		for (Move move :this.move){
//			draw_move = move;
//			float dizieme = (float) (time /10.0);
//			if (move.getTime() > dizieme ){
//				//System.out.println(move.getTime() + " / " + time + ":" + dizieme);
//				break;
//			}
//		}
		
		if (draw_move != null)
			draw(g, draw_move.getX_dest(),draw_move.getY_dest() );
			//System.out.println(draw_move.getX_dest() + ", " + draw_move.getY_dest());
		}
	}
		/**else
		
	}
	
	/**
	 * Expand the move list to get the animation effect
	 * @param move2
	 * @return
	 */
	private ArrayList<Move> CalculMoves(ArrayList<Move> move2) {
		ArrayList<Move> new_move = new ArrayList<Move>();
		float ttime = 0;
		for (int i=0 ;i< move2.size()-1;i++){
			Move last_move = move2.get(i);
			Move current_move = move2.get(i+1);
			if (current_move.getTime() == 0){
				ttime += 0.1;
				new_move.add(current_move);
			}
			else{
				ArrayList<Move> insert_move = CalculInsertMove(current_move,last_move,ttime);
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
	 * @param last_move 
	 * @param ttime
	 * @return
	 */
	private ArrayList<Move> CalculInsertMove(Move current_move, Move last_move, float ttime) {
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
			}
			else{
				int offsetX =   current_move.getX_dest() -last_move.getX_dest();
				int offsetY =   current_move.getY_dest() - last_move.getY_dest();
				int offsetX_per_move = (int) (offsetX / (current_move.getTime()*1));
				int offsetY_per_move = (int) (offsetY / (current_move.getTime()*1));
				for (float i=0; i<= current_move.getTime();i = (float) (i + 0.1)){
					
					insert_moves.add(new Move((int) (last_move.getX_dest() + (offsetX_per_move *(i) )) ,(int)(last_move.getY_dest()+ (offsetY_per_move *(i) )),(ttime + i),'M'));	
				}
			}
		}
		
		return insert_moves;
	}

}
