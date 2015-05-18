package com.rodit.pokemans.map;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

import android.graphics.RectF;

import com.rodit.pokemans.Game;

public class MapLoader {

	public static Map load(String file){
		Map m = new Map();
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(Game.getAssetStream("map/" + file)));
			String line = null;
			while((line = reader.readLine()) != null){
				if(be(line).equals("name"))
					m.setShowName(ae(line));
				else if(be(line).equals("spawn.x"))
					m.setSpawnX(Float.valueOf(ae(line)));
				else if(be(line).equals("spawn.y"))
					m.setSpawnY(Float.valueOf(ae(line)));
				else if(be(line).equals("b64"))
					m.setBase64Img(ae(line));
				else if(be(line).equals("script")){
					m.setScript(ae(line));
				}else if(be(line).equals("collisions")){
					for(String rect : ae(line).split(Pattern.quote(":"))){
						m.addCollision(getRect(rect));
					}
				}
			}
		}catch(Exception e){
			
		}
		return m;
	}

	private static RectF getRect(String s){
		String[] parts = s.split(Pattern.quote(","));
		return new RectF(Float.valueOf(parts[0]), Float.valueOf(parts[1]), Float.valueOf(parts[2]), Float.valueOf(parts[3]));
	}

	private static String be(String s){
		return split(s)[0].replace("=", "");
	}

	private static String ae(String s){
		return split(s)[1].replace("=", "");
	}

	private static String[] split(String in){
		return in.split(Pattern.quote("="));
	}
}
