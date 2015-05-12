package com.rodit.pokemans.script;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.rodit.pokemans.Game;

public class ScriptParser {

	public static Object parse(String script, VariableManager args){
		VariableManager localVars = new VariableManager();
		Object obj = null;
		Script s = new Script();
		s.body = script;
		s.scanMethods();
		return obj;
	}

	public static Object evaluate(String expression, VariableManager localVars, VariableManager args, Script scriptRef){
		if(expression.startsWith("$"))
			return localVars.getVar(expression.substring(1, expression.length() - 1));
		else if(expression.startsWith("#"))
			return Game.globals.getVar(expression.substring(1, expression.length() - 1));
		else if(expression.startsWith("@")){
			Object[] fargs = VariableManager.create(getTextBetweenBraces(expression), localVars, args, scriptRef).getObjects();
			try{
				ScriptParser.class.getMethod(expression.split(" ")[0].trim().replace("@", "")).invoke(null, fargs);
			}catch(Exception e){}
		}else if(expression.startsWith("&")){
			String funcName = expression.substring(1, expression.split(" ")[0].trim().length() - 1);
			ScriptMethod method = scriptRef.getMethod(funcName);
			if(method != null){
				
			}
		}
		return expression;
	}

	public static String getTextBetweenBraces(String s){
		Pattern p = Pattern.compile("\\[.*?\\]");
		Matcher m = p.matcher(s);
		return m.group();
	}

	public static int add(int... args){
		int added = 0;
		for(int i : args)
			added += i;
		return added;
	}

	public static int sub(int... args){
		int subbed = args[0];
		for(int i = 1; i < args.length; i++)
			subbed -= args[i];
		return subbed;
	}

	public static int div(int... args){
		int dived = args[0];
		for(int i = 1; i < args.length; i++)
			dived /= args[i];
		return dived;
	}

	public static int mul(int... args){
		int muled = 1;
		for(int i : args)
			muled *= i;
		return muled;
	}

	public static long fact(int args){
		long factorial = args;
		for(int i = 0; i < args; i++)
			factorial *= i;
		return factorial;
	}
}
