package visualanalyticslibrary;

import java.awt.Color;
import java.util.Random;

import plots.ParallelCoordinatesPlot;
import plots.ParallelCoordinatesPlotLine;
import processing.core.PApplet;
import util.VAPoint;


public class VisualAnalyticsLibrary extends PApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ParallelCoordinatesPlot parallelCoordinatesPlot;
	private VAPoint point;
	private ParallelCoordinatesPlotLine plotLine;
	
	private int sizeX;
	private int sizeY;

	public void setup() 
	{
		sizeX = 1000;
		sizeY = 500;
		
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
//		
//		int[][] points = new int[][]{{100,100}, {200,200}, {150,362}};
//		plotLine = new ParallelCoordinatesPlotLine(this, points);
	}

	public void draw() 
	{
		/*
		if(point.mouseIsOverReaction())
		{
			background(Color.GRAY.getRGB());
		}
		point.drawPoint();
//		*/
		/*
		if(plotLine.mouseIsOverReaction())
		{
			background(Color.GRAY.getRGB());
		}
		plotLine.drawLine();
//		*/
		/*
		if(parallelCoordinatesPlot.mouseIsOverReaction())
		{
			background(Color.GRAY.getRGB());
		}
		parallelCoordinatesPlot.drawPlot();
		
		
//		*/
//		/*
		
		if(parallelCoordinatesPlot.mouseIsOverFeedback())
		{
			background(Color.GRAY.getRGB());
			if(!point.mouseIsOverFeedback())
			{
				point.setChanged();
			}
		}
		else if(point.mouseIsOverFeedback())
		{
			background(Color.GRAY.getRGB());
			parallelCoordinatesPlot.setSelectedLines(new int[]{1,3,5});
//			if(!parallelCoordinatesPlot.mouseIsOverFeedback())
//			{
//				parallelCoordinatesPlot.setChanged();
//			}
		}
		
		
		parallelCoordinatesPlot.drawPlot();
		point.drawPoint();
		//*/
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
}
