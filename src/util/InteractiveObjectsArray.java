package util;

import java.util.ArrayList;
import processing.core.PApplet;

/**
 * It is an array of interactive objects. 
 * It is in charge of drawing the objects
 * @author Juan Camilo Ibarra
 * @version 0.5b
 *
 */
public class InteractiveObjectsArray {

	private ArrayList<AbstractInteractiveObject> objects;
	private PApplet mainApplet;
	
	/**
	 * Basic constructor
	 * @param applet the applet that will use the array
	 */
	public InteractiveObjectsArray(PApplet applet)
	{
		this.mainApplet = applet;
		this.objects = new ArrayList<AbstractInteractiveObject>();
	}
	
	/**
	 * Adds an object to the array
	 * @param object the object to add
	 */
	public void add(AbstractInteractiveObject object)
	{
		objects.add(object);
	}
	/**
	 * Removes an object from the array
	 * @param object the object to remove
	 */
	public void remove(AbstractInteractiveObject object)
	{
		objects.remove(object);
	}
	/**
	 * Removes an oject from the array
	 * @param i index of the object
	 */
	public void removeObject(int i)
	{
		objects.remove(i);
	}
	
	/**
	 * Draws the objects in the array if necessary.
	 * @param backgroundColor background color to clear the canvas
	 */
	public void draw(int backgroundColor)
	{
		int objectChangedIndex = -1;
		for(int i = 0; i < objects.size(); i++)
		{
			AbstractInteractiveObject object = objects.get(i);
			if(object.mouseIsOverFeedback())
			{
				mainApplet.background(backgroundColor);
				objectChangedIndex = i;
				break;
			}
		}
		
		for(int i = 0; i < objects.size(); i++)
		{
			AbstractInteractiveObject object = objects.get(i);
			if(objectChangedIndex != -1 && i != objectChangedIndex)
			{
				object.setChanged();
			}
			object.drawObject();
		}
	}
}
