package com.rodit.pokemans.pokeman.ability;

import java.util.HashMap;

import com.rodit.pokemans.Game;
import com.rodit.pokemans.XmlDataReader;

public class Abilities {

	private static HashMap<String, Ability> abilities;

	public static void init(){
		abilities = XmlDataReader.readAbilities(new String(Game.readAsset("pokeman/abilities.xml")));
	}
	
	public static Ability get(String name){
		return abilities.get(name);
	}
	
	public static void add(String name, Ability a){
		abilities.put(name, a);
	}
}
