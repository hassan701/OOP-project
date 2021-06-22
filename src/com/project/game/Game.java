package com.project.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Music;
import org.newdawn.slick.util.LocatedImage;

public class Game extends Canvas implements Runnable{
	
	private static final long serialVersionUID = 4152405601456631823L;

	public static final int Width =  960, Height =Width/12*9;
	private Thread thread;
	private boolean running = false;
	private int fps,ups;
	public static boolean changeMap=false;
	public static boolean  Pause=false;
	public static int[][] map = {
            {0, 1, 1, 3, 2, 3, 1, 3},
            {2, 3, 2, 0, 0, 2, 3, 1},
            {1, 2, 2, 1, 0, 0, 1, 2},
            {3, 2, 3, 1, 3, 1, 2, 3},
            {2, 1, 2, 0, 1, 0, 2, 1},
            {0, 1, 2, 2, 3, 3, 1, 2},
            {0, 0, 1, 3, 3, 2, 3, 3},
            {3, 0, 1, 3, 1, 2, 3, 1}, 
        };
	
	private Handler handler;
	private Player player;
	private Menu menu;
	Random rand = new Random();
	public static BufferedImage background;
	public static BufferedImage background2;
	public static BufferedImage background3;
	public static GameState gameState = GameState.Menu;

	/**
	 * 
	 */
	public Game(){
		handler = new Handler();
		player= new Player();
		this.addKeyListener(new KeyInput(handler));
		this.addMouseListener(new MouseInput(handler,player));
		this.addMouseMotionListener(new MouseInput(handler,player));
		Audio.init();;
		
		new Window(Width, Height, "Candy Crash", this);
		menu = new Menu(player);
		ImageLoader loader = new ImageLoader();

		background= loader.loadImage("/background.png");
		background2= loader.loadImage("/background2.png");
		background3= loader.loadImage("/background3.png");
		
		
		
	}
			
	
	public boolean inArray(int[]arr,int ele) {
		for (int element : arr) {
            if (element == ele) {
                return true;
            }
        }
		return false;
	}
	
	public synchronized void start() {	
		thread = new Thread(this);
		running = true;
		thread.start();
	}
	public synchronized void stop() {	
		try{
			thread.join();
			running = false;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void run() {
		this.requestFocus();
		double ns = 1000000000 / 60d; 
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		double delta =0;
		while(running) {
			long currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / ns;
			lastTime = currentTime;
			 
			while(delta>=1) {
				update();
				delta--;
			}
			
			render();
			if(System.currentTimeMillis()- timer >1000) {
				timer+=1000;
				//System.out.println(String.format("FPS: %d, UPS: %d",fps,ups));
				fps= 0;
				ups=0;
				
			}
		}
		stop();
			
	}
	
	private void render() {
		fps++;
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, Width, Height);
		g.drawImage(background3,0,0,null);
		if(gameState==GameState.Game) {
			g.drawImage(background2,0,0,null);
			handler.render(g);
			player.render(g);
			if(Pause){
				g.drawString("Paused",500, 500);

			}
			
		}else if(gameState!=GameState.Game) {
			menu.render(g);
			
		}
		
		
		g.dispose();
		bs.show();
	}

	private void update() {
		ups++;
		if(gameState==GameState.Game) {
			if(!Pause) {
				handler.update();
				player.update();
				checkmap();
				if(player.getMoves()==0||player.getScore()==player.getGoal()) {
					handler.clear();
					gameState=GameState.End;
				}
			}
		}else if(gameState!=GameState.Game) {
			menu.update();
			
		}
	}
	
	
	
	public static int Clamp(int y, int Min, int Max) {
		if(y<= Min ) return y=Min;
		else if(y>=Max) return y=Max;
		else return y;
	}
	
	private int checkmap() {
		int varx= Game.map[0][0];
		int vary= Game.map[0][0];
		int countx =0;
		int county =0;
		int q=0;
		int p=0;
	
		for(int y=0;y<8;y++) {
			for(int x=0;x<8;x++) {
				if(Game.map[y][x]==varx && x>0&&varx!=4) {
					countx++;
				}else {
					varx=Game.map[y][x];
					if(countx>=3) {
						if(x==0&&y>0) {
							q=8;
							p=y-1;
						}else{
							q=x;
							p=y;
						}
						if(Game.map[p][q-1]==5) {
							player.setMoves(player.getMoves() + 1);
						}else {
							player.setScore(player.getScore() + county);
						}
						for(int i=countx;i>0;i--) {
							Game.map[p][q-i]=4;
						}
						
					}
					countx=1;
				}
				
				if(Game.map[x][y]==vary&&x>0&&vary!=4) {
					county++;
				}else {
					vary=Game.map[x][y];
					if(county>=3) {
						
						if(y>0&&x==0) {
							q=8;
							p=y-1;
						}else{
							q=x;
							p=y;
						}
						if(Game.map[q-1][p]==5) {
							player.setMoves(player.getMoves() + 1);
						}else {
							player.setScore(player.getScore() + county);
						}
						for(int i=county;i>0;i--) {
							Game.map[q-i][p]=4;
						}
						
					}
					county=1;
				}	
			}
		}
		return 0;
	}

	public static void main(String args[]) {
		
		new Game();
		
	}

}
