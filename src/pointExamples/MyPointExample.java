package pointExamples;

import java.awt.Color;

import processing.core.PApplet;
import util.InteractiveObjectEvent;
import util.InteractiveObjectListener;

public class MyPointExample extends PApplet implements InteractiveObjectListener{

	private static final long serialVersionUID = 1L;
	private MyPoint point;
	
	public void setup()
	{
		point = new MyPoint(250, 250, this);
		point.addListener(this);
		point.setImages("selected.png", "unselected.png");
		point.setImageSize(200, 200);
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
