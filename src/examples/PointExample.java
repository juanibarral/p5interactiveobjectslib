package examples;

import java.awt.Color;
import processing.core.PApplet;
import util.PickeableObjectEvent;
import util.PickeableObjectListener;
import util.VAPoint;

public class PointExample extends PApplet implements PickeableObjectListener{

	private static final long serialVersionUID = 1L;
	private VAPoint point;
	
	public void setup()
	{
		double data = 3.2564;
		point = new VAPoint(250, 250, this);
		point.setUserData(data);
		point.setText("Data: " + data, null, VAPoint.DEFAULT_TEXT_SIZE);
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
		point.drawObject();
	}

	@Override
	public void eventTriggered(PickeableObjectEvent event){
		System.out.println("message from " + event.getSource().getClass().getName() + " id: " +  event.getSource().getId() + " event: " + event.getEventType());
	}
}
