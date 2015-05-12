package com.rodit.pokemans.script;

public class ScriptMethod {

	public String name;
	public String body;
	
	public ScriptMethod(){
		name = "";
		body = "";
	}
	
	public ScriptMethod(String name, String body){
		this.name = name;
		this.body = body;
	}
	
	public Object invoke(VariableManager args){
		return ScriptParser.parse(body, args);
	}
}
