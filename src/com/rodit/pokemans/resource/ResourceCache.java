package com.rodit.pokemans.resource;

import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;

import com.rodit.pokemans.Game;
import com.rodit.pokemans.map.Map;
import com.rodit.pokemans.map.MapLoader;

public class ResourceCache {

	private static HashMap<String, Bitmap> bmpCache = new HashMap<String, Bitmap>();
	private static HashMap<String, Animation> animCache = new HashMap<String, Animation>();
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
	
	public static Animation getAnim(String name){
		Animation a = animCache.get(name);
		if(a == null){
			readAnim(name);
			return getAnim(name);
		}
		return a;
	}
	
	public static void readAnim(String name){
		//TODO FIX THIS
		String dat = new String(Game.readAsset("anim/" + name));
		int rows = 1;
		int columns = 1;
		String sheet = "";
		for(String prop : dat.split(";")){
			prop = prop.trim();
			String key = prop.split(":")[0].trim();
			String val = prop.split(":")[1].trim();
			if(key.equals("rows"))rows = Integer.valueOf(val);
			if(key.equals("columns"))columns = Integer.valueOf(val);
			if(key.equals("sheet"))sheet = val;
		}
		byte[] buff = Game.readAsset("anim/sheet/" + sheet);
		Bitmap bmp = BitmapFactory.decodeByteArray(buff, 0, buff.length);
		Animation anim = new Animation();
		
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
		fontCache.put(name, Typeface.createFromAsset(Game.getContext().getAssets(), "fonts/" + name));
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
		mapCache.put(name, MapLoader.load("maps/" + name));
	}
	
	public static void clearAll(){
		bmpCache.clear();
		animCache.clear();
	}
	
	public static void clearBmp(){
		bmpCache.clear();
	}
	
	public static void clearAnim(){
		animCache.clear();
	}
}
