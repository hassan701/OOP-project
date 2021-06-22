package com.project.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Random;

import org.newdawn.slick.util.LocatedImage;

public class Candy extends GameObject {
	Handler handler;
	Random rand =new Random();
	
	public static BufferedImage candy1;
	public static BufferedImage candy2;
	public static BufferedImage candy3;
	public static BufferedImage candy4;
	public static BufferedImage candy5;
	
	private int type;
	public static boolean updatemap=false;
	Random ran = new Random();

	public Candy(int x,int y, ID id,Handler handler) {
		super(x, y, id);
		this.handler = handler;;
		this.type=Game.map[this.b][this.a];
		ImageLoader loader = new ImageLoader();
		candy1= loader.loadImage("/candy1.png");
		candy2= loader.loadImage("/candy2.png");
		candy3= loader.loadImage("/candy3.png");
		candy4= loader.loadImage("/candy4.png");
		candy5= loader.loadImage("/candy5.png");
		
		
	}
	
	 

	@Override
	public void update() {
		if(MouseInput.dragging==false) {
		this.a=this.getX()/(500/8);
		this.b= this.getY()/(500/8);
		this.ab=Integer.parseInt(Integer.toString(b)+Integer.toString(a));
		}
		if(b<7) {
			if(Game.map[b+1][a]==4&&y!=(b+1)*(500/8)) {
				this.setY(this.getY()+5);
				if(y>=(b+1)*(500/8)) {
					Game.map[b+1][a]=Game.map[b][a];
					Game.map[b][a]=4;
					this.setY((b)*(500/8));
				}
				
			}
		
			}
		this.type=Game.map[b][a];
		if(this.type==4 && b==0) {
			int randm = rand.nextInt(4) + 0;
			if(randm!=Game.map[b+1][a])Game.map[b][a]= randm;
			else Game.map[b][a]=4;
			}
	}	
	

	@Override
	public void render(Graphics g) {
		
		if(this.type==4) {
			
		}else {
			if(this.type==0) {
			g.drawImage(candy1,x,y,null);

		}
		else if(this.type==3) {
			g.drawImage(candy4,x,y,null);
		}
		else if(this.type==2) {
			g.drawImage(candy3,x,y,null);
		}
		else if(this.type==1) {
			g.drawImage(candy2,x,y,null);

		}else if(this.type==5) {
			g.drawImage(candy5,x,y,null);

		}
		}

		}
			
	

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x,y,54,54);
	}

}

