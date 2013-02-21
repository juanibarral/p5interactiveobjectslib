package visualanalyticslibrary;

import java.awt.Color;

import processing.core.PApplet;
import util.PickeableObject;
import util.PickeableObjectListener;
import util.VAPoint;

public class PointExample extends PApplet implements PickeableObjectListener{

	private VAPoint point;
	
	public void setup()
	{
		point = new VAPoint(250, 250, this);
		point.setText("Text", null, 15);
		point.setDataValue(3.2564);
//		point.renderData(true);
		point.renderText(true);
		point.addListener(this);
		point.setBackgroundColor(Color.RED.getRGB());
		point.renderBackground(true);
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

	@Override
	public void update(PickeableObject object, Object message) {

		if(message instanceof Integer)
		{
			System.out.println("message from " + message);
		}
	}
}
