package com.rodit.pokemans.script;

import java.lang.reflect.Method;

import com.rodit.pokemans.Game;
import com.rodit.pokemans.GameLog;
import com.rodit.pokemans.script.exception.ScriptException;

public class ScriptParser {

	public static void run(String script, VariableManager args){
		int lno = 0;
		try{
			VariableManager locals = args.cloneManager();
			boolean doIf = false;
			boolean waitForEndIf = false;
			for(String s : script.split(";")){
				lno++;
				String line = s.trim().replace("\\;", "ada8AE(FaefafoasaOAFE").replace(";", "").replace("ada8AE(FaefafoasaOAFE", ";");
				String[] parts = splitWS(line);
				if(waitForEndIf){
					if(parts[0].equals("end") && parts[1].equals("if")){
						waitForEndIf = false;
						doIf = false;
						continue;
					}
					if(!doIf)continue;
				}
				if(parts[0].equals("if")){
					boolean shouldDo = true;
					for(String condition : line.split("and")){
						condition = condition.replace("if ", "").replace("and", "");
						String[] c = splitWS(condition);
						Object o1 = getVal(c[1], locals);
						Object o2 = getVal(c[2], locals);
						if(c[0].equals("equal"))
							shouldDo = o1.equals(o2);
						else if(c[0].equals("notequal"))
							shouldDo = !o1.equals(o2);
						else if(c[0].equals("smaller"))
							shouldDo = Double.valueOf(o1.toString()) < Double.valueOf(o2.toString());
						else if(c[0].equals("greater"))
							shouldDo = Double.valueOf(o1.toString()) > Double.valueOf(o2.toString());
					}
					doIf = shouldDo;
				}
				if(parts[0].equals("var"))
					locals.addVar(parts[1], getVal(parts[2], locals));
				else if(parts[0].equals("global"))
					Game.globals.addVar(parts[1], getVal(parts[2], locals));
				else if(parts[0].equals("set")){
					if(getVal(parts[1], locals) != null);
					if(parts[1].startsWith("$"))
						locals.addVar(parts[1].replace("$", ""), getVal(parts[2], locals));
					else if(parts[1].startsWith("#"))
						Game.globals.addVar(parts[1].replace("#", ""), getVal(parts[2], locals));
				}else if(parts[0].equals("echo"))
					GameLog.write("[SCRIPT]" + getVal(line.substring(5), locals).toString());
				else if(parts[0].equals("destroy")){
					if(getVal(parts[1], locals) != null);
					if(parts[1].startsWith("$"))
						locals.delVar(parts[1].replace("$", ""));
					else if(parts[1].startsWith("#"))
						Game.globals.delVar(parts[1].replace("#", ""));
				}else if(parts[0].equals("echo")){
					GameLog.write(line.substring(5));
				}else if(parts[0].equals("script")){
					String sBody = new String(Game.readAsset("script/" + parts[1] + ".psc"));
					Object[] sArgs = new Object[parts.length - 2];
					for(int i = 2; i < parts.length; i++){
						sArgs[i - 2] = getVal(parts[i], locals);
					}
					VariableManager vArgs = new VariableManager();
					for(int i = 0; i < sArgs.length; i++){
						vArgs.addVar("arg" + i, sArgs[i]);
					}
					ScriptParser.run(sBody, vArgs);
				}
			}
		}catch(ScriptException e){
			GameLog.write("Error while running script. Line: " + lno + ", " + e.getMessage());
			GameLog.write("Script execution cancelled.");
		}
	}

	public static String[] splitWS(String s){
		return s.split("\\s+");
	}

	public static Object getVal(String expression, VariableManager local)throws ScriptException{
		Object o = expression;
		if(expression.startsWith("$")){
			o = local.getVar(expression.replace("$", ""));
			if(o == null)throw new ScriptException("Reference to undefined local vairable " + expression + ".");
		}else if(expression.startsWith("#")){
			o = Game.globals.getVar(expression.replace("#", ""));
			if(o == null)throw new ScriptException("Reference to undefined global variable " + expression + ".");
		}else if(expression.startsWith("append[") || expression.startsWith("add[") || expression.startsWith("+[")){
			String str = expression.substring(expression.indexOf("[") + 1, expression.indexOf("]"));
			for(String obj : str.split(",")){
				obj = obj.replace(",", "");
				Object lo = getVal(obj, local);
				if(lo instanceof String)
					o = String.valueOf(o) + lo;
				else if(lo instanceof Integer || lo instanceof Double || lo instanceof Float){
					if(o instanceof String)
						o = (String)o + (Double)lo;
					else if(o instanceof Integer)
						o = (Double)lo + (Double)o;
				}
			}
		}else if(expression.startsWith("-[") || expression.startsWith("sub[")){
			String str = expression.substring(expression.indexOf("[") + 1, expression.indexOf("]"));
			for(String obj : str.split(",")){
				obj = obj.replace(",", "");
				Object lo = getVal(obj, local);
				o = (Double)o - (Double)lo;
			}
		}else if(expression.startsWith("*[") || expression.startsWith("mul[")){
			String str = expression.substring(expression.indexOf("[") + 1, expression.indexOf("]"));
			for(String obj : str.split(",")){
				obj = obj.replace(",", "");
				Object lo = getVal(obj, local);
				o = (Double)o * (Double)lo;
			}
		}else if(expression.startsWith("/[") || expression.startsWith("div[")){
			String str = expression.substring(expression.indexOf("[") + 1, expression.indexOf("]"));
			for(String obj : str.split(",")){
				obj = obj.replace(",", "");
				Object lo = getVal(obj, local);
				o = (Double)o / (Double)lo;
			}
		}else if(expression.startsWith("*[") || expression.startsWith("mul[")){
			String str = expression.substring(expression.indexOf("[") + 1, expression.indexOf("]"));
			for(String obj : str.split(",")){
				obj = obj.replace(",", "");
				Object lo = getVal(obj, local);
				o = (Double)o * (Double)lo;
			}
		}else if(expression.contains("->")){
			String objName = expression.split("->")[0];
			Class<?> c = null;
			Object o2 = null;
			if(objName.equals("_game")){
				c = Game.class;
			}else{
				o2 = getVal(objName, local);
				c = o2.getClass();
			}
			String mName = expression.split("->")[1].split("[")[0];
			String[] args = expression.split("[")[1].split("]")[0].replace("[", "").replace("]", "").split(",");
			Object[] argsO = new Object[args.length];
			for(int i = 0; i < args.length; i++)
				argsO[i] = getVal(args[i], local);
			for(Method m : c.getMethods()){
				if(m.getName().equals(mName)){
					if(m.isAnnotationPresent(ScriptRef.class)){
						try{
							m.invoke(o2, argsO);
						}catch (Exception e) {
							throw new ScriptException("There was an exception while invoking the referenced method " + e.getMessage() + ".");
						}
					}else{
						throw new ScriptException("The referenced method '" + mName + "' cannot be invoked through scripts.");
					}
				}
			}
		}
		return o;
	}
}