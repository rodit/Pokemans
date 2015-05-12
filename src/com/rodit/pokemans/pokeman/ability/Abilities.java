package com.rodit.pokemans.pokeman.ability;

import java.util.HashMap;

public class Abilities {

	private static HashMap<String, Ability> abilities;

	public static void init(){
		abilities = new HashMap<String, Ability>();
		//TODO add all abilities
	}
	
	public static Ability get(String name){
		return abilities.get(name);
	}
	
	public static void add(String name, Ability a){
		abilities.put(name, a);
	}
}
