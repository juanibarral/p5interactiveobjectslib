package examples;

import java.awt.Color;
import java.util.Random;
import plots.SimpleDandelionPlot;
import processing.core.PApplet;
import util.PickeableObjectEvent;
import util.PickeableObjectListener;

public class SimpleDandelionExample2 extends PApplet implements PickeableObjectListener{

	private static final long serialVersionUID = 1L;
	private SimpleDandelionPlot plot;
	
	public void setup()
	{
		plot = new SimpleDandelionPlot(this, 50, 50, 400);
		plot.setRange(0, 1);
		plot.setCenterOffset(50);
		
		int numOfCategories = 10;
		int numMaxOfValuesPerCategory = 5;
		double[][] data = new double[numOfCategories][];
		int[] colors = new int[numOfCategories];
		String[] text = new String[numOfCategories];
		Random r = new Random();
		for(int i = 0; i < numOfCategories; i++)
		{
			int values = (int) (r.nextFloat() * numMaxOfValuesPerCategory);
			data[i] = new double[values];
			for(int j = 0; j < values; j++)
			{
				data[i][j] = r.nextDouble();
				Color c = new Color(r.nextFloat(), r.nextFloat(), r.nextFloat());
				colors[i] = c.getRGB();
				text[i] = "Text " + data[i];
			}
		}
		
		try {
			
			plot.setNodeColors(colors);
			plot.setNodesText(text);
			plot.addListener(this);
			plot.renderNodesData(true);
			
			plot.setData(data);
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
	public void eventTriggered(PickeableObjectEvent event){
		System.out.println("message from " + event.getSource().getClass().getName() + " id: " +  event.getSource().getId() + " event: " + event.getEventType());
	}
}