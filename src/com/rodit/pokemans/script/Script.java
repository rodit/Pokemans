package com.rodit.pokemans.script;

import java.util.ArrayList;

public class Script {

	public VariableManager localVars;
	public String body = "";
	public ArrayList<ScriptMethod> methods;
	public ArrayList<Script> includes;
	
	public Script(){
		localVars = new VariableManager();
		methods = new ArrayList<ScriptMethod>();
		includes = new ArrayList<Script>();
	}
	
	public ScriptMethod getMethod(String name){
		for(ScriptMethod method : methods){
			if(method.name.equals(name))return method;
		}
		return null;
	}
	
	public void scanMethods(){
		if(body == null)return;
		if(body == "")return;
		methods.clear();
		String cmethod = "";
		String cmethodbody = "";
		for(String s : body.split(";")){
			String line = s.trim();
			if(cmethod != ""){
				if(line.equalsIgnoreCase("end function")){
					methods.add(new ScriptMethod(cmethod, cmethodbody));
					cmethod = "";
					continue;
				}
				cmethodbody += line;
			}
			if(line.startsWith("function")){
				cmethod = line.split(" ")[1].trim();
			}
		}
	}
	
	public static Script parse(String body){
		Script scr = new Script();
		scr.body = body;
		
		return scr;
	}
}
