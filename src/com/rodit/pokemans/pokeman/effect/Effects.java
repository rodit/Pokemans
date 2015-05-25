package com.rodit.pokemans.pokeman.effect;

import java.util.HashMap;

import com.rodit.pokemans.Game;
import com.rodit.pokemans.GameLog;
import com.rodit.pokemans.XmlDataReader;

public class Effects {

	private static HashMap<String, Effect> effects;
	
	public static void init(){
		effects =  XmlDataReader.readEffects(new String(Game.readAsset("pokeman/effects.xml")));
		GameLog.write("Effects registry size: " + effects.size() + "");
	}
	
	public static Effect get(String name){
		return effects.get(name);
	}
	
	public static void add(String name, Effect e){
		effects.put(name, e);
	}
}
