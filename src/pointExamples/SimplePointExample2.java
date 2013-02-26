package pointExamples;

import java.awt.Color;

import basic.InteractivePoint;
import processing.core.PApplet;

public class SimplePointExample2 extends PApplet {

	private static final long serialVersionUID = 1L;
	private InteractivePoint point;
	
	public void setup()
	{
		point = new InteractivePoint(100,100,this);
		point.setSize(50);
		point.setText("This is a point", null, 30);
		point.renderText(true);
		point.setTextColor(Color.RED.getRGB());
		point.setBackgroundColor(Color.LIGHT_GRAY.getRGB());
		point.renderBackground(true);
		size(500,500);
	}
	
	public void draw()
	{
		if(point.mouseIsOverFeedback())
		{
			background(Color.DARK_GRAY.getRGB());
		}
		point.drawObject();
	}
}
