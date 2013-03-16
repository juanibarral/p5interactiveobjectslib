
package plots;

import java.awt.Color;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PFont;
import util.AbstractInteractiveObject;
import util.InteractiveObjectListener;

/**
 * It is a class that represents a parallel coordinates plot.
 * A parallel coordinates plot let the user visualize n-dimensional data.
 * Each vertical line represents a dimension, and the data is represented
 * along this line.
 * @author Juan Camilo Ibarra
 * @version 0.5b
 */
public class ParallelCoordinatesPlot extends AbstractInteractiveObject{

	private String[] headers;
	private double[][] minMax;
	private ArrayList<ParallelCoordinatesPlotLine> lines;
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
	private boolean listenerAdded;
	private boolean renderNodesInfo;
	private InteractiveObjectListener listener;
	private boolean withHeaders;
	private PFont fontHeaders;

	public ParallelCoordinatesPlot(PApplet mainApplet, int posX, int posY)
	{
		super(mainApplet, posX, posY);
		lines = new ArrayList<ParallelCoordinatesPlotLine>();
		width = 100;
		height = 100;
		horizontalGap = 20; 
		verticalGap = 20;
		axisColor = Color.WHITE.getRGB();
		colorLine = Color.WHITE.getRGB();
		colorSelected = Color.YELLOW.getRGB();
		colorText = Color.WHITE.getRGB();
		colorBackground = Color.DARK_GRAY.getRGB();
		lineLength = 60;
		axisTextSize = 14;
	}

	public ParallelCoordinatesPlot(PApplet mainApplet, int posX, int posY, int width, int height)
	{
		this(mainApplet, posX, posY);
		this.width = width;
		this.height =height;
		this.axisColor = Color.WHITE.getRGB();
		this.colorLine = Color.WHITE.getRGB();
		this.colorSelected = Color.YELLOW.getRGB();
		this.colorText = Color.WHITE.getRGB();
		this.colorBackground = Color.DARK_GRAY.getRGB();
		this.withBackground = true;
		this.lines = new ArrayList<ParallelCoordinatesPlotLine>();
		this.autoSelection = false;
		this.fontHeaders = AbstractInteractiveObject.basicFont;

	}

	/**
	 * Sets a whole new dataset to plot
	 * @param headers titles for each dimension
	 * @param minMax minimum and maximum values for each dimension
	 * @param data data matrix
	 */
	public void setData(String[] headers, double[][] minMax, double[][] data)
	{
		this.headers = headers;
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

		withHeaders = true;
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
		if(minMax == null)
		{
			minMax = new double[data[0].length][2];
			for(int i = 0; i < data[0].length; i++)
			{
				minMax[i][0] = Double.MAX_VALUE;
				minMax[i][1] = Double.MIN_VALUE;
			}
			for(int i = 0; i < data[0].length; i++)
			{
				for(int j = 0; j < data.length; j++)
				{
					if(minMax[i][0] > data[j][i])
					{
						minMax[i][0] = data[j][i];
					}
					if(minMax[i][1] < data[j][i])
					{
						minMax[i][1] = data[j][i];
					}
				}
			}
		}
		this.axisXPositions = new int[data[0].length];
		lineLength = height - (2*verticalGap);
		axisYUpperPosition = posY + verticalGap;
		axisYLowerPosition = posY + height - verticalGap;

		axisXPositions[0] = posX + horizontalGap;

		int axisGap = (int)((double)(width - (2 * horizontalGap)) / (double)(data[0].length - 1));
		
		for(int i = 2; i < data[0].length; i++)
		{
			axisXPositions[i - 1] = posX + horizontalGap + ((i-1)*axisGap);
		}
		axisXPositions[data[0].length - 1] = posX + (width - horizontalGap);
		

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
			if(listenerAdded)
			{
				line.addListener(listener);
			}

			line.renderPointsData(renderNodesInfo);

			lines.add(line);
		}
		setChanged();
	}

	public void addListener(InteractiveObjectListener listener)
	{
		listenerAdded = true;
		this.listener = listener;
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
			//			lines.get(i).setChanged();
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
			line.drawObject();
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
		if(axisXPositions != null)
		{
			for(int i = 0; i < axisXPositions.length; i++)
			{
				mainApplet.stroke(axisColor);
				mainApplet.strokeWeight(1);
				mainApplet.line(axisXPositions[i], axisYUpperPosition, axisXPositions[i], axisYLowerPosition);

				if(withHeaders)
				{
//					String header = headers[i] + "\n" + decimalFormat.format(minMax[i][0]) + " to " + decimalFormat.format(minMax[i][1]);
					String header = headers[i];
					String min = decimalFormat.format(minMax[i][0]);
					String max = decimalFormat.format(minMax[i][1]);
					float headerTextWidth = mainApplet.textWidth(header);
					float minTextWidth = mainApplet.textWidth(min);
					mainApplet.fill(colorText);
					mainApplet.textFont(fontHeaders);
					mainApplet.text(min, axisXPositions[i] - (int)(minTextWidth/2),  axisYLowerPosition + fontHeaders.getSize() + 5, axisTextSize);
					mainApplet.text(max, axisXPositions[i] - (int)(minTextWidth/2),  axisYUpperPosition - fontHeaders.getSize() - 5, axisTextSize);
					mainApplet.text(header, axisXPositions[i] - (int)(headerTextWidth/2),  axisYLowerPosition + (fontHeaders.getSize()*2) + 10, axisTextSize);
				}
			}
		}
	}

	public void drawObject()
	{
		super.drawObject();
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
		renderNodesInfo = b;
	}	

	public boolean mouseIsOverFeedback()
	{

		return super.mouseIsOverFeedback();
	}

	/**
	 * @return the headers
	 */
	public String[] getHeaders() {
		return headers;
	}

	/**
	 * @param headers the headers to set
	 */
	public void setHeaders(String[] headers) {
		this.headers = headers;
		withHeaders = true;
	}

	/**
	 * @return the minMax
	 */
	public double[][] getMinMax() {
		return minMax;
	}

	/**
	 * @param minMax the minMax to set
	 */
	public void setMinMax(double[][] minMax) {
		this.minMax = minMax;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the horizontalGap
	 */
	public int getHorizontalGap() {
		return horizontalGap;
	}
	
	public void setColorLines(int[] colorLines)
	{
		for(int i = 0; i < lines.size(); i++)
		{
			lines.get(i).setUnselectedColor(colorLines[i]);
		}
	}

	/**
	 * @param horizontalGap the horizontalGap to set
	 */
	public void setHorizontalGap(int horizontalGap) {
		this.horizontalGap = horizontalGap;
	}

	/**
	 * @return the verticalGap
	 */
	public int getVerticalGap() {
		return verticalGap;
	}

	/**
	 * @param verticalGap the verticalGap to set
	 */
	public void setVerticalGap(int verticalGap) {
		this.verticalGap = verticalGap;
	}

	/**
	 * @return the axisColor
	 */
	public int getAxisColor() {
		return axisColor;
	}

	/**
	 * @param axisColor the axisColor to set
	 */
	public void setAxisColor(int axisColor) {
		this.axisColor = axisColor;
	}

	/**
	 * @return the colorLine
	 */
	public int getColorLine() {
		return colorLine;
	}

	/**
	 * @param colorLine the colorLine to set
	 */
	public void setColorLine(int colorLine) {
		this.colorLine = colorLine;
	}

	/**
	 * @return the colorSelected
	 */
	public int getColorSelected() {
		return colorSelected;
	}

	/**
	 * @param colorSelected the colorSelected to set
	 */
	public void setColorSelected(int colorSelected) {
		this.colorSelected = colorSelected;
	}

	/**
	 * @return the colorText
	 */
	public int getColorText() {
		return colorText;
	}

	/**
	 * @param colorText the colorText to set
	 */
	public void setColorText(int colorText) {
		this.colorText = colorText;
	}

	/**
	 * @return the colorBackground
	 */
	public int getColorBackground() {
		return colorBackground;
	}

	/**
	 * @param colorBackground the colorBackground to set
	 */
	public void setColorBackground(int colorBackground) {
		this.colorBackground = colorBackground;
	}

	/**
	 * @return the axisTextSize
	 */
	public int getAxisTextSize() {
		return axisTextSize;
	}

	/**
	 * @param axisTextSize the axisTextSize to set
	 */
	public void setAxisTextSize(int axisTextSize) {
		this.axisTextSize = axisTextSize;
	}

	public void setGap(int horizontalGap, int verticalGap)
	{
		this.horizontalGap = horizontalGap;
		this.verticalGap = verticalGap;
	}

}
