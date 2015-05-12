package com.rodit.pokemans.gui.component;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.rodit.pokemans.Game;
import com.rodit.pokemans.gui.Gui;
import com.rodit.pokemans.resource.ResourceCache;

public class GuiComponent {

	private boolean visible = true;
	private String id = "null";
	private String text = "";
	private String font = "default";
	private Paint.Align align;
	private float fontSize = 10;
	private float x = 0, y = 0, width = 0, height = 0;
	private int color = Color.BLACK;
	private Gui parent;
	private String background = "";
	
	public GuiComponent(){
		
	}
	
	public GuiComponent(Gui parent){
		this.parent = parent;
	}
	
	public boolean getVisible(){
		return visible;
	}
	
	public void setVisible(boolean visible){
		this.visible = visible;
	}

	public String getID(){
		return id;
	}

	public void setID(String id){
		this.id = id;
	}

	public String getText(){
		return text;
	}

	public void setText(String text){
		this.text = text;
	}

	public String getFont(){
		return font;
	}

	public void setFont(String font){
		getPaint().setTypeface(ResourceCache.getFont(font));
		this.font = font;
	}
	
	public Paint.Align getAlign(){
		return align;
	}
	
	public void setAlign(Paint.Align align){
		getPaint().setTextAlign(align);
		this.align = align;
	}

	public float getFontSize(){
		return fontSize;
	}

	public void setFontSize(float size){
		getPaint().setTextSize(size);
		this.fontSize = size;
	}

	public float getX(){
		return x;
	}
	
	public void setX(float x){
		this.x = x;
	}
	
	public float getY(){
		return y;
	}
	
	public void setY(float y){
		this.y = y;
	}
	
	public float getWidth(){
		return width;
	}
	
	public void setWidth(float width){
		this.width = width;
	}
	
	public float getHeight(){
		return height;
	}

	public void setHeight(float height){
		this.height = height;
	}
	
	public int getColor(){
		return color;
	}
	
	public void setColor(int color){
		getPaint().setColor(color);
		this.color = color;
	}
	
	public Gui getParent(){
		return parent;
	}
	
	public void setParent(Gui parent){
		this.parent = parent;
	}
	
	public String getBackground(){
		return background;
	}
	
	public void setBackground(String background){
		this.background = background;
	}
	
	public Bitmap getBGBitmap(){
		return ResourceCache.getImage(background);
	}
	
	public void paint(Canvas canvas){
		if(background != ""){
			Bitmap bgbit = getBGBitmap();
			canvas.drawBitmap(bgbit, new Rect(0, 0, bgbit.getWidth(), bgbit.getHeight()), Game.resizeRect(new RectF(x, y, width, height)), getPaint());
		}
		if(text != ""){
			canvas.drawText(text, Game.resizeX(x), Game.resizeY(y), getPaint());
		}
	}
	
	private Paint paintCache = null;
	public Paint getPaint(){
		if(paintCache == null){
			paintCache = new Paint();
		}
		return paintCache;
	}
}
