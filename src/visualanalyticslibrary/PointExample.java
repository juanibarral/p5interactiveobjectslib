package visualanalyticslibrary;

import java.awt.Color;

import processing.core.PApplet;
import util.VAPoint;

public class PointExample extends PApplet{

	private VAPoint point;
	
	public void setup()
	{
		point = new VAPoint(250, 250, this);
		size(500, 500);
	}
	
	public void draw()
	{
		if(point.mouseIsOverFeedback())
		{
			background(Color.white.getRGB());
		}
		point.drawPoint();
	}
}
