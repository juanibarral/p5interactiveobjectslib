package examples;

import java.awt.Color;
import java.util.Random;
import plots.ParallelCoordinatesPlot;
import processing.core.PApplet;
import util.PickeableObjectArray;
import util.PickeableObjectEvent;
import util.PickeableObjectListener;
import util.VAPoint;


public class DynamicParallelCoordinatesPlotExample extends PApplet implements PickeableObjectListener{

	private static final long serialVersionUID = 1L;
	private ParallelCoordinatesPlot parallelCoordinatesPlot;
	private VAPoint point;
	private PickeableObjectArray array;
	private int sizeX;
	private int sizeY;

	public void setup() 
	{
		sizeX = 1000;
		sizeY = 500;
		array = new PickeableObjectArray(this);
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
		
		parallelCoordinatesPlot = new ParallelCoordinatesPlot(this, headers, minMax, 50, 50,  600, 300, 50, 50);
		parallelCoordinatesPlot.setData(data);	
		size(sizeX,sizeY);
		point = new VAPoint(800, 250, this);
		point.setSize(50);
		point.setId(111);
		point.addListener(this);
		array.add(parallelCoordinatesPlot);
		array.add(point);
	}

	public void draw() 
	{
		array.draw(Color.WHITE.getRGB());
	}
	
	public void setDataForPlot()
	{
		int coordinatesSize = (int)random(3,10);
		int dataSize = (int) random(5,10);
		
		System.out.println(coordinatesSize + "  " + dataSize);
		
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
		parallelCoordinatesPlot.setNewData(headers, minMax, data);
	}

	@Override
	public void eventTriggered(PickeableObjectEvent event) {
		if(event.getSource() instanceof VAPoint)
		{
			if(event.getEventType() == PickeableObjectEvent.SELECTED && event.getSource().getId() == 111)
			{
				setDataForPlot();
			}
		}
		
	}
}
