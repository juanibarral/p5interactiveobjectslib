package util;

import java.awt.Color;
import java.text.DecimalFormat;

import processing.core.PApplet;
import processing.core.PFont;

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
	private int selectedColor;
	private int size = 5;
	private String text;
	private PFont font;
	private int textSize;
	private boolean withText;
	private boolean withData;
	private double data;
	private boolean withBackground;
	private int colorBackground;

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
		this.selectedColor = Color.BLACK.getRGB();
		this.text = "";
		this.withText = false;
		this.withData = false;
		this.textSize = 14;
		this.withBackground = false;
		this.colorBackground = Color.WHITE.getRGB();
	}
	
	/**
	 * Sets the data associated with the point
	 * @param data the data associated
	 */
	public void setDataValue(double data)
	{
		this.data = data;
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
		this.withText = b;
	}
	
	/**
	 * Sets if the visualization will show the data
	 * @param b if the data is going to be rendered
	 */
	public void renderData(boolean b)
	{
		this.withData = b;
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
		selectedColor = currentColor;
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
		mainApplet.fill(selectedColor);
		mainApplet.stroke(selectedColor);
		mainApplet.ellipse (posX, posY, pointSize, pointSize);
		if(withText || withData)
		{
			String textToRender = text;
			if(withText && withData)
			{
				DecimalFormat df = new DecimalFormat("#.##");
				textToRender = textToRender.concat("\n" + df.format(data));
			}
			else if(!withText && withData)
			{
				DecimalFormat df = new DecimalFormat("#.##");
				textToRender = df.format(data);
			}
			
			float textWidth = mainApplet.textWidth(textToRender);
			if(withBackground)
			{
				mainApplet.fill(colorBackground);
				mainApplet.rect(posX + 5, posY - 4 - textSize, textWidth, textSize);
				mainApplet.fill(color);
			}
			
			if(font != null)
			{
				mainApplet.textFont(font, textSize);
				mainApplet.text(textToRender, posX + 5, posY - 5);
			}
			else
			{
				mainApplet.textSize(textSize);
				mainApplet.text(textToRender, posX + 5, posY - 5);
			}
		}
		
	}
}
