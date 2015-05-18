package com.rodit.pokemans;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.Display;
import android.view.WindowManager;

import com.rodit.pokemans.entity.EntityPlayer;
import com.rodit.pokemans.gui.Gui;
import com.rodit.pokemans.gui.guis.GuiMenu;
import com.rodit.pokemans.map.Map;
import com.rodit.pokemans.pokeman.PokemanRegistry;
import com.rodit.pokemans.resource.ResourceCache;
import com.rodit.pokemans.resource.Strings;
import com.rodit.pokemans.script.VariableManager;

public class Game {

	private static Context context;
	private static float delta = 0;
	private static long lastTick = 0;
	
	public static float scrollX = 0;
	public static float scrollY = 0;
	
	public static String startMap = "start";
	
	public static EntityPlayer player;
	public static Map map;
	public static HashMap<String, Gui> guis = new HashMap<String, Gui>();
	public static PokemanRegistry pokemans = new PokemanRegistry();
	
	public static float screenX = 0;
	public static float screenY = 0;
	public static float screenRatioX = 0;
	public static float screenRatioY = 0;
	
	public static VariableManager globals = new VariableManager();
	
	public static void init(){
		Strings.init();
		PokemanRegistry.init(pokemans);
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		screenX = size.x;
		screenY = size.y;
		screenRatioX = 1280 / screenX;
		screenRatioY = 720 / screenY;
		guis.put("menu", new GuiMenu());
		guis.get("menu").setActive(true);
		map = ResourceCache.getMap(startMap);
		player = new EntityPlayer();
		globals.addVar("player", player);
		globals.addVar("map", map);
	}

	public static Context getContext(){
		return context;
	}

	public static void setContext(Context c){
		context = c;
	}

	public static float getDelta(){
		return delta;
	}

	public static long getLastTick(){
		return lastTick;
	}

	public static long getTime(){
		return System.currentTimeMillis();
	}

	public static void update(){
		delta = 1 / (System.currentTimeMillis() - lastTick);
		lastTick = System.currentTimeMillis();
	}

	public static void render(Canvas canvas){
		map.render(canvas);
		for(Gui g : guis.values()){
			if(g.getActive())g.draw(canvas);
		}
	}

	public static byte[] readAsset(String file) {
		byte[] buffer = null;
		try {
			InputStream stream = getAssetStream(file);
			
			int size = stream.available();
			buffer = new byte[size];
			stream.read(buffer);
			stream.close();
		} catch (IOException e) {
			GameLog.write("Error while reading asset " + file + ": " + e.getMessage());
		}
		return buffer;
	}
	
	public static InputStream getAssetStream(String file) throws IOException{
		return context.getAssets().open(file);
	}
	
	public static Rect resizeRect(Rect rect){
		return new Rect((int)resizeX(rect.left), (int)resizeY(rect.top), (int)resizeX(rect.width()), (int)resizeY(rect.height()));
	}
	
	public static RectF resizeRect(RectF rect){
		return new RectF(resizeX(rect.left), resizeY(rect.top), resizeX(rect.width()), resizeY(rect.height()));
	}
	
	public static float resizeX(float x){
		return x * screenRatioX;
	}
	
	public static float resizeY(float y){
		return y * screenRatioY;
	}
	
	public static Rect getScreenRect(){
		return new Rect(0, 0, (int)screenX, (int)screenY);
	}

	public static RectF getScreenRectF() {
		return new RectF(0, 0, screenX, screenY);
	}
}
