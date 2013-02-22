package plots;

import java.awt.Color;

import processing.core.PApplet;
import util.PickeableObject;
import util.VAPoint;

/**
 * It is a class that represents a line in the plot.
 * Each line has points in its vertices.
 * Default color for the line is white.
 * Default color for selection is yellow 
 * @author Juan Camilo Ibarra
 * @version 0.5b
 */
public class ParallelCoordinatesPlotLine extends PickeableObject{

	private int[][] controlPoints;
	private VAPoint[] visualPoints;
	private int unselectedColor;
	private int selectedColor;
	private boolean updateLines;
	
	/**
	 * Basic constructor
	 * @param mainApplet the applet where the line is going to be
	 * @param points the vertices of the line in pixels, (x0, y0, x1, y1, ... xn, yn)
	 */
	public ParallelCoordinatesPlotLine(PApplet mainApplet, int[][] points)
	{
		super(mainApplet);
		this.controlPoints = points;
		this.visualPoints = new VAPoint[controlPoints.length];
		this.unselectedColor = Color.WHITE.getRGB();
		this.selectedColor = Color.YELLOW.getRGB();
		this.updateLines = false;
		
		for(int i = 0; i < controlPoints.length; i++)
		{
			visualPoints[i] = new VAPoint(controlPoints[i][0], controlPoints[i][1], mainApplet);
			visualPoints[i].setId(i);
			visualPoints[i].setColor(unselectedColor);
			visualPoints[i].setTextColor(selectedColor);
		}
	}
	
	public void setData(double[] data)
	{
		for(int i = 0; i < data.length; i++)
		{
			visualPoints[i].setUserData(data[i]);
			visualPoints[i].setText(data[i] + "", null, VAPoint.DEFAULT_TEXT_SIZE);
		}
	}
	
	/**
	 * Sets if the line will be rendered with text in its nodes
	 * @param withText if the lines will have text in its nodes
	 */
	public void renderPointsData(boolean withText)
	{
		for(int i = 0; i < controlPoints.length; i++)
		{
			visualPoints[i].renderText(withText);
		}
	}
	/**
	 * Sets the color for when the line is not selected
	 * @param unselectedColor RGB color value
	 */
	public void setUnselectedColor(int unselectedColor) {
		this.unselectedColor = unselectedColor;
	}
	/**
	 * Sets the color for when the line is selected
	 * @param selectedColor RGB color value
	 */
	public void setSelectedColor(int selectedColor) {
		for(VAPoint p : visualPoints)
		{
			p.setSelectedColor(selectedColor);
		}
		this.selectedColor = selectedColor;
	}
	
	/**
	 * Returns if the mouse is over the line.
	 * The mouse is over the line if its over any of its vertices
	 * @return if the mouse is over the line
	 */
	public boolean mouseIsOver()
	{
		boolean noneSelected = true;
		boolean mouseIsOver = false;
		for(VAPoint p : visualPoints)
		{
			if(p.mouseIsOver())
			{
				mouseIsOver = true;
				noneSelected = false;
				break;
			}
		}
		if(noneSelected)
		{
			mouseIsOver = false;
		}
		return mouseIsOver;
	}
	
	public void setChanged()
	{
		super.setChanged();
		updateLines = true;
	}

	public void drawObject()
	{
		if(changed)
		{
			int startX = controlPoints[0][0];
			int startY = controlPoints[0][1];
			int endX = 0;
			int endY = 0;
			for(VAPoint p : visualPoints)
			{
				if(isSelected())
				{
					p.setColor(selectedColor);
					p.setSelected();
				}
				else
				{
					p.setColor(unselectedColor);
					p.setUnselected();
				}
				if(updateLines)
				{
					p.setChanged();
				}
				p.drawObject();
			}
			
			for(int i = 1; i < controlPoints.length; i++)
			{
				endX = controlPoints[i][0];
				endY = controlPoints[i][1];
				if(isSelected())
				{
					mainApplet.stroke(selectedColor);
					mainApplet.strokeWeight(4);
				}
				else
				{
					mainApplet.stroke(unselectedColor);
					mainApplet.strokeWeight(1);
				}
				mainApplet.line(startX, startY, endX, endY);
				startX = endX;
				startY = endY;
			}
			mainApplet.stroke(unselectedColor);
			changed = false;
		}
	}
}
