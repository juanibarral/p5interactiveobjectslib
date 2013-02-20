package visualanalyticslibrary;

import java.awt.Color;

import processing.core.PApplet;

import util.VAPoint;

public class MultiplePointsExample extends PApplet{

	private VAPoint[] points;

	public void setup()
	{
		int sizeX = 500;
		int sizeY = 500;

		size(sizeX, sizeY);

		int numberOfPoints = 300;
		points = new VAPoint[numberOfPoints];
		for(int i = 0; i < numberOfPoints; i ++)
		{
			points[i] = new VAPoint((int)random(0, 500), (int)random(0,500), this);
		}
	}

	public void draw()
	{
		int pointChangedIndex = -1;
		for(int i = 0; i < points.length; i++)
		{
			VAPoint point = points[i];
			if(point.mouseIsOverFeedback())
			{
				background(Color.white.getRGB());
				pointChangedIndex = i;
				break;
			}
		}
		
		for(int i = 0; i < points.length; i++)
		{
			VAPoint point = points[i];
			if(pointChangedIndex != -1 && i != pointChangedIndex)
			{
				point.setChanged();
			}
			point.drawPoint();
		}
	}
}
