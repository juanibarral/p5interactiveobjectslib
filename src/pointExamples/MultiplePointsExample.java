package pointExamples;

import java.awt.Color;
import processing.core.PApplet;
import util.InteractiveObjectsArray;
import util.InteractiveObjectEvent;
import util.InteractiveObjectListener;
import util.InteractivePoint;

public class MultiplePointsExample extends PApplet implements InteractiveObjectListener{


	private static final long serialVersionUID = 1L;
	private InteractiveObjectsArray array;
	public void setup()
	{
		int sizeX = 500;
		int sizeY = 500;
		size(sizeX, sizeY);
		int numberOfPoints = 100;	
		array = new InteractiveObjectsArray(this);
		for(int i = 0; i < numberOfPoints; i ++)
		{
			InteractivePoint point = new InteractivePoint((int)random(0, 500), (int)random(0,500), this);
			point.setId(i);
			point.addListener(this);
			point.setText(i + "", null, InteractivePoint.DEFAULT_TEXT_SIZE);
			point.renderText(true);
			array.add(point);
		}
	}

	public void draw()
	{
		array.draw(Color.WHITE.getRGB());
	}

	@Override
	public void eventTriggered(InteractiveObjectEvent event){
		System.out.println("message from " + event.getSource().getClass().getName() + " id: " +  event.getSource().getId() + " event: " + event.getEventType());
	}
}
