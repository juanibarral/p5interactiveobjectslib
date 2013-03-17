package plotExamples;

import java.awt.Color;
import java.util.Random;

import plots.SimplePlot;
import processing.core.PApplet;
import processing.core.PFont;
import util.InteractiveObjectEvent;
import util.InteractiveObjectListener;

public class SimplePlotExample extends PApplet implements InteractiveObjectListener{

	private SimplePlot plot;
	
	public void setup()
	{
		size(500,500);
		plot = new SimplePlot(this, 50, 50, 400, 400);
//		plot.withLegend(true);
		plot.withTitle(true);
		PFont font = createFont("Arial", 25);
		plot.setTitleFont(font);
		plot.setTitle("Este es mi plot");
		int numOfValues = 6;
		double[] data = new double[numOfValues];
		int[] colors = new int[numOfValues];
		String[] axisTitles = new String[numOfValues];
		Random r = new Random();
		for(int i = 0; i < numOfValues; i++)
		{
			data[i] = r.nextDouble() + 0.5;
			Color c = new Color(r.nextFloat(), r.nextFloat(), r.nextFloat());
			colors[i] = c.getRGB();
			axisTitles[i] = "Title " + i;
		}
//		plot.setColorsByRow(colors);
		plot.withBackground(true);
		plot.setColorBackground(Color.DARK_GRAY.getRGB());
		plot.setColorsByAxis(colors);
		plot.setAxisTitles(axisTitles);
		plot.setRenderNodesText(true);
		plot.addListener(this);
		plot.setData(data);
		
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
