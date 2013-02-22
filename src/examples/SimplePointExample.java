package examples;

import java.awt.Color;
import processing.core.PApplet;
import util.VAPoint;

public class SimplePointExample extends PApplet {

	private static final long serialVersionUID = 1L;
	private VAPoint point;
	
	public void setup()
	{
		point = new VAPoint(100,100,this);
		size(200,200);
	}
	
	public void draw()
	{
		if(point.mouseIsOverFeedback())
		{
			background(Color.LIGHT_GRAY.getRGB());
		}
		point.drawObject();
	}
}
