package com.rodit.pokemans.resource;

import android.graphics.RectF;

public interface IBoundable {

	public float getX();
	public float getY();
	public float getWidth();
	public float getHeight();
	public void setX(float x);
	public void setY(float y);
	public void setWidth(float width);
	public void setHeight(float height);
	public RectF getBounds();
	public RectF getCollisionBounds();
	public boolean collides(IBoundable b);
	public void onCollide(IBoundable b);
}
