package com.rodit.pokemans.item;

import java.util.HashMap;

public class Items {

	private static HashMap<String, Item> items;
	
	public static void init(){
		items = new HashMap<String, Item>();
		//TODO add all items
	}
	
	public static Item get(String name){
		return items.get(name);
	}
	
	public static void add(String name, Item i){
		items.put(name, i);
	}
}
