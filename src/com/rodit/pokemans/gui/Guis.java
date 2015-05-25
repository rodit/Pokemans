package com.rodit.pokemans.gui;

import java.util.HashMap;

import com.rodit.pokemans.Game;
import com.rodit.pokemans.GameLog;
import com.rodit.pokemans.XmlDataReader;

public class Guis {

	private static HashMap<String, Gui> guis;
	
	public static void init(){
		GameLog.write("Initializing guis.");
		guis = new HashMap<String, Gui>();
		try{
			String[] guiList = Game.assets.list("gui");
			GameLog.write("Gui count " + guiList.length);
			for(String file : guiList){
				GameLog.write("GUI File:" + file);
				String xml = new String(Game.readAsset("gui/" + file));
				register(file.replace(".xml", ""), XmlDataReader.readGui(xml));
			}
		}catch(Exception e){
			GameLog.write("Error while reading gui.");
			e.printStackTrace();
		}
		GameLog.write("Gui registry size " + guis.size() + ".");
	}
	
	public static Gui get(String name){
		return guis.get(new String(name));
	}
	
	public static void register(String name, Gui g){
		guis.put(name, g);
		GameLog.write("Registered gui " + name + ".");
	}
}
