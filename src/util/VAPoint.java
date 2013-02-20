package util;

import java.awt.Color;

import processing.core.PApplet;

/**
 * Represent a pickeable point.
 * @author Juan Camilo Ibarra
 * @version 0.5b
 *
 */
public class VAPoint extends PickeableObject{

	private int posX;
	private int posY;
	private int color;
	public int size = 5;

	/**
	 * Basic constructor
	 * @param posX position X in pixels of the point
	 * @param posY position Y in pixels of the point
	 * @param mainApplet
	 */
	public VAPoint(int posX, int posY,PApplet mainApplet)
	{
		super(mainApplet);
		this.posX = posX;
		this.posY = posY;
		this.color = Color.BLACK.getRGB();
	}
	/**
	 * Sets the size of the point
	 * @param size size of the point in pixels
	 */
	public void setSize(int size)
	{
		this.size = size;
	}

	/**
	 * Returns if the mouse is over the point. 
	 * By default the mouse is over if the distance of the mouse pointer
	 * to the point center is less that the point size
	 * @return if the mouse is over the point
	 */
	public boolean mouseIsOver (){
		float disX = posX - mainApplet.mouseX;
		float disY = posY - mainApplet.mouseY;

		if((int)(PApplet.sqrt(PApplet.sq(disX) + PApplet.sq(disY))) < size) {
			return true;
		}else{
			return false;
		}
	}

	/**
	 * Returns the X position of the point
	 * @return the X position in pixels
	 */
	public int getPosX() {
		return posX;
	}

	/**
	 * Returns the Y position of the point
	 * @return the Y position in pixels
	 */
	public int getPosY() {
		return posY;
	}

	/**
	 * Sets the color of the point
	 * @param currentColor RGB color value
	 */
	public void setColor(int currentColor) {
		color = currentColor;
	}
	/**
	 * Draw the point only if it is necessary (if the state has changed)
	 */
	public void drawPoint()
	{
		if(changed)
		{			
			if(isSelected())
			{
				drawSelectedState();
			}
			else
			{
				drawUnselectedState();
			}
			changed = false;
		}
	}
	
	protected void drawUnselectedState()
	{
		mainApplet.fill(color);
		mainApplet.stroke(color);
		mainApplet.ellipse (posX, posY, size, size);
	}
	
	protected void drawSelectedState()
	{
		int pointSize = size * 2;
		mainApplet.fill(color);
		mainApplet.stroke(color);
		mainApplet.ellipse (posX, posY, pointSize, pointSize);
	}
}
