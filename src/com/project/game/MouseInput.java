package com.project.game;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

public class MouseInput extends MouseAdapter {
	private Game game;
	private Handler handler;
	private Player player;
	Random rand = new Random();
	boolean mouseclicked= true;
	public static boolean updatemap= false;
	public static boolean dragging= true;
	private static int Imx;
	private static int Imy;
	public static int[][] Cmap= new int[8][8];
	
	public MouseInput(Handler handler, Player player) {
			this.handler= handler;
			this.player=player;
		}

	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		if(Game.gameState==GameState.Game) {
		if(!Game.Pause) {
		dragging= true;
		Imx =mx/(500/8);
		Imy = my/(500/8);
		Cmap = createClonemap();
		}
		}if(Game.gameState==GameState.Menu) {
			if(mouseOver(mx,my,0, 250, 250, 50)) {
				game.gameState=GameState.Level;
			}
			if(mouseOver(mx,my,0, 450, 200, 50)) {
				System.exit(1);
			}
		}
		if(Game.gameState==GameState.Level) {
			//easy
			if(mouseOver(mx,my,380, 215, 150, 50)) {
				Mapmaker();
				printmap(Game.map);
				player.setMoves(20);
				player.setGoal(80);
				player.setScore(0);
				Audio.getMusic("Menu").stop();
				Audio.getMusic("Game").play();
				Game.gameState=GameState.Game;
				
				for(int y=0;y<8;y++) {
					for(int x=0;x<8;x++) {
							handler.addObject(new Candy(x,y,ID.Candy,handler));
					}
				}
				player.setScore(0);
				
			}
			//normal
			if(mouseOver(mx,my,380, 335, 150, 50)) {
					Mapmaker();
					printmap(Game.map);
					player.setMoves(20);
					player.setGoal(150);
					player.setScore(0);
					Game.gameState=GameState.Game;
					
					for(int y=0;y<8;y++) {
						for(int x=0;x<8;x++) {
								handler.addObject(new Candy(x,y,ID.Candy,handler));
						}
					}
					Audio.getMusic("Menu").stop();
					Audio.getMusic("Game").play();

					player.setScore(0);
				
				
			}
			//hard
			if(mouseOver(mx,my,380, 455, 150, 50)) {
					Mapmaker();
					printmap(Game.map);
					player.setMoves(20);
					player.setScore(0);
					player.setGoal(500);
					Game.gameState=GameState.Game;
					
					for(int y=0;y<8;y++) {
						for(int x=0;x<8;x++) {
								handler.addObject(new Candy(x,y,ID.Candy,handler));
						}
					}
					Audio.getMusic("Menu").stop();
					Audio.getMusic("Game").play();

					player.setScore(0);
				
				
			}
			//back
			if(mouseOver(mx,my,0, 455, 150, 50)) {
				Game.gameState=GameState.Menu;
			}
		
		}
		
		
		if(Game.gameState==GameState.End) {

			if(mouseOver(mx,my,0, 450, 200, 50)) {
				Game.gameState=GameState.Level;
				
			}
		}
		
		e.consume();
	}
	
	
	private boolean mouseOver(int mx,int my,int x, int y, int w, int h) {
		if(mx>x&&mx<x+w) {
			if(my>y&&my<y+h) {
				return true;
			}return false;
		}return false;
	}
	
	public void mouseReleased(MouseEvent e) {
		if(Game.gameState==GameState.Game&&updatemap==true) {
		if(checkmap()==true) {
			Audio.getSound("nice").play();
			updatemap();player.setMoves(player.getMoves() - 1);
		}
		if(dragging==true) dragging= false;
		updatemap=false;
		}else if(Game.gameState==GameState.Menu) {
			
		}
		e.consume();
	}
	
	
	
	public void mouseDragged(MouseEvent e) {
		if(dragging=true) {
		int cx= e.getX()/(500/8);
		int cy= e.getY()/(500/8);
		GameObject temp;
		for(int i =0;i<handler.object.size();i++) {
			temp = handler.object.get(i);
			if(temp.id==ID.Candy&& Candy.updatemap==false) {
				if(cx<8&&cy<8&&cx>=0&&cy>=0)
					if(temp.getA()==Imx&&temp.getB()==Imy) {
							if(cx==Imx&&Math.abs(cy-(temp.getY()/(500/8)))==1) {
								int temy= temp.getY();
								int temx= temp.getX();
								temp.setY(cy*(500/8));
								collision(temp,temx,temy,cy);
							}
							if(cy==Imy&&Math.abs(cx-(temp.getX()/(500/8)))==1) {
								int temx= temp.getX();
								int temy= temp.getY();
								temp.setX(cx*(500/8));
								collision(temp,temx,temy,cx);
							}
					}
			}
		}
		updatemap=true;
	
	}
		e.consume();
	}
	
	
	public void collision(GameObject a,int oldx,int oldy,int old) {
		GameObject temp;
		for(int i =0;i<handler.object.size();i++) {
			temp = handler.object.get(i);
			if(temp.id==ID.Candy&&a.getAb()!=temp.getAb()) {
				if(a.getBounds().intersects(temp.getBounds())) {
					if(oldy==temp.getY()) {
						oldx = oldx/(500/8);
						temp.setX((oldx)*(500/8));
						int t = Cmap[temp.getY()/(500/8)][old];
						Cmap[temp.getY()/(500/8)][old] = Cmap[temp.getY()/(500/8)][oldx];
						Cmap[temp.getY()/(500/8)][oldx] = t;

					}
					if(oldx==temp.getX()) {
						oldy = oldy/(500/8);
						temp.setY(oldy*(500/8));
						int t = Cmap[old][temp.getX()/(500/8)];
						Cmap[old][a.getX()/(500/8)] = Cmap[oldy][temp.getX()/(500/8)];
						Cmap[oldy][temp.getX()/(500/8)] = t;
					}
				}
			}
		}
	}
	
	private boolean checkmap() {
		int varx= Cmap[0][0];
		int vary= Cmap[0][0];
		int countx =0;
		int county =0;
		int q=0;
		int p=0;
	
		for(int y=0;y<8;y++) {
			for(int x=0;x<8;x++) {
				if(Cmap[y][x]==varx && x>0) {
					countx++;
				}else {
					varx=Cmap[y][x];
					if(countx>=3) {
						if(x==0&&y>0) {
							q=8;
							p=y-1;
						}else{
							q=x;
							p=y;
						}
						if(Cmap[p][q-1]==5) {
							player.setMoves(player.getMoves() + 1);
						}else {
							player.setScore(player.getScore() + county);
						}
						return true;
						
					}
					countx=1;
				}
				
				if(Cmap[x][y]==vary&&x>0) {
					county++;
				}else {
					vary=Cmap[x][y];
					if(county>=3) {
						if(y>0&&x==0) {
							q=8;
							p=y-1;
						}else{
							q=x;
							p=y;
						}
						if(Cmap[q-1][p]==5) {
							player.setMoves(player.getMoves() + 1);
						}else {
							player.setScore(player.getScore() + county);
						}
						return true;
					}
					county=1;
				}	
			}
		}
			
		return false;
	}
	
	

	private void updatemap() {
		for(int y=0;y<8;y++) {
		for(int x=0;x<8;x++) {
			Game.map[y][x]=MouseInput.Cmap[y][x];
		}
	}
	}
	
	private int[][] createClonemap() {
		for(int y=0;y<8;y++) {
			for(int x=0;x<8;x++) {
				Cmap[y][x]=Game.map[y][x];
			}
		}
		return Cmap;
	}
	
	private void printmap(int[][]array) {
		System.out.print("\n");
		for(int y=0;y<8;y++) {
			for(int x=0;x<8;x++) {
					System.out.print(array[y][x]);
				}
				
			}
		}
				
	public void Mapmaker() {
		ArrayList<Integer> type = new ArrayList();
		
		int[][] array= new int[8][8];
		type.add(0);type.add(1);type.add(2);type.add(3);type.add(5);
		int varx = 0;
		int vary=0;
		int countx=1;
		int county=1;
		for(int y=0;y<8;y++) {
			for(int x=0;x<8;x++){
				if(countx<2) {
					array[y][x] = type.get(rand.nextInt(type.size()));
					if(array[y][x]==varx) {
						countx++;
					}
					varx=array[y][x];
				}else {
	
					type.remove(Integer.valueOf(varx));
					array[y][x] = type.get(rand.nextInt(type.size()));
					type.add(varx);
					varx=array[y][x];
					countx=1;
					}
			}}
			for(int y=0;y<8;y++) {
				for(int x=0;x<8;x++){
				if(county<2) {
					if(array[x][y]==vary) {
						county++;
					}
					vary=array[x][y];
				}else {
					type.remove(Integer.valueOf(vary));
					array[x][y] = type.get(rand.nextInt(type.size()));
					type.add(vary);
					vary=array[x][y];
					county=1;
					}
				}	
			}
		for(int y=0;y<8;y++) {
			for(int x=0;x<8;x++) {
				Game.map[y][x]=array[y][x];
			}
		}
	}

}
