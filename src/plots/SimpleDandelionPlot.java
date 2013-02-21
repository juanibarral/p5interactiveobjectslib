package plots;

import java.awt.Color;
import java.util.ArrayList;

import processing.core.PApplet;

import util.PickeableObject;
import util.PickeableObjectListener;
import util.VAPoint;

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
	}

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

	public void setRange(double min, double max)
	{
		this.minValue = min;
		this.maxValue = max;
	}

	public void setData(double[] data) throws Exception
	{
		float radiusGap = 360.0f / (float)(data.length);
		for(int i = 0; i < data.length; i++)
		{
			double d = data[i];
			float radius = PApplet.map((float)d, (float)minValue, (float)maxValue, 0, (float)maxLineLength);
			int posX = (int) (radius * Math.cos(Math.toRadians(radiusGap  * i)));
			int posY = (int) (radius * Math.sin(Math.toRadians(radiusGap  * i)));

			VAPoint point = new VAPoint(centerX + posX, centerY - posY, mainApplet);
			point.setId(i);
			point.setDataValue(data[i]);
			point.setColor(colorLine);
			point.setSelectedColor(colorSelected);
			point.setBackgroundColor(colorBackground);
			point.renderBackground(true);
			points.add(point);
		}
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
		for(VAPoint p : points)
		{
			p.addListener(listener);
		}
	}

	public void renderNodesData(boolean b)
	{
		for(VAPoint p : points)
		{
			p.renderData(b);
		}
	}
}
