package com.rodit.pokemans.pokeman;

import java.util.HashMap;

public class Pokeman {

	public int id;
	public String name;
	public int level;
	public String icon;
	public HashMap<String, String> animations;
	public BattleType type;
	public int evolvesto;
	public int evolvesat;
	
	public PokemanStats stats = new PokemanStats();
	
	public Pokeman(){
		id = 0;
		name = "Unknown Pokeman";
		level = 1;
		icon = "undefined";
		animations = new HashMap<String, String>();
		type = BattleType.NORMAL;
		evolvesto = -1;
	}
}
