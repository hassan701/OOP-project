package com.project.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Menu{
	private Player player;
	private int score;
	public Menu(Player player) {
		this.player = new Player();
		Audio.init();
		Audio.getMusic("Menu").play();
		Audio.getMusic("Game").stop();
	}
	
	public void render(Graphics g) {
		Font fnt = new Font("arial",1,50);
		Font fnt2 = new Font("arial",1,50);
		if(Game.gameState==GameState.Menu) {
		g.setFont(fnt);
		g.setColor(Color.blue);
		g.drawString("Candy Crush",350, 100);;
		
		
		g.setFont(fnt2);
		g.setColor(Color.green);
		g.drawString("New Game", 0, 300);;
		
		
		g.setColor(Color.red);
		g.drawString("Exit", 0, 500);;
		
		} else if(Game.gameState==GameState.End) {
			g.setFont(fnt);
			if(player.getScore()<player.getGoal()) {
				g.setColor(Color.red);
				g.drawString("Game Over",350, 100);;
				g.setFont(fnt2);
				g.setColor(Color.DARK_GRAY);
				g.drawString("You will get them next time", 0, 400);;
				g.drawString("Your Score was:", 0, 450);;
				g.setColor(Color.red);
				g.drawString(Integer.toString(player.getScore()), 400, 450);;
			}else if(player.getScore()>=player.getGoal()) {
				g.setColor(Color.green);
				g.drawString("Level Cleared",350, 100);;
				g.setFont(fnt2);
				g.setColor(Color.black);
				g.drawString("Your Score was:", 0, 350);;
				g.setColor(Color.green);
				g.drawString(Integer.toString(player.getScore()), 300, 450);;
				
			}
			
			g.setColor(Color.black);
			g.drawString("Go back to Level", 0, 500);;
			
		}else if(Game.gameState==GameState.Level) {
		g.setFont(fnt);
		g.setColor(Color.blue);
		g.drawString("Select Dificulty",290, 100);;
		g.setColor(Color.black);
		g.drawString("Easy", 400, 250);;
		
		g.drawString("Normal", 400, 375);;
		
		g.drawString("Hard",400, 500);;
		
		g.drawString("Back",0, 500);;
		}
		
		
	}

	public void update() {
		this.score= player.getScore();
	}

}
