package plots;

import java.awt.Color;

import javax.sound.sampled.DataLine;

import basic.InteractivePoint;

import processing.core.PApplet;
import util.AbstractPointPlot;

/**
 * It is a Class that represents a radial plot that looks like a dandelion
 * Each axis is rendered from the center to a point along a circle of radius max value of the data
 * Each data is rendered in one axis, its distance to the center is proportional to the data value
 * @author Juan Camilo Ibarra
 * @version 0.5b
 */
public class SimpleDandelionPlot extends AbstractPointPlot {
	protected int gap; 
	protected int colorLine;
	protected int colorSelected;
	protected double minValue;
	protected double maxValue;
	protected int maxLineLength;
	protected int centerX;
	protected int centerY;
	protected int centerOffset;
	protected int[][] lines;

	/**
	 * Simple Constructor
	 */
	public SimpleDandelionPlot()
	{
		super();
		width = 100;
		gap = 10; 
		colorLine = Color.WHITE.getRGB();
		colorSelected = Color.YELLOW.getRGB();
		minValue = 0;
		maxValue = 1.0;
		maxLineLength = 40;
		centerX = 50;
		centerY = 50;
		centerOffset = 0;

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
	 * Sets the center offset, the distance from the center where the axis will begin
	 * @param offset in pixels
	 */
	public void setCenterOffset(int offset)
	{
		this.centerOffset = offset;
		this.maxLineLength = (int)((float)(width - gap) / 2.0f);
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
		lines = new int[data.length][2];
		points.clear();
		float radiusGap = 360.0f / (float)(data.length);
		for(int i = 0; i < data.length; i++)
		{
			double d = data[i];
			if(d < minValue || d > maxValue)
			{
				throw new Exception ("Data out of range " + d + ", [" + minValue + "," + maxValue + "]");
			}
			float radius = PApplet.map((float)d, (float)minValue, (float)maxValue, centerOffset, (float)maxLineLength);

			int posX = (int) (radius * Math.cos(Math.toRadians(radiusGap  * i)));
			int posY = (int) (radius * Math.sin(Math.toRadians(radiusGap  * i)));

			lines[i][0] = centerX + posX;
			lines[i][1] = centerY - posY;
			InteractivePoint point = new InteractivePoint(centerX + posX, centerY - posY, mainApplet);
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
		setChanged();
	}

	/**
	 * Sets the data for the plot
	 * @param data the data 
	 * @throws Exception if the data is out of range
	 */
	public void setData(double[][] data) throws Exception
	{
		lines = new int[data.length][2];
		points.clear();
		float radiusGap = 360.0f / (float)(data.length);
		for(int i = 0; i < data.length; i++)
		{
			double maxData = 0;
			for(int j = 0; j < data[i].length; j++)
			{
				double d = data[i][j];
				if(d < minValue || d > maxValue)
				{
					throw new Exception ("Data out of range " + d + ", [" + minValue + "," + maxValue + "]");
				}
				
				if(maxData < d)
				{
					maxData = d;
				}
				
				float radius = PApplet.map((float)d, (float)minValue, (float)maxValue, centerOffset, (float)maxLineLength);

				int posX = (int) (radius * Math.cos(Math.toRadians(radiusGap  * i)));
				int posY = (int) (radius * Math.sin(Math.toRadians(radiusGap  * i)));

				InteractivePoint point = new InteractivePoint(centerX + posX, centerY - posY, mainApplet);
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
			
			float radius = PApplet.map((float)maxData, (float)minValue, (float)maxValue, centerOffset, (float)maxLineLength);

			lines[i][0] = centerX + (int) (radius * Math.cos(Math.toRadians(radiusGap  * i)));
			lines[i][1] = centerY - (int) (radius * Math.sin(Math.toRadians(radiusGap  * i)));
		}
		setChanged();
	}


	public void drawObject()
	{
		if(changed)
		{
			drawBackground();
			drawLines();
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
			mainApplet.rect((float)posX, (float)posY, (float)width, (float)width);
		}
	}
	protected void drawLines()
	{
//		for(InteractivePoint p : points)
//		{
//			mainApplet.strokeWeight(1);
//			mainApplet.stroke(colorLine);
//
//			int posX = p.getPosX() - centerX;
//			int posY = p.getPosY() - centerY;
//			int distance = (int) Math.sqrt(( posX * posX) + (posY * posY));
//			int initX = (int)((float)posX / (float)distance * centerOffset);
//			int initY = (int)((float)posY / (float)distance * centerOffset);
//
//			mainApplet.line(centerX + initX, centerY + initY, p.getPosX(), p.getPosY());
//
//		}
		if(lines != null)
		{
			for(int i = 0; i < lines.length; i++)
			{
				mainApplet.strokeWeight(1);
				mainApplet.stroke(colorLine);

				int endX = lines[i][0] - centerX;
				int endY = lines[i][1] - centerY;
				int distance = (int) Math.sqrt(( endX * endX) + (endY * endY));
				int initX = (int)((float)endX / (float)distance * centerOffset);
				int initY = (int)((float)endY / (float)distance * centerOffset);

				mainApplet.line(centerX + initX, centerY + initY, endX + centerX, endY + centerY);

			}
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
	
}
