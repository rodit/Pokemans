package com.rodit.pokemans.pokeman.effect;

import java.util.HashMap;

public class Effects {

	private static HashMap<String, Effect> effects;
	
	public static void init(){
		effects = new HashMap<String, Effect>();
		//TODO add all effects
	}
	
	public static Effect get(String name){
		return effects.get(name);
	}
	
	public static void add(String name, Effect e){
		effects.put(name, e);
	}
}
