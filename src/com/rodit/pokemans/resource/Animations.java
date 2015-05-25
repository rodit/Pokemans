package com.rodit.pokemans.resource;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.rodit.pokemans.Game;
import com.rodit.pokemans.GameLog;
import com.rodit.pokemans.Util;
import com.rodit.pokemans.XmlDataReader;

public class Animations {
	
	private static HashMap<String, Animation> anims;
	
	public static void init(){
		anims = new HashMap<String, Animation>();
		Document doc = XmlDataReader.createDocument(new String(Game.readAsset("anim/animations.xml")));
		NodeList animNodes = doc.getElementsByTagName("animation");
		for(int i = 0; i < animNodes.getLength(); i++){
			Node n = animNodes.item(i);
			if(n.getNodeType() != Node.ELEMENT_NODE)continue;
			Element e = (Element)n;
			String name = e.getAttribute("name");
			Animation a = new Animation();
			int rows = Integer.valueOf(e.getAttribute("rows"));
			int cols = Integer.valueOf(e.getAttribute("columns"));
			try{
				a.setBitmaps(loadSheet(BitmapFactory.decodeStream(Game.getAssetStream("anim/sheet/" + e.getAttribute("sheet") + ".png")),
						rows, cols));
			}catch(Exception ex){
				GameLog.write("Error while reading sprite sheet for animation " + name + "." + rows + ":" + cols);
				ex.printStackTrace();
				continue;
			}
			a.setDelay(Float.valueOf(e.getAttribute("delay")));
			a.setRetainFrame(Boolean.valueOf(e.getAttribute("retainFrame")));
			register(name, a);
		}
		GameLog.write("Animation registry size " + anims.size() + ".");
	}
	
	public static ArrayList<Bitmap> loadSheet(Bitmap bmp, int rows, int columns){
		ArrayList<Bitmap> bmps = new ArrayList<Bitmap>();
		int w = bmp.getWidth() / columns;
		int h = bmp.getHeight() / rows;
		for(int x = 0; x < columns; x++){
			for(int y = 0; y < rows; y++){
				int x2 = w * x;
				int y2 = h * y;
				bmps.add(Util.Bitmap.crop(bmp, new Rect(x2, y2, x2 + w, y2 + h)));
			}
		}
		return bmps;
	}
	
	public static Animation get(String name, String reqID){
		if(name == null)return null;
		return anims.get(name).get(reqID);
	}
	
	public static void register(String name, Animation animation){
		anims.put(name, animation);
	}
}
