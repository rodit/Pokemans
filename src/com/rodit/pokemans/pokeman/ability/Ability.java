package com.rodit.pokemans.pokeman.ability;

import java.util.ArrayList;

import com.rodit.pokemans.pokeman.effect.Effect;

public class Ability {
	
	public String name;
	public float damage;
	public ArrayList<Effect> effectsSelf;
	public ArrayList<Effect> effectsTarget;
	public int maxPP;
	
	public Ability(){
		name = "Unknown Ability";
		damage = 0;
		effectsSelf = new ArrayList<Effect>();
		effectsTarget = new ArrayList<Effect>();
		maxPP = 15;
	}
}
