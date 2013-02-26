package pointExamples;

import java.awt.Color;

import basic.InteractivePoint;
import processing.core.PApplet;

public class SimplePointExample extends PApplet {

	private static final long serialVersionUID = 1L;
	private InteractivePoint point;
	
	public void setup()
	{
		point = new InteractivePoint(100,100,this);
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
