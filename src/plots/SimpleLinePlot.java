package plots;

import java.awt.Color;

import processing.core.PApplet;
import util.AbstractPointPlot;
import util.InteractivePoint;

public class SimpleLinePlot extends AbstractPointPlot{

	protected int gap; 
	protected int colorLine;
	protected int colorSelected;
	protected double minValue;
	protected double maxValue;
	protected int maxLineLength;
	protected int linePosY;

	public SimpleLinePlot()
	{
		super();
		width = 100;
		height = 50;
		gap = 10; 
		colorLine = Color.WHITE.getRGB();
		colorSelected = Color.YELLOW.getRGB();
		minValue = 0;
		maxValue = 1.0;
		renderNodesData = false;
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
		setChanged();
		points.clear();
		for(int i = 0; i < data.length; i++)
		{
			double d = data[i];
			if(d < minValue || d > maxValue)
			{
				throw new Exception ("Data out of range " + d + ", [" + minValue + "," + maxValue + "]");
			}
			float positionInLine = PApplet.map((float)d, (float)minValue, (float)maxValue, 0, (float)maxLineLength);
			InteractivePoint point = new InteractivePoint((int) (posX + gap + positionInLine), linePosY , mainApplet);
			point.setId(i);
			point.setUserData(data[i]);
			if(nodesText!= null)
			{
				point.setText(nodesText[i], null, InteractivePoint.DEFAULT_TEXT_SIZE);
			}
			else
			{
				point.setText(data[i] + "", null, InteractivePoint.DEFAULT_TEXT_SIZE);
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


	public void drawObject()
	{
		if(changed)
		{
			drawBackground();
			drawLine();
			drawPoints();
			changed = false;
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
	
	protected void drawLine()
	{
		mainApplet.strokeWeight(1);
		mainApplet.stroke(colorLine);
		mainApplet.line(posX + gap, linePosY, posX + maxLineLength, linePosY);
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
}
