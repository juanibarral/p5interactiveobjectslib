package plots;

import java.awt.Color;
import java.util.ArrayList;

import processing.core.PApplet;

import util.PickeableObject;
import util.PickeableObjectListener;
import util.VAPoint;

/**
 * It is a Class that represents a radial plot that looks like a dandelion
 * Each axis is rendered from the center to a point along a circle of radius max value of the data
 * Each data is rendered in one axis, its distance to the center is proportional to the data value
 * @author Juan Camilo Ibarra
 * @version 0.5b
 */
public class SimpleDandelionPlot extends PickeableObject {
	private int posY;
	private int posX;
	private int width;
	private int gap; 
	private int colorLine;
	private int colorSelected;
	private int colorBackground;
	private double minValue;
	private double maxValue;
	private int maxLineLength;
	private int centerX;
	private int centerY;
	private boolean withBackground;
	private ArrayList<VAPoint> points;
	private PApplet mainApplet;
	private boolean listenerAdded;
	private boolean renderNodesData;
	private PickeableObjectListener listener;
	private String[] nodesText;
	private int[] nodesColor;

	/**
	 * Simple Constructor
	 */
	public SimpleDandelionPlot()
	{
		width = 100;
		gap = 10; 
		colorLine = Color.WHITE.getRGB();
		colorSelected = Color.YELLOW.getRGB();
		colorBackground = Color.DARK_GRAY.getRGB();
		minValue = 0;
		maxValue = 1.0;
		maxLineLength = 40;
		centerX = 50;
		centerY = 50;
		withBackground = true;
		points = new ArrayList<VAPoint>();
		listenerAdded = false;
		renderNodesData = false;
	}

	/**
	 * Simple constructor
	 * @param mainApplet the applet where the plot is going to be rendered
	 * @param posX upper left position in X for the plot
	 * @param posY upper left position in Y for the plot
	 * @param width width of the plot
	 */
	public SimpleDandelionPlot(PApplet mainApplet, int posX, int posY, int width)
	{
		this();
		this.mainApplet = mainApplet;
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.maxLineLength = (int)((float)(width - gap) / 2.0f);
		this.centerX = this.posX + (int)((float)width / 2);
		this.centerY = this.posY + (int)((float)width / 2);
	}

	/**
	 * Sets the ranges for the data values
	 * @param min minimum value
	 * @param max maximum value
	 */
	public void setRange(double min, double max)
	{
		this.minValue = min;
		this.maxValue = max;
	}

	/**
	 * Sets the data for the plot
	 * @param data the data 
	 * @throws Exception if the data is out of range
	 */
	public void setData(double[] data) throws Exception
	{
		points.clear();
		float radiusGap = 360.0f / (float)(data.length);
		for(int i = 0; i < data.length; i++)
		{
			double d = data[i];
			if(d < minValue || d > maxValue)
			{
				throw new Exception ("Data out of range " + d + ", [" + minValue + "," + maxValue + "]");
			}
			float radius = PApplet.map((float)d, (float)minValue, (float)maxValue, 0, (float)maxLineLength);
			int posX = (int) (radius * Math.cos(Math.toRadians(radiusGap  * i)));
			int posY = (int) (radius * Math.sin(Math.toRadians(radiusGap  * i)));

			VAPoint point = new VAPoint(centerX + posX, centerY - posY, mainApplet);
			point.setId(i);
			point.setUserData(data[i]);
			if(nodesText!= null)
			{
				point.setText(nodesText[i], null, VAPoint.DEFAULT_TEXT_SIZE);
			}
			else
			{
				point.setText(data[i] + "", null, VAPoint.DEFAULT_TEXT_SIZE);
			}
			if(nodesColor != null)
			{
				point.setColor(nodesColor[i]);
			}
			else
			{
				point.setColor(colorLine);
			}
			point.setSelectedColor(colorSelected);
			point.setBackgroundColor(colorBackground);
			point.renderBackground(true);
			if(listenerAdded && listener != null)
			{
				point.addListener(listener);
			}
			point.renderText(renderNodesData);
			points.add(point);
		}
	}
	
	/**
	 * Sets the color for the unselected nodes
	 * @param colors list of colors in RGB
	 */
	public void setNodeColors(int[] colors)
	{
		nodesColor = colors;
	}
	
	public void setNodesText(String[] text)
	{
		nodesText = text;
	}

	public boolean mouseIsOver()
	{
		boolean noneSelected = true;
		boolean mouseIsOver = false;
		for(VAPoint p : points)
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
	 * Draws the plot
	 */
	public void drawPlot()
	{
		if(changed)
		{
			if(withBackground)
			{
				mainApplet.fill(colorBackground);
				mainApplet.stroke(colorBackground);
				mainApplet.strokeWeight(1);
				mainApplet.rect((float)posX, (float)posY, (float)width, (float)width);
			}
			for(VAPoint p : points)
			{
				mainApplet.strokeWeight(1);
				mainApplet.stroke(colorLine);
				mainApplet.line(centerX, centerY, p.getPosX(), p.getPosY());

			}
			for(VAPoint p : points)
			{
				p.mouseIsOverFeedback();
				p.setChanged();
				p.drawPoint();
			}
		}
	}

	public void addListener(PickeableObjectListener listener)
	{
		listenerAdded = true;
		this.listener = listener;
	}

	/**
	 * If render the data of each node
	 * @param b if the data will be rendered
	 */
	public void renderNodesData(boolean b)
	{
		this.renderNodesData= b;

	}
}
