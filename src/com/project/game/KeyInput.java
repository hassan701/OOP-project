package com.project.game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter{
	private Handler handler;
	
	public KeyInput(Handler handler) {
		this.handler = handler;
	}
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		GameObject temp;
		for(int i =0;i<handler.object.size();i++) {
			temp = handler.object.get(i);
			if(temp.id==ID.Candy) {
				
		}
			
		}
		if(key== KeyEvent.VK_P){
			if(Game.gameState==GameState.Game) {
			if(Game.Pause==false) Game.Pause=true;
			else Game.Pause = false;
			
		}
		}
		if(key== KeyEvent.VK_ESCAPE) System.exit(1);
		
	}
	
	


}
