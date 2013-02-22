package util;

import java.awt.Color;

import processing.core.PApplet;
import processing.core.PFont;

/**
 * Represent a pickeable point.
 * @author Juan Camilo Ibarra
 * @version 0.5b
 *
 */
public class VAPoint extends PickeableObject{

	public static final int DEFAULT_TEXT_SIZE = 14;
	protected int posX;
	protected int posY;
	protected int color;
	protected int colorSelected;
	protected int size = 5;
	protected String text;
	protected PFont font;
	protected int textSize;
	protected boolean renderText;
	protected boolean withBackground;
	protected int colorBackground;
	protected int colorText;

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
		this.colorSelected = Color.BLACK.getRGB();
		this.text = "";
		this.renderText = false;
		this.textSize = DEFAULT_TEXT_SIZE;
		this.withBackground = false;
		this.colorBackground = Color.WHITE.getRGB();
		this.colorText = Color.BLACK.getRGB();
	}
	
	/**
	 * Sets the text that will be visible when picked
	 * @param text text to be rendered
	 * @param font font for the text (it could be null)
	 * @param size size of the text to be rendered
	 */
	public void setText(String text, PFont font, int size)
	{
		this.text = text;
		this.font = font;
		this.textSize = size;
	}
	
	/**
	 * Sets if the visualization will show the text
	 * @param b if the text is going to be rendered
	 */
	public void renderText(boolean b)
	{
		this.renderText = b;
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
	 * Sets the color of the point
	 * @param currentColor RGB color value
	 */
	public void setSelectedColor(int currentColor) {
		colorSelected = currentColor;
	}
	
	/**
	 * Sets the color for the text
	 * @param color color RGB value
	 */
	public void setTextColor(int color)
	{
		colorText = color;
	}
	
	public void drawObject()
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
	
	/**
	 * Sets the color of the background of the text rendered
	 * @param color colro fo the background
	 */
	public void setBackgroundColor(int color)
	{
		this.colorBackground = color;
	}
	
	/**
	 * Sets if render background with the text
	 * @param b if render or not background
	 */
	public void renderBackground(boolean b)
	{
		this.withBackground = b;
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
		mainApplet.fill(colorSelected);
		mainApplet.stroke(colorSelected);
		mainApplet.ellipse (posX, posY, pointSize, pointSize);
		if(renderText)
		{	
			float textWidth = mainApplet.textWidth(text);
			if(withBackground)
			{
				mainApplet.fill(colorBackground);
				mainApplet.rect(posX + 5, posY - 4 - textSize, textWidth, textSize);
				mainApplet.fill(color);
			}
			
			mainApplet.fill(colorText);
			if(font != null)
			{
				mainApplet.textFont(font, textSize);
				mainApplet.text(text, posX + 5, posY - 5);
			}
			else
			{
				mainApplet.textSize(textSize);
				mainApplet.text(text, posX + 5, posY - 5);
			}
			mainApplet.fill(color);
		}
		
	}
}
