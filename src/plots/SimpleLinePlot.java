package plots;

import java.awt.Color;
import java.util.ArrayList;

import processing.core.PApplet;
import util.PickeableObject;
import util.PickeableObjectListener;
import util.VAPoint;

public class SimpleLinePlot extends PickeableObject{
	protected int posY;
	protected int posX;
	protected int width;
	protected int height;
	protected int gap; 
	protected int colorLine;
	protected int colorSelected;
	protected int colorBackground;
	protected double minValue;
	protected double maxValue;
	protected boolean withBackground;
	protected ArrayList<VAPoint> points;
	protected PApplet mainApplet;
	protected boolean listenerAdded;
	protected boolean renderNodesData;
	protected PickeableObjectListener listener;
	protected String[] nodesText;
	protected int[] nodesColor;
	protected int maxLineLength;
	protected int linePosY;

	public SimpleLinePlot()
	{
		width = 100;
		height = 50;
		gap = 10; 
		colorLine = Color.WHITE.getRGB();
		colorSelected = Color.YELLOW.getRGB();
		colorBackground = Color.DARK_GRAY.getRGB();
		minValue = 0;
		maxValue = 1.0;
		withBackground = true;
		points = new ArrayList<VAPoint>();
		listenerAdded = false;
		renderNodesData = false;
		listenerAdded = false;
		renderNodesData = false;
		points = new ArrayList<VAPoint>();
		maxLineLength = 80;
		linePosY = 25;
	}
	
	public SimpleLinePlot(PApplet applet, int posX, int posY, int width, int height)
	{
		this();
		mainApplet = applet;
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
		
		maxLineLength = width - (2*gap);
		linePosY = this.posY + (int)((float)height/2.0f);
	}
	
	public void setData(double[] data) throws Exception
	{
		for(int i = 0; i < data.length; i++)
		{
			double d = data[i];
			if(d < minValue || d > maxValue)
			{
				throw new Exception ("Data out of range " + d + ", [" + minValue + "," + maxValue + "]");
			}
			float positionInLine = PApplet.map((float)d, (float)minValue, (float)maxValue, 0, (float)maxLineLength);
			VAPoint point = new VAPoint((int) (posX + gap + positionInLine), linePosY , mainApplet);
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


	public void drawObject()
	{
		if(changed)
		{
			if(withBackground)
			{
				mainApplet.fill(colorBackground);
				mainApplet.stroke(colorBackground);
				mainApplet.strokeWeight(1);
				mainApplet.rect((float)posX, (float)posY, (float)width, (float)height);
			}
			
			mainApplet.strokeWeight(1);
			mainApplet.stroke(colorLine);
			mainApplet.line(posX + gap, linePosY, posX + maxLineLength, linePosY);

			for(VAPoint p : points)
			{
				p.mouseIsOverFeedback();
				p.setChanged();
				p.drawObject();
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
	
	public void setNewPointType(String pointClassName)
	{
		ArrayList<VAPoint> clones = new ArrayList<VAPoint>();
		for(VAPoint point : points)
		{
			PickeableObject instance = null;
			Class<?> resource;
			try {
				resource = Class.forName(pointClassName);			
				instance = (PickeableObject)resource.newInstance();
				instance = (VAPoint) point.clone();
				clones.add((VAPoint)instance);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		points.clear();
		points.addAll(clones);
	}
}
