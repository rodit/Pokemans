package com.rodit.pokemans;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.Display;
import android.view.WindowManager;

import com.rodit.pokemans.entity.Entity;
import com.rodit.pokemans.entity.EntityPlayer;
import com.rodit.pokemans.gui.Gui;
import com.rodit.pokemans.gui.Guis;
import com.rodit.pokemans.map.Map;
import com.rodit.pokemans.pokeman.PokemanRegistry;
import com.rodit.pokemans.pokeman.ability.Abilities;
import com.rodit.pokemans.pokeman.effect.Effects;
import com.rodit.pokemans.resource.Animations;
import com.rodit.pokemans.resource.ResourceCache;
import com.rodit.pokemans.script.ScriptParser;
import com.rodit.pokemans.script.ScriptRef;
import com.rodit.pokemans.script.VariableManager;

public class Game {

	private static Context context;
	private static float delta = 0;
	private static long lastTick = 0;

	public static String startMap = "start.xml";

	public static AssetManager assets;
	public static EntityPlayer player;
	public static Map map;
	public static Gui gui;
	public static PokemanRegistry pokemans = new PokemanRegistry();
	public static FPSCounter fpsCounter = new FPSCounter();

	public static float screenX = 0;
	public static float screenY = 0;
	public static float screenRatioX = 0;
	public static float screenRatioY = 0;
	
	public static Camera camera;

	public static final boolean DEBUG = true;
	public static final boolean DEBUG_DRAW = false;

	public static VariableManager globals = new VariableManager();

	public static void init(){
		try{
			GameLog.write("Initializing game...");
			camera = new Camera();
			Animations.init();
			Abilities.init();
			Effects.init();
			Guis.init();
			PokemanRegistry.init(pokemans);
			WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			Display display = wm.getDefaultDisplay();
			Point size = new Point();
			display.getSize(size);
			screenX = size.x;
			screenY = size.y;
			screenRatioX = 1280 / screenX;
			screenRatioY = 720 / screenY;
			setGui("menu").setActive(true);
			player = new EntityPlayer(XmlDataReader.readEntity(new String(readAsset("entity/player.xml"))));
			//map = ResourceCache.getMap(startMap);
			globals.addVar("player", player);
			globals.addVar("map", map);
			
			fpsCounter.start();
			MemRecord.init();
		}catch(Exception e){
			GameLog.write("Exception while starting game.");
			e.printStackTrace();
		}
	}

	@ScriptRef
	public static Map getCurrentMap(String arg){
		return map;
	}

	@ScriptRef
	public static String getStartMap(String arg){
		return startMap;
	}

	@ScriptRef
	public static void setMapSc(String m){
		GameLog.write("Script requested map change to " + m + ".");
		Map ma = ResourceCache.getMap(m);
		setMap(ma);
	}

	@ScriptRef
	public static void setMap(Map m){
		globals.addVar("lastmap", map);
		map = m;
		map.addEntity(player);
		globals.addVar("map", map);
		if(m.getScript() != null){
			if(m.getScript() != "")ScriptParser.run(m.getScript(), new VariableManager());
		}
		player.setX(map.getSpawnX());
		player.setY(map.getSpawnY());
	}

	@ScriptRef
	public static Gui setGui(String name){
		GameLog.write("GUI requested change to " + name + ".");
		if(gui != null){
			gui.setActive(false);
			GameLog.write("Old GUI set to inactive. Will be removed.");
		}
		gui = Guis.get(name);
		gui.setActive(true);
		GameLog.write("Loaded GUI " + name + " with " + gui.getComponents().size() + " components.");
		return gui;
	}

	@ScriptRef
	public static Gui getGui(String name){
		return Guis.get(name);
	}

	public static Context getContext(){
		return context;
	}

	public static void setContext(Context c){
		context = c;
	}

	@ScriptRef
	public static float getDelta(){
		return delta;
	}

	@ScriptRef
	public static long getLastTick(){
		return lastTick;
	}

	@ScriptRef
	public static long getTime(){
		return System.currentTimeMillis();
	}

	public static float screenCenterX = -1;
	public static float screenCenterY = -1;

	public static void update(){
		float div = System.currentTimeMillis() - lastTick;
		if(div == 0)div = 1;
		delta = 1 / div;
		lastTick = System.currentTimeMillis();

		if(screenCenterX == -1){
			screenCenterX = getScreenRectF().centerX();
			screenCenterY = getScreenRectF().centerY();
		}

		if(gui != null)gui.update();
		if(map != null){
			map.update();
		}
	}

	private static Paint fpsPaint = null;
	private static Paint bbPaint = null;
	
	public static boolean paused = false;

	@SuppressWarnings("unused")
	public static void render(Canvas canvas){
		if(canvas == null){
			GameLog.write("Supplied canvas cannot be drawn on.");
			return;
		}
		canvas.drawColor(Color.BLACK);
		int bcam = canvas.save();
		camera.applyToCanvas(canvas);
		if(map != null)map.render(canvas);
		canvas.restoreToCount(bcam);
		if(gui.getActive())gui.draw(canvas);
		if(DEBUG){
			if(fpsPaint == null){
				fpsPaint = new Paint();
				fpsPaint.setColor(Color.WHITE);
				fpsPaint.setTypeface(ResourceCache.getFont("default"));
				fpsPaint.setTextSize(12);
				fpsPaint.setTextAlign(Align.LEFT);
				bbPaint = new Paint();
				bbPaint.setColor(Color.RED);
				bbPaint.setTypeface(ResourceCache.getFont("default"));
				bbPaint.setTextSize(8);
				bbPaint.setTextAlign(Align.LEFT);
			}try{
				canvas.drawText("FPS: " + fpsCounter.fps(), 30, 15, fpsPaint);
				canvas.drawText("FREE: " + MemRecord.cFree / 1024 + "K", 30, 30, fpsPaint);
				canvas.drawText("USE: " + MemRecord.cUse / 1024 + "K", 30, 45, fpsPaint);
				if(player != null){
					canvas.drawText("X: " + player.getX(), 30, 60, fpsPaint);
					canvas.drawText("Y: " + player.getY(), 30, 75, fpsPaint);
				}
				RectF camRect = new RectF();
				Matrix m = new Matrix();
				camera.getMatrix(m);
				m.mapRect(camRect);
				canvas.drawText("CamX: " + camRect.left , 30, 90, fpsPaint);
				canvas.drawText("CamY: " + camRect.top, 30, 105, fpsPaint);
				camera.applyToCanvas(canvas);
				if(map != null && DEBUG_DRAW){
					for(Entity e : map.getEntities()){
						RectF r = e.getCollisionBounds();
						canvas.drawLine(r.left, r.top, r.right, r.top, bbPaint);
						canvas.drawLine(r.left, r.top, r.right, r.bottom, bbPaint);
						canvas.drawLine(r.left, r.bottom, r.left, r.top, bbPaint);
						canvas.drawLine(r.right, r.top, r.right, r.bottom, bbPaint);
						canvas.drawLine(r.right, r.bottom, r.left, r.bottom, bbPaint);
						canvas.drawText(e.getName() + " (" + e.getX() + "," + e.getY() + ")", e.getX(), e.getY(), bbPaint);
					}
					for(RectF r : map.getCollisions()){
						canvas.drawLine(r.left, r.top, r.right, r.top, bbPaint);
						canvas.drawLine(r.left, r.top, r.right, r.bottom, bbPaint);
						canvas.drawLine(r.left, r.bottom, r.left, r.top, bbPaint);
						canvas.drawLine(r.right, r.top, r.right, r.bottom, bbPaint);
						canvas.drawLine(r.right, r.bottom, r.left, r.bottom, bbPaint);
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		fpsCounter.update();
	}

	@ScriptRef
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
		return assets.open(file);
	}

	public static Rect resizeRect(Rect rect){
		return new Rect((int)resizeX(rect.left), (int)resizeY(rect.top), (int)resizeX(rect.left) + (int)resizeX(rect.width()), (int)resizeX(rect.bottom) + (int)resizeY(rect.height()));
	}

	public static RectF resizeRect(RectF rect){
		return new RectF(resizeX(rect.left), resizeY(rect.top), resizeX(rect.left), resizeY(rect.bottom));
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
