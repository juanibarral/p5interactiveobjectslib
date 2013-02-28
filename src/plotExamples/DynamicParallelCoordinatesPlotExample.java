package plotExamples;

import java.awt.Color;
import java.util.Random;

import basic.InteractivePoint;
import plots.ParallelCoordinatesPlot;
import processing.core.PApplet;
import util.InteractiveObjectsArray;
import util.InteractiveObjectEvent;
import util.InteractiveObjectListener;


public class DynamicParallelCoordinatesPlotExample extends PApplet implements InteractiveObjectListener{

	private static final long serialVersionUID = 1L;
	private ParallelCoordinatesPlot parallelCoordinatesPlot;
	private InteractivePoint point1;
	private InteractivePoint point2;
	private InteractiveObjectsArray array;
	private int sizeX;
	private int sizeY;
	private boolean drawn;

	public void setup() 
	{
		sizeX = 1000;
		sizeY = 500;
		array = new InteractiveObjectsArray(this);
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
		
		parallelCoordinatesPlot = new ParallelCoordinatesPlot(this, 50, 50,  600, 300);
		parallelCoordinatesPlot.setHeaders(headers);
		parallelCoordinatesPlot.setMinMax(minMax);
		parallelCoordinatesPlot.setData(data);
//		parallelCoordinatesPlot.setData(headers, minMax, data);	
		parallelCoordinatesPlot.renderNodesInfo(true);
		parallelCoordinatesPlot.addListener(this);
		
		point1 = new InteractivePoint(800, 150, this);
		point1.setId(1);
		point1.setSize(50);
		point1.addListener(this);
		
		point2 = new InteractivePoint(800, 250, this);
		point2.setId(2);
		point2.setSize(50);
		point2.setColor(Color.BLUE.getRGB());
		point2.addListener(this);
		
		
		array.add(parallelCoordinatesPlot);
//		array.add(point1);
		array.add(point2);
		
		size(sizeX,sizeY);
	}

	public void draw() 
	{
		array.draw(Color.WHITE.getRGB());
		
		float disX = point1.getPosX() - mouseX;
		float disY = point1.getPosY() - mouseY;
		
		if((int)(PApplet.sqrt(PApplet.sq(disX) + PApplet.sq(disY))) < point1.getSize()) 
		{
			if(!drawn)
			{
				setDataForPlot();
				drawn = true;
			}

		}
		else
		{
			drawn = false;
		}
	}
	
	public void setDataForPlot()
	{
		
		int coordinatesSize = (int)random(3,10);
		int dataSize = (int) random(5,10);
		
		System.out.println("set data: " + coordinatesSize + "    " + dataSize);
		
		double[][] minMax = new double[coordinatesSize][2];
		double[][] data = new double[dataSize][coordinatesSize];
		
		String[] headers = new String[coordinatesSize];
		for(int i = 0; i < coordinatesSize; i++)
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
		parallelCoordinatesPlot.setData(headers, minMax, data);
	}


	@Override
	public void eventTriggered(InteractiveObjectEvent event) {
		if(event.getSource() instanceof InteractivePoint)
		{
			int pointId = event.getSource().getId();
			if(pointId == 1)
			{
				if(!drawn)
				{
					setDataForPlot();
					drawn = true;
				}
			}
			else if (pointId == 2)
			{
				parallelCoordinatesPlot.setSelectedLines(new int[]{0});
			}
		}
		else
		{
			System.out.println("message from " + event.getSource().getClass().getName() + " id: " +  event.getSource().getId() + " event: " + event.getEventType());
		}
	}
}
