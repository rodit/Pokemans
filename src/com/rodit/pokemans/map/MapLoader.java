package com.rodit.pokemans.map;

import com.rodit.pokemans.XmlDataReader;

public class MapLoader {

	public static Map load(String file){
		return XmlDataReader.readMap(file);
	}
}
