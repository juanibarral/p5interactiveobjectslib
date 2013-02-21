
package plots;

import java.awt.Color;
import java.util.ArrayList;

import processing.core.PApplet;
import util.PickeableObject;
import util.PickeableObjectListener;

/**
 * It is a class that represents a parallel coordinates plot.
 * A parallel coordinates plot let the user visualize n-dimensional data.
 * Each vertical line represents a dimension, and the data is represented
 * along this line.
 * @author Juan Camilo Ibarra
 * @version 0.5b
 */
public class ParallelCoordinatesPlot extends PickeableObject{

	private String[] headers;
	private double[][] minMax;
	private ArrayList<ParallelCoordinatesPlotLine> lines;
	private PApplet mainApplet;
	private int posY;
	private int posX;
	private int width;
	private int height;
	private int horizontalGap; 
	private int verticalGap;
	private int axisColor;
	private int colorLine;
	private int colorSelected;
	private int colorText;
	private int colorBackground;

	private int lineLength;

	private int[] axisXPositions;

	private int axisYUpperPosition;
	private int axisYLowerPosition;
	private int axisTextSize = 14;

	private boolean withBackground;
	
	private boolean autoSelection;

	/**
	 * Basic constructor
	 * @param mainApplet the applet where its going to be drawn
	 * @param headers titles for each dimension
	 * @param minMax minimum and maximum values for each dimension
	 * @param posX position in X of the left upper corner for the plot in pixels
	 * @param posY position in Y of the left upper corner for the plot in pixels
	 * @param width width of the plot in pixels
	 * @param height height of the plot in pixels
	 * @param horizontalGap horizontal gap in pixels. 
	 * @param verticalGap vertical gap in pixels
	 */
	public ParallelCoordinatesPlot(PApplet mainApplet, String[] headers, double[][] minMax, int posX, int posY, int width, int height, int horizontalGap, int verticalGap)
	{
		this.mainApplet = mainApplet;
		this.headers = headers;
		this.minMax = minMax;
		this.width = width;
		this.height =height;
		this.horizontalGap = horizontalGap;
		this.verticalGap = verticalGap;
		this.posX = posX;
		this.posY = posY;
		this.axisColor = Color.WHITE.getRGB();
		this.colorLine = Color.WHITE.getRGB();
		this.colorSelected = Color.YELLOW.getRGB();
		this.colorText = Color.WHITE.getRGB();
		this.colorBackground = Color.DARK_GRAY.getRGB();
		this.withBackground = true;
		this.lines = new ArrayList<ParallelCoordinatesPlotLine>();
		this.axisXPositions = new int[headers.length];
		this.autoSelection = false;

		lineLength = this.height - (2*verticalGap);

		axisYUpperPosition = posY + verticalGap;
		axisYLowerPosition = posY + this.height - verticalGap;

		axisXPositions[0] = posX + horizontalGap;

		int axisGap = (int)((double)(width - (2*horizontalGap)) / (double)(headers.length - 1));
		for(int i = 2; i < headers.length; i++)
		{
			axisXPositions[i - 1] = posX + horizontalGap + ((i-1)*axisGap);
		}
		axisXPositions[headers.length - 1] = posX + (width - horizontalGap);
	}
	
	/**
	 * Sets a whole new dataset to plot
	 * @param headers titles for each dimension
	 * @param minMax minimum and maximum values for each dimension
	 * @param data data matrix
	 */
	public void setNewData(String[] headers, double[][] minMax, double[][] data)
	{
		this.minMax = minMax;
		this.axisXPositions = new int[headers.length];

		lineLength = height - (2*verticalGap);

		axisYUpperPosition = posY + verticalGap;
		axisYLowerPosition = posY + height - verticalGap;

		axisXPositions[0] = posX + horizontalGap;

		int axisGap = (int)((double)(width - (2 * horizontalGap)) / (double)(headers.length - 1));
		for(int i = 2; i < headers.length; i++)
		{
			axisXPositions[i - 1] = posX + horizontalGap + ((i-1)*axisGap);
		}
		axisXPositions[headers.length - 1] = posX + (width - horizontalGap);
		
		setData(data);
	}

	/**
	 * Sets if the plot has a background
	 * @param background if the background is drawn
	 */
	public void withBackground(boolean background)
	{
		withBackground = background;
	}

	/**
	 * Sets the data for the plot. 
	 * Each row is a line
	 * @param data data matrix
	 */
	public void setData(double[][] data)
	{
		lines.clear();

		for(int i = 0; i < data.length; i++)
		{
			double[] lineData = data[i];
			int[][] points = new int[lineData.length][2];
			for(int j = 0; j < lineData.length; j++)
			{
				points[j][0] = axisXPositions[j];
				points[j][1] = posY + height - verticalGap - (int)PApplet.map((float)lineData[j], (float)minMax[j][0], (float)minMax[j][1], 0, (float)lineLength);
			}
			ParallelCoordinatesPlotLine line = new ParallelCoordinatesPlotLine(mainApplet, points);
			line.setData(lineData);
			line.setUnselectedColor(colorLine);
			line.setSelectedColor(colorSelected);
			line.setId(i);
			lines.add(line);
		}
	}

	public void addListener(PickeableObjectListener listener)
	{
		for(ParallelCoordinatesPlotLine line : lines)
		{
			line.addListener(listener);
		}
	}
	
	/**
	 * Sets the color of the lines
	 * @param lineColor RGB color value
	 */
	public void setLineColor(int lineColor) {
		this.colorLine = lineColor;
	}

	/**
	 * Sets the color of the lines when they are selected
	 * @param selectedColor RGB color value
	 */
	public void setSelectedColor(int selectedColor) {
		this.colorSelected = selectedColor;
	}

	/**
	 * Sets the color of the headers
	 * @param textColor RGB color value
	 */
	public void setTextColor(int textColor) {
		this.colorText = textColor;
	}

	/**
	 * Sets the color of the background
	 * @param backgroundColor
	 */
	public void setBackgroundColor(int backgroundColor) {
		this.colorBackground = backgroundColor;
	}

	/**
	 * Returns if the mouse is over the plot.
	 * The mouse is over the plot if its over any of its lines
	 * @return if the mouse is over the plot
	 */
	public boolean mouseIsOver()
	{
		for(ParallelCoordinatesPlotLine l : lines)
		{
			if(l.mouseIsOver())
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Deselects all of the lines
	 */
	public void deselectLines()
	{
		for(ParallelCoordinatesPlotLine line_i : lines)
		{
			line_i.setUnselected();
			line_i.setChanged();
		}
		
		drawAxis();
		drawLines();
	}
	
	/**
	 * Selects the given lines 
	 * @param lineNumbers indices of the lines to be selected
	 */
	public void setSelectedLines(int[] lineNumbers)
	{
		autoSelection = true;
		for(int i : lineNumbers)
		{
			lines.get(i).setSelected();
			lines.get(i).setChanged();
		}
		
		drawAxis();
		drawLines();
	}
	
	private void drawLines()
	{
		for(ParallelCoordinatesPlotLine line : lines)
		{
			if(!autoSelection)
			{
				line.mouseIsOverFeedback();
			}
			line.setChanged();
			line.drawLine();
		}
		autoSelection = false;
	}
	
	private void drawAxis()
	{
		if(withBackground)
		{
			mainApplet.fill(colorBackground);
			mainApplet.stroke(axisColor);
			mainApplet.strokeWeight(1);
			mainApplet.rect((float)posX, (float)posY, (float)width, (float)height);
		}
		for(int i = 0; i < axisXPositions.length; i++)
		{
			mainApplet.stroke(axisColor);
			mainApplet.strokeWeight(1);
			mainApplet.line(axisXPositions[i], axisYUpperPosition, axisXPositions[i], axisYLowerPosition);

			String header = headers[i] + "\n" + minMax[i][0] + " to " + minMax[i][1];
			float textWidth = mainApplet.textWidth(header);
			mainApplet.fill(colorText);
			mainApplet.text(header, axisXPositions[i] - (int)(textWidth/2),  axisYLowerPosition + 15, axisTextSize);
		}
	}
	
	/**
	 * Draws the plot if its necessary
	 */
	public void drawPlot()
	{
		if(changed)
		{
			drawAxis();
			if(!lines.isEmpty())
			{
				drawLines();
			}
			changed = false;
		}
	}
	
	/**
	 * Sets if the plot will render the values of the points
	 * @param b if the nodes will render their info
	 */
	public void renderNodesInfo(boolean b)
	{
		for(ParallelCoordinatesPlotLine line : lines)
		{
			line.renderPointsData(b);
		}
	}
}
