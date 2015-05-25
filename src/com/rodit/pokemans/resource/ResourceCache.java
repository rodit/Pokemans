package com.rodit.pokemans.resource;

import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;

import com.rodit.pokemans.Game;
import com.rodit.pokemans.XmlDataReader;
import com.rodit.pokemans.map.Map;

public class ResourceCache {

	private static HashMap<String, Bitmap> bmpCache = new HashMap<String, Bitmap>();
	private static HashMap<String, Typeface> fontCache = new HashMap<String, Typeface>();
	private static HashMap<String, Map> mapCache = new HashMap<String, Map>();
	
	public static Bitmap getImage(String name){
		Bitmap b = bmpCache.get(name);
		if(b == null){
			readBitmap(name);
			return getImage(name);
		}
		return b;
	}
	
	public static void readBitmap(String name){
		byte[] buff = Game.readAsset("bitmap/" + name);
		bmpCache.put(name, BitmapFactory.decodeByteArray(buff, 0, buff.length));
		buff = null;
	}
	
	public static Typeface getFont(String name){
		Typeface tf = fontCache.get(name);
		if(tf == null){
			readFont(name);
			return getFont(name);
		}
		return tf;
	}
	
	public static void readFont(String name){
		fontCache.put(name, Typeface.createFromAsset(Game.assets, "font/" + name));
	}
	
	public static Map getMap(String name){
		Map m = mapCache.get(name);
		if(m == null){
			readMap(name);
			return getMap(name);
		}
		return m;
	}
	
	public static void readMap(String name){
		mapCache.put(name, XmlDataReader.readMap("map/" + name));
	}
	
	public static void clearAll(){
		bmpCache.clear();
		fontCache.clear();
		mapCache.clear();
	}
	
	public static void clearBmp(){
		bmpCache.clear();
	}
	
	public static void clearFont(){
		fontCache.clear();
	}
	
	public static void clearMap(){
		mapCache.clear();
	}
}
