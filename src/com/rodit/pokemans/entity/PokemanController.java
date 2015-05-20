package com.rodit.pokemans.entity;

import java.util.ArrayList;
import java.util.HashMap;

import android.util.SparseIntArray;

import com.rodit.pokemans.pokeman.ability.Ability;
import com.rodit.pokemans.pokeman.effect.Effect;

public class PokemanController {

	private HashMap<Integer, ArrayList<Effect>> effects;
	private HashMap<Integer, ArrayList<Ability>> abilities;
	private SparseIntArray levels;
	private HashMap<Integer, Float> healths;
	
	public PokemanController(){
		effects = new HashMap<Integer, ArrayList<Effect>>();
		abilities = new HashMap<Integer, ArrayList<Ability>>();
		levels = new SparseIntArray();
		healths = new HashMap<Integer, Float>();
	}
	
	public ArrayList<Effect> getEffects(int id){
		ArrayList<Effect> ef = effects.get(id);
		if(ef == null)return new ArrayList<Effect>();
		return ef;
	}
	
	public ArrayList<Ability> getAbilities(int id){
		ArrayList<Ability> af = abilities.get(id);
		if(af == null)return new ArrayList<Ability>();
		return af;
	}
	
	public int getLevel(int id){
		return levels.get(id);
	}
	
	public boolean hasEffect(int id, Effect e){
		return getEffects(id).contains(e);
	}
	
	public boolean hasAbility(int id, Ability a){
		return getAbilities(id).contains(a);
	}
	
	public void addEffect(int id, Effect e){
		if(effects.containsKey(id))
			effects.get(id).add(e);
		else{
			ArrayList<Effect> es = new ArrayList<Effect>();
			es.add(e);
			effects.put(id, es);
		}
	}
	
	public void addAbility(int id, Ability a){
		if(abilities.containsKey(id))
			abilities.get(id).add(a);
		else{
			ArrayList<Ability> as = new ArrayList<Ability>();
			as.add(a);
			abilities.put(id, as);
		}
	}
	
	public void setLevel(int id, int level){
		levels.put(id, level);
	}
	
	public void setHealth(int id, float health){
		healths.put(id, health);
	}
	
	public void addLevel(int id){
		addLevel(id, 1);
	}
	
	public void addLevel(int id, int amount){
		levels.put(id, levels.get(id) + amount);
	}
	
	public void addHealth(int id, float amount){
		Float f = healths.get(id);
		if(f == null){
			healths.put(id, amount);
		}else{
			healths.put(id, f + amount);
		}
	}
}
