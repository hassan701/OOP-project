package com.project.game;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject {
	protected int x,y;
	protected int a,b;
	protected int ab;
	
	public Rectangle collide = new Rectangle();
	protected ID id;
	public GameObject(int x, int y, ID id) {
		
		this.id = id;
		if(this.id==ID.Candy) {
			this.a =x;
			this.b=y;
			this.ab=Integer.parseInt(Integer.toString(b)+Integer.toString(a));
			this.x=x*(500/8);
			this.y=y*(500/8);
			}else {
				this.x = x;
				this.y = y;
			}
	}
	
	public abstract void update();
	public abstract void render(Graphics g);
	public abstract Rectangle getBounds();
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public ID getId() {
		return id;
	}
	public void setId(ID id) {
		this.id = id;
	}
	public int getA() {
		return a;
	}
	public int getB() {
		return b;
	}
	public int getAb() {
		return ab;
	}
	public void setA(int a) {
		this.a = a;
	}
	public void setB(int b) {
		this.b = b;
	}
}
