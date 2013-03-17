package util;

import java.awt.Color;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PFont;

import basic.InteractivePoint;

/**
 * It is a Class that represent a plot that has a set of interactive points that could be selected
 * @author Juan Camilo Ibarra
 * @version 0.5b
 */
public abstract class AbstractPointPlot extends AbstractInteractiveObject{

	protected int posY;
	protected int posX;
	protected int width;
	protected int height;
	protected int colorBackground;
	protected int colorTitle;
	protected int pointsColorText;
	protected boolean withBackground;
	protected ArrayList<InteractivePoint> points;
	protected boolean renderNodesText;
	protected boolean listenerAdded;
	protected InteractiveObjectListener listener;
	protected String[] nodesText;
	protected String[][] nodesText2;
	protected int[] nodesColor;
	
	protected int hGap;
	protected int vGap;
	
	//FOR TITLE
	protected boolean withTitle;
	protected String title;
	protected PFont titleFont;
	protected int titlePosX;
	protected int titlePosY;
	
	/**
	 * Basic Constructor
	 */
	public AbstractPointPlot(PApplet mainApplet)
	{
		super(mainApplet);
		posX = 0;
		posY = 0;
		width = 100;
		height = 100;
		colorBackground = Color.DARK_GRAY.getRGB();
		pointsColorText = Color.WHITE.getRGB();
		colorTitle = Color.WHITE.getRGB();
		withBackground = true;
		points = new ArrayList<InteractivePoint>();
		renderNodesText = false;
		listenerAdded = false;
		withTitle = false;
		titleFont = basicFont;
		hGap = 5;
		vGap = 5;
		title = "Title";
	}
	

	
	public void setTitleFont(PFont font)
	{
		titleFont = font;
		calculateTitlePos();
	}
	
	protected void calculateTitlePos()
	{
		titlePosX = posX + hGap;
		titlePosY = posY + vGap + titleFont.getSize();
	}
	
	protected void drawTitle()
	{
		if(withTitle)
		{
			mainApplet.textFont(titleFont);
			mainApplet.fill(colorTitle);
			mainApplet.text(title, titlePosX, titlePosY);
		}
	}
	
	protected void drawBackground()
	{
		if(withBackground)
		{
			mainApplet.fill(colorBackground);
			mainApplet.stroke(colorBackground);
			mainApplet.strokeWeight(1);
			mainApplet.rect((float)posX, (float)posY, (float)width, (float)height);
		}
	}
	
	/**
	 * If render the data of each node
	 * @param b if the data will be rendered
	 */
	public void renderNodesText(boolean b)
	{
		this.renderNodesText= b;

	}
	
	
	/**
	 * Set the new points for this plot
	 * @param newPoints the new points
	 */
	public void setNewPoints(ArrayList<InteractivePoint> newPoints)
	{
		ArrayList<InteractivePoint> clonedPoints = new ArrayList<InteractivePoint>();
		for(int i = 0; i < points.size(); i++)
		{
			InteractivePoint p = newPoints.get(i);
			p.setValues(points.get(i));
			clonedPoints.add(p);
		}
		points.clear();
		points.addAll(clonedPoints);
	}
	
	public void setPointsUserData(Object[] pointsUserData)
	{
		for(int i = 0; i < points.size(); i++)
		{
			points.get(i).setUserData(pointsUserData[i]);
		}
	}
	
	public void addListener(InteractiveObjectListener listener)
	{
		listenerAdded = true;
		this.listener = listener;
	}
	
	/**
	 * Sets the color for the unselected nodes
	 * @param colors list of colors in RGB
	 */
	public void setNodeColors(int[] colors)
	{
		nodesColor = colors;
	}
	
	/**
	 * Sets the text that will be along the points
	 * @param text test to show
	 */
	public void setNodesText(String[] text)
	{
		nodesText = text;
	}
	
	public void withBackground(boolean b)
	{
		withBackground = b;
	}
	
	public void setColorTextForPoints(int color)
	{
		pointsColorText = color;
	}
	
	public boolean mouseIsOver()
	{
		boolean noneSelected = true;
		boolean mouseIsOver = false;
		for(InteractivePoint p : points)
		{
			if(p.mouseIsOver())
			{
				mouseIsOver = true;
				noneSelected = false;
				break;
			}
		}
		if(noneSelected)
		{
			mouseIsOver = false;
		}
		return mouseIsOver;
	}

	/**
	 * @return the posY
	 */
	public int getPosY() {
		return posY;
	}

	/**
	 * @return the posX
	 */
	public int getPosX() {
		return posX;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}
	
	public void drawObject()
	{
		super.drawObject();
		if(changed)
		{
			drawBackground();
			drawTitle();
		}
	}
	
	protected void drawPoints()
	{
		for(InteractivePoint p : points)
		{
			p.mouseIsOverFeedback();
			p.setChanged();
			p.drawObject();
		}
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setColorBackground(int colorBackground) {
		this.colorBackground = colorBackground;
	}

	public void setPointsColorText(int pointsColorText) {
		this.pointsColorText = pointsColorText;
	}

	public void setWithBackground(boolean withBackground) {
		this.withBackground = withBackground;
	}

	public void setPoints(ArrayList<InteractivePoint> points) {
		this.points = points;
	}

	public void setRenderNodesText(boolean renderNodesText) {
		this.renderNodesText = renderNodesText;
	}

	public void setListenerAdded(boolean listenerAdded) {
		this.listenerAdded = listenerAdded;
	}

	public void setListener(InteractiveObjectListener listener) {
		this.listener = listener;
	}

	public void setNodesColor(int[] nodesColor) {
		this.nodesColor = nodesColor;
	}

	public void sethGap(int hGap) {
		this.hGap = hGap;
	}

	public void setvGap(int vGap) {
		this.vGap = vGap;
	}

	public void setWithTitle(boolean withTitle) {
		this.withTitle = withTitle;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTitlePosX(int titlePosX) {
		this.titlePosX = titlePosX;
	}

	public void setTitlePosY(int titlePosY) {
		this.titlePosY = titlePosY;
	}
	
}
