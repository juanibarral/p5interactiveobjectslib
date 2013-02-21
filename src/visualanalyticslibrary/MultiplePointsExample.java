package visualanalyticslibrary;

import java.awt.Color;

import processing.core.PApplet;

import util.PickeableObject;
import util.PickeableObjectListener;
import util.VAPoint;

public class MultiplePointsExample extends PApplet implements PickeableObjectListener{

	private VAPoint[] points;

	public void setup()
	{
		int sizeX = 500;
		int sizeY = 500;

		size(sizeX, sizeY);

		int numberOfPoints = 100;
		points = new VAPoint[numberOfPoints];
		for(int i = 0; i < numberOfPoints; i ++)
		{
			points[i] = new VAPoint((int)random(0, 500), (int)random(0,500), this);
			points[i].setId(i);
			points[i].addListener(this);
			points[i].setUserData(i);
			points[i].setText(i + "", null, VAPoint.DEFAULT_TEXT_SIZE);
			points[i].renderText(true);
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
				background(Color.WHITE.getRGB());
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

	@Override
	public void update(PickeableObject object) {
		System.out.println("message from " + object.getClass().getName() + " id: " + object.getId());
		
	}
}
