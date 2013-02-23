package pointExamples;

import java.awt.Color;
import processing.core.PApplet;
import util.InteractiveObjectEvent;
import util.InteractiveObjectListener;
import util.InteractivePoint;

public class PointListenerExample extends PApplet implements InteractiveObjectListener{

	private static final long serialVersionUID = 1L;
	private InteractivePoint point;
	
	public void setup()
	{
		point = new InteractivePoint(250, 250, this);
		point.setId(1);
		point.addListener(this);
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
	public void eventTriggered(InteractiveObjectEvent event){
		System.out.println("message from " + event.getSource().getClass().getName() + " id: " +  event.getSource().getId() + " event: " + event.getEventType());
	}
}
