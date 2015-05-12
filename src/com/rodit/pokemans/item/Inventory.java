package com.rodit.pokemans.item;

import java.util.HashMap;


public class Inventory {

	public HashMap<Item, Integer> items;
	
	public Inventory(){
		items = new HashMap<Item, Integer>();
	}
	
	public void add(Item i){
		add(i, 1);
	}
	
	public void add(Item i, int count){
		int c = count;
		if(items.get(i) != null)c += items.get(i);
		items.put(i, c);
	}
	
	public void consume(Item i){
		consume(i, 1);
	}
	
	public void consume(Item i, int count){
		add(i, -count);
	}
}
