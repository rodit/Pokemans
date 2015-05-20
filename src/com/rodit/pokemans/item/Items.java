package com.rodit.pokemans.item;

import java.util.HashMap;

import com.rodit.pokemans.Game;
import com.rodit.pokemans.XmlDataReader;

public class Items {

	private static HashMap<String, Item> items;
	
	public static void init(){
		items = XmlDataReader.readItems(new String(Game.readAsset("item/items.xml")));
	}
	
	public static Item get(String name){
		return items.get(name);
	}
	
	public static void add(String name, Item i){
		items.put(name, i);
	}
}
