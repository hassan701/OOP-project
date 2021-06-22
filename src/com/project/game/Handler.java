package com.project.game;

import java.awt.Graphics;
import java.util.LinkedList;

public class Handler {
	
	LinkedList<GameObject> object =  new LinkedList<GameObject>();
	
	public void update() {
		for(int i =0;i<object.size();i++) {
			GameObject temp = object.get(i);
			temp.update();
		}
}
	public void render(Graphics g) {
		for(int i =0;i<object.size();i++) {
			GameObject temp = object.get(i);
			temp.render(g);
		}
	}
	
	public void addObject(GameObject object) {
		this.object.add(object);
	}
	public int getSize() {
		return this.object.size();
	}
	public void removeObject(GameObject object) {
		this.object.remove(object);
	}
	public void clear() {
		this.object.clear();
	}
		
}
