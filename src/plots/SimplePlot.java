package plots;

import java.awt.Color;

import basic.InteractivePoint;

import processing.core.PApplet;
import processing.core.PFont;
import util.AbstractPointPlot;
import util.Position;

public class SimplePlot extends AbstractPointPlot {

	protected String[] axisTitles;
	protected String[] valueTitles;
	protected int[] colorByAxis;
	protected int[] colorByRows;
	protected double[][] data;
	//For Plot
	protected int plotPosX;
	protected int plotPosY;
	protected int plotHeight;
	protected int plotWidth;
	protected Position[][] lines;
	//For Axis
	protected PFont axisFont;
	protected int axisHeight;
	protected int axisInterDistance;
	protected Position[][] axisLines;
	protected int axisColor;
	protected Position[] zeroLine;
	protected int colorZeroLine;

	
	//For Legend
	protected boolean withLegend;
	protected PFont legendFont;
	protected int legendPosX;
	protected int legendPosY;
	protected int legendHeight;
	protected int legendWidth;
	
	public SimplePlot(PApplet applet)
	{
		super(applet);
		axisFont = basicFont;
		axisColor = Color.WHITE.getRGB();
		colorZeroLine = Color.WHITE.getRGB();
		zeroLine = new Position[2];
		withLegend = false;
	}
	
	public SimplePlot(PApplet applet, int posX, int posY, int width, int height) {
		this(applet);
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
		mainApplet = applet;
		calculatePlotPos();
		calculatePlotSize();
	}
	
	public void setTitleFont(PFont font)
	{
		super.setTitleFont(font);
		calculatePlotPos();
		calculatePlotSize();
	}

	private void calculatePlotPos()
	{
		plotPosX = posX + hGap;
		plotPosY = posY + vGap;
		if(withTitle)
		{
			plotPosY += titleFont.getSize() + (2*vGap);
		}
	}
	
	public void withLegend(boolean b)
	{
		withLegend = b;
		if(withLegend)
		{
			legendWidth = (width / 4) - vGap;
			calculatePlotSize();
		}
	}
	
	public void withTitle(boolean b)
	{
		withTitle = true;
		if(withTitle)
		{
			calculateTitlePos();
			calculatePlotPos();
			calculatePlotSize();
		}
	}
	
	private void calculatePlotSize()
	{
		if(withLegend)
		{
			plotWidth = width - legendWidth - (3*hGap);
		}
		else
		{
			plotWidth = width - (2*hGap);
		}
		
		if(withTitle)
		{
			plotHeight = height - titleFont.getSize() - (3*vGap);
		}
		else
		{
			plotHeight = height - (2*vGap);
		}
		calculateAxisHeight();
	}
	
	private void calculateAxisHeight()
	{
		axisHeight = plotHeight - (2 * axisFont.getSize());
	}
	
	
	public void setAxisTitles(String[] axisTitles) {
		this.axisTitles = axisTitles;
	}
	public void setValueTitles(String[] valueTitles) {
		this.valueTitles = valueTitles;
	}
	public void setColorsByRow(int[] valueColors) {
		this.colorByRows = valueColors;
	}

	public void setColorsByAxis(int[] axisColors) {
		this.colorByAxis = axisColors;
	}
	public void setData(double[] data)
	{
		double[][] d = new double[1][data.length];
		d[0] = data;
		setData(d);
	}
	public void setData(double[][] data) {
		this.data = data;
		points.clear();
		double maxValue = 0;
		for(double[] dataRow : data)
		{
			for(double dataVal : dataRow)
			{
				if(dataVal > maxValue)
				{
					maxValue = dataVal;
				}
			}
		}
		
		if(maxValue < 1)
		{
			maxValue = 1;
		}

		
		axisInterDistance = plotWidth / data[0].length; 
		calculateAxisLines(data[0].length);
		if(colorByAxis == null)
		{
			colorByAxis = new int[data[0].length];
			for(int i = 0; i < colorByAxis.length; i++)
			{
				colorByAxis[i] = Color.WHITE.getRGB();
			}
		}
		if(colorByRows == null)
		{
			colorByRows = new int[data.length];
			for(int i = 0; i < colorByRows.length; i++)
			{
				colorByRows[i] = Color.WHITE.getRGB();
			}
		}
	
		lines = new Position[data.length][data[0].length];
		
		int basePointPosX = plotPosX + (axisInterDistance / 2);
		
		int zeroYValue = (int) PApplet.map(1.0f, 0.0f, (float)maxValue, 0.0f, (float) axisHeight);
		zeroLine[0] = new Position(basePointPosX, axisLines[0][1].y - zeroYValue);
		zeroLine[1] = new Position(basePointPosX + (axisInterDistance * (data[0].length - 1)), axisLines[0][1].y - zeroYValue);
		
		for(int i = 0; i < data.length; i++)
		{
			for(int j = 0; j < data[0].length; j++)
			{
				double dataValue = data[i][j];
				int yValue = (int) PApplet.map((float)dataValue, 0.0f, (float)maxValue, 0.0f, (float) axisHeight);
				int pointPosX = basePointPosX + (j * axisInterDistance); 
				int pointPosY = axisLines[j][1].y - yValue;
				InteractivePoint point = new InteractivePoint(pointPosX, pointPosY, mainApplet);
				point.setColor(colorByAxis[j]);
				point.setSelectedColor(colorByAxis[j]);
				point.setParentId(this.id);
				point.setId(j);
				if(nodesText2!= null)
				{
					point.setText(nodesText2[i][j], null, InteractivePoint.DEFAULT_TEXT_SIZE);
				}
				else
				{
					point.setText("Data: " + decimalFormat.format(data[i][j]) + "", null, InteractivePoint.DEFAULT_TEXT_SIZE);
				}
				if(listenerAdded && listener != null)
				{
					point.addListener(listener);
				}
				point.renderText(renderNodesText);
				points.add(point);
			
				lines[i][j] = new Position(pointPosX, pointPosY);
			}
		}
		
	}
	
	private void calculateAxisLines(int n)
	{
		axisLines = new Position[n][2];
		int x = plotPosX + (axisInterDistance / 2);
		int p0y = plotPosY;
		int p1y = plotPosY + axisHeight;
		for(int i = 0; i < n; i++)
		{
			int p0x = x + (i * axisInterDistance);
			axisLines[i][0] = new Position(p0x,p0y);
			axisLines[i][1] = new Position(p0x,p1y);
		}
	}

	public void drawObject()
	{
		super.drawObject();
		if(changed)
		{
			drawAxis();
			drawPlot();
			drawLegend();
			changed = false;
		}
	}
	
	protected void drawAxis()
	{
		for(int i = 0; i < axisLines.length; i++)
		{
			mainApplet.stroke(axisColor);
			Position p0 = axisLines[i][0];
			Position p1 = axisLines[i][1];
			mainApplet.line(p0.x, p0.y, p1.x, p1.y);
			
			if(axisTitles != null)
			{
				mainApplet.textFont(axisFont);
				mainApplet.fill(axisColor);
				String title = axisTitles[i];
				int textX = (int) (p1.x - (mainApplet.textWidth(title) / 2));
				int textY = (int) (p1.y + (1.5 * axisFont.getSize()));
				mainApplet.text(title, textX, textY);
			}
		}
	}
	
	protected void drawPlot()
	{
		if(zeroLine[0] != null)
		{
			mainApplet.stroke(colorZeroLine);
			Position p0 = zeroLine[0];
			Position p1 = zeroLine[1];
			mainApplet.line(p0.x, p0.y, p1.x, p1.y);
		}
		if(lines != null)
		{
			for(int i = 0; i < lines.length; i++)
			{
				mainApplet.stroke(colorByRows[i]);
				Position p0 = lines[i][0];
				for(int j = 1; j < lines[0].length; j++)
				{
					Position p1 = lines[i][j];
					mainApplet.line(p0.x, p0.y, p1.x, p1.y);
					p0 = p1;
				}
			}
		}
		drawPoints();
		
	}
	
	protected void drawLegend()
	{
		
	}
	
	
}
