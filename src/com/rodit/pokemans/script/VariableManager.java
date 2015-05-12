package com.rodit.pokemans.script;

import java.util.HashMap;

public class VariableManager {

	private HashMap<String, Object> vars = new HashMap<String, Object>();
	
	public VariableManager(){}
	
	public void addVar(String id, Object value){
		vars.put(id, value);
	}
	
	public void delVar(String id){
		vars.remove(id);
	}
	
	public Object getVar(String id){
		return vars.get(id);
	}
	
	public Object[] getObjects(){
		Object[] objs = new Object[vars.size()];
		int i = 0;
		for(Object o : vars.entrySet()){
			objs[i] = o;
			i++;
		}
		return objs;
	}
	
	public static VariableManager create(String expression, VariableManager localVars, VariableManager args, Script script){
		VariableManager mgr = new VariableManager();
		int i = 0;
		for(String s : expression.split(",")){
			mgr.addVar("arg" + i, ScriptParser.evaluate(s, localVars, args, script));
			i++;
		}
		return mgr;
	}
}
