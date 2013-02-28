package plotExamples;

import java.awt.Color;
import java.util.Random;
import plots.ParallelCoordinatesPlot;
import processing.core.PApplet;
import util.InteractiveObjectEvent;
import util.InteractiveObjectListener;

public class ParallelCoordinatesPlotExample extends PApplet implements InteractiveObjectListener{

	private static final long serialVersionUID = 1L;
	private ParallelCoordinatesPlot parallelCoordinatesPlot;
	
	public void setup() 
	{	
		size(700,500);
		int coordinatesSize = 3;
		int dataSize = 9;
		
		double[][] minMax = new double[coordinatesSize][2];
		double[][] data = new double[dataSize][coordinatesSize];
		int[] colorLines = new int[dataSize];
		
		String[] headers = new String[coordinatesSize];
		for(int i = 0; i < headers.length; i++)
		{
			headers[i] = "Header" + i;
			minMax[i][0] = 0;
			minMax[i][1] = 1;
		}
		
		Random r = new Random();
		for(int i = 0; i < dataSize; i++)
		{
			
			for(int j = 0; j < coordinatesSize; j++)
			{
				data[i][j] = r.nextDouble();
			}
			Color c = new Color(r.nextFloat(), r.nextFloat(), r.nextFloat());
			colorLines[i] = c.getRGB();
		}
		
//		parallelCoordinatesPlot = new ParallelCoordinatesPlot(this, headers, minMax, 50, 50,  600, 400, 50, 50);
		parallelCoordinatesPlot = new ParallelCoordinatesPlot(this, 50, 50, 600, 400);
		
		parallelCoordinatesPlot.renderNodesInfo(true);
		parallelCoordinatesPlot.addListener(this);
		parallelCoordinatesPlot.setData(data);
		parallelCoordinatesPlot.setColorLines(colorLines);
		parallelCoordinatesPlot.setId(20);
	}

	public void draw() 
	{
		if(parallelCoordinatesPlot.mouseIsOverFeedback())
		{
			background(Color.LIGHT_GRAY.getRGB());
		}
		parallelCoordinatesPlot.drawObject();
	}

	@Override
	public void eventTriggered(InteractiveObjectEvent event){
		System.out.println("message from " + event.getSource().getClass().getName() + " id: " +  event.getSource().getId() + " event: " + event.getEventType());
	}
}
