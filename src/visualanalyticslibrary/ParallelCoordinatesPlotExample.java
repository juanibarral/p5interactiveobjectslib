package visualanalyticslibrary;

import java.util.Random;
import plots.ParallelCoordinatesPlot;
import processing.core.PApplet;

public class ParallelCoordinatesPlotExample extends PApplet{

	private ParallelCoordinatesPlot parallelCoordinatesPlot;
	
	public void setup() 
	{	
		size(700,500);
		int coordinatesSize = 9;
		int dataSize = 9;
		
		double[][] minMax = new double[coordinatesSize][2];
		double[][] data = new double[dataSize][coordinatesSize];
		
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
		}
		
		parallelCoordinatesPlot = new ParallelCoordinatesPlot(this, headers, minMax, 50, 50,  600, 400, 50, 50);
		parallelCoordinatesPlot.setData(data);
	}

	public void draw() 
	{
		parallelCoordinatesPlot.mouseIsOverFeedback();
		parallelCoordinatesPlot.drawPlot();
	}
}
