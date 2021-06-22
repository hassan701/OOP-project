package com.project.game;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class Audio {
	public static Map<String, Sound> soundMap = new HashMap<String,Sound>();
	public static Map<String, Music> musicMap = new HashMap<String,Music>(); 
	
	public static void init(){
		try {
			musicMap.put("Menu",new Music("res/bensound-memories.ogg"));
			musicMap.put("Game",new Music("res/bensound-creativeminds.ogg"));
			
			soundMap.put("nice",new Sound("res/nice.ogg"));
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public static Music getMusic(String key) {
		return musicMap.get(key);
	}
	public static Sound getSound(String key) {
		return soundMap.get(key);
	}
}
