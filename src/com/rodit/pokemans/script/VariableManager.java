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
	
	public HashMap<String, Object> getVars(){
		return vars;
	}
	
	public HashMap<String, Object> cloneVars(){
		HashMap<String, Object> vars = new HashMap<String, Object>();
		for(String s : vars.keySet()){
			vars.put(s, vars.get(s));
		}
		return vars;
	}
	
	public void setVars(HashMap<String, Object> vars){
		this.vars = vars;
	}
	
	public VariableManager cloneManager(){
		VariableManager vm = new VariableManager();
		vm.setVars(cloneVars());
		return vm;
	}
}
