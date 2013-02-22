package examples;

import java.awt.Color;
import processing.core.PApplet;
import util.PickeableObjectArray;
import util.PickeableObjectEvent;
import util.PickeableObjectListener;
import util.VAPoint;

public class MultiplePointsExample extends PApplet implements PickeableObjectListener{


	private static final long serialVersionUID = 1L;
	private PickeableObjectArray array;
	public void setup()
	{
		int sizeX = 500;
		int sizeY = 500;
		size(sizeX, sizeY);
		int numberOfPoints = 100;	
		array = new PickeableObjectArray(this);
		for(int i = 0; i < numberOfPoints; i ++)
		{
			VAPoint point = new VAPoint((int)random(0, 500), (int)random(0,500), this);
			point.setId(i);
			point.addListener(this);
			point.setUserData(i);
			point.setText(i + "", null, VAPoint.DEFAULT_TEXT_SIZE);
			point.renderText(true);
			array.add(point);
		}
	}

	public void draw()
	{
		array.draw(Color.WHITE.getRGB());
	}

	@Override
	public void eventTriggered(PickeableObjectEvent event){
		System.out.println("message from " + event.getSource().getClass().getName() + " id: " +  event.getSource().getId() + " event: " + event.getEventType());
	}
}
