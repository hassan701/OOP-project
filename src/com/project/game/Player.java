package com.project.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Player{
	public static int Score;
	public static int Moves=20;
	public static int Goal=80;
	private double score;
	
	


	public void update() {
		score= ((double)getScore()/Goal);
		setScore(Game.Clamp(getScore(), 0, Goal));
		
	}
	
	
	public void render(Graphics g) {

		Font fnt = new Font("arial",1,40);
		g.setFont(fnt);
		g.setColor(Color.black);
		g.drawString("Moves:", 700, 100);
		g.drawString(Integer.toString(Moves), 850, 100);
		g.setColor(Color.gray);
		g.fillRect(700, 15, 200, 32);
		g.setColor(Color.green);
		g.fillRect(700, 15, (int) ((score)*200), 32);
		g.setColor(Color.white);
		g.drawRect(700, 15, 200, 32);
		
	}



	public void setMoves(int moves) {
		Moves = moves;
	}


	public int getMoves() {
		return Moves;
	}


	public int getScore() {
		return Score;
	}


	public void setScore(int score) {
		Score = score;
	}


	public void setGoal(int goal) {
		Goal =goal;
		
	}


	public int getGoal() {
		return Goal;
	}
	
}
