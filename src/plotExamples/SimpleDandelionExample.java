package plotExamples;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import plots.SimpleDandelionPlot;
import pointExamples.MyPoint;
import processing.core.PApplet;
import util.InteractiveObjectEvent;
import util.InteractiveObjectListener;
import util.InteractivePoint;

public class SimpleDandelionExample extends PApplet implements InteractiveObjectListener{

	private static final long serialVersionUID = 1L;
	private SimpleDandelionPlot plot;
	
	public void setup()
	{
		plot = new SimpleDandelionPlot(this, 50, 50, 400);
		plot.setRange(0, 1);
//		plot.setCenterOffset(150);
		
		int numOfValues = 10;
		double[] data = new double[numOfValues];
		int[] colors = new int[numOfValues];
		String[] text = new String[numOfValues];
		Random r = new Random();
		for(int i = 0; i < numOfValues; i++)
		{
			data[i] = r.nextDouble();
			Color c = new Color(r.nextFloat(), r.nextFloat(), r.nextFloat());
			colors[i] = c.getRGB();
			text[i] = "Text " + data[i];
		}
		
		try {
			
			plot.setNodeColors(colors);
			plot.setNodesText(text);
			plot.addListener(this);
			plot.renderNodesData(true);
			
			ArrayList<InteractivePoint> newPoints = new ArrayList<InteractivePoint>();
			for(int i = 0; i < numOfValues; i++)
			{
				MyPoint newPoint = new MyPoint(0, 0, this);
				newPoint.setImages("C:/Users/usuario/Downloads/selected.png", "C:/Users/usuario/Downloads/unselected.png");
				newPoints.add(newPoint);
			}
			
			plot.setData(data);
			plot.setNewPoints(newPoints);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		size(500, 500);
	}
	
	public void draw()
	{
		if(plot.mouseIsOverFeedback())
		{
			background(Color.LIGHT_GRAY.getRGB());
		}
		plot.drawObject();
	}

	@Override
	public void eventTriggered(InteractiveObjectEvent event){
		System.out.println("message from " + event.getSource().getClass().getName() + " id: " +  event.getSource().getId() + " event: " + event.getEventType());
	}
}