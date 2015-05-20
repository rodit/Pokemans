package com.rodit.pokemans.item;

import com.rodit.pokemans.script.ScriptParser;
import com.rodit.pokemans.script.VariableManager;

public class Item{
	
	public String name;
	public String resource;
	public String description;
	public boolean canUse;
	public String useScript;

	public Item(){
		name = "Undefined item";
		resource = "";
		description = "";
		canUse = true;
		useScript = "";
	}

	public void use(Object user, Object target){
		if(useScript != null){
			if(useScript != "" && !useScript.isEmpty()){
				VariableManager args = new VariableManager();
				args.addVar("user", user);
				args.addVar("target", target);
				ScriptParser.run(useScript, args);
			}
		}
	}
}
