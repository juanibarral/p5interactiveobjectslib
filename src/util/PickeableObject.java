package util;

import processing.core.PApplet;

/**
 * It is a Class that represents an object that has two states: selected and unselected.
 * The object has a feedback associated with the state that its in. By default the state is selected.
 * The object will be drawn only if it has changes.
 * @author Juan Camilo Ibarra
 * @version 0.5b
 *
 */
public abstract class PickeableObject {
	protected boolean selected;
	protected boolean changed;
	protected PApplet mainApplet;
	protected int id;
	
	/**
	 * Basic constructor
	 */
	public PickeableObject()
	{
		selected = true;
		changed = true;
		id = 0;
	}
	/**
	 * Constructor
	 * @param applet the applet where the object is going to be
	 */
	public PickeableObject(PApplet applet)
	{
		this();
		mainApplet = applet;
	}
	/**
	 * Sets the id for the object
	 * @param id identifier
	 */
	public void setId(int id)
	{
		this.id = id;
	}
	/**
	 * Return the identifier for the object
	 * @return the identifier for the object
	 */
	public int getId()
	{
		return id;
	}

	/**
	 * Sets the applet where the object is going to be
	 * @param applet the applet
	 */
	public void setApplet(PApplet applet)
	{
		mainApplet = applet;
	}
	/**
	 * Returns if the mouse is over the object.
	 * Each object have its own way to calculate this state
	 * @return if the mouse is over the object
	 */
	public boolean mouseIsOver (){
		return true;
	}
	/**
	 * Returns if the mouse is over the object. 
	 * Sets all the attributes to prepare the object for visual feedback.
	 * If the mouse is over the object, its state will change to selected (if necessary)
	 * If the mouse is not over the object, its state will change to unselected (if necessary)
	 * @return if the mouse is over the object
	 */
	public boolean mouseIsOverFeedback()
	{
		if(mouseIsOver())
		{
			setSelected();
		}
		else
		{
			setUnselected();
		}
		return changed;
	}
	/**
	 * Sets the object state to changed in order to be ready for visualization
	 */
	public void setChanged()
	{
		changed = true;
	}
	
	/**
	 * Sets if the point is selected
	 */
	public void setSelected()
	{
		if(!selected)
		{
			selected = true;
			changed = true;
		}
		else
		{
			changed = false;
		}
	}
	
	/**
	 * Sets if the point is unselected
	 */
	public void setUnselected()
	{
		if(selected)
		{
			selected = false;
			changed = true;
		}
		else
		{
			changed = false;
		}
	}
	/**
	 * Returns if the point is selected
	 * @return
	 */
	public boolean isSelected()
	{
		return selected;
	}
	/**
	 * 
	 * @return if the state of the point has changed
	 */
	public boolean hasChanged()
	{
		return changed;
	}

}
