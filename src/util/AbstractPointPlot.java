package util;

import java.awt.Color;
import java.util.ArrayList;

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
	protected boolean withBackground;
	protected ArrayList<InteractivePoint> points;
	protected boolean renderNodesData;
	protected boolean listenerAdded;
	protected InteractiveObjectListener listener;
	protected String[] nodesText;
	protected int[] nodesColor;
	
	/**
	 * Basic Constructor
	 */
	public AbstractPointPlot()
	{
		posX = 0;
		posY = 0;
		width = 100;
		height = 100;
		colorBackground = Color.DARK_GRAY.getRGB();
		withBackground = true;
		points = new ArrayList<InteractivePoint>();
		renderNodesData = false;
		listenerAdded = false;
	}
	
	/**
	 * If render the data of each node
	 * @param b if the data will be rendered
	 */
	public void renderNodesData(boolean b)
	{
		this.renderNodesData= b;

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
	
	
}
