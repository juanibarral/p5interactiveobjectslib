package util;

import java.text.DecimalFormat;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PFont;

/**
 * It is a Class that represents an object that has two states: selected and unselected.
 * The object has a feedback associated with the state that its in. By default the state is selected.
 * The object will be drawn only if it has changes.
 * @author Juan Camilo Ibarra
 * @version 0.5b
 *
 */
public abstract class AbstractInteractiveObject{
	protected int posX;
	protected int posY;
	protected boolean selected;
	protected boolean changed;
	protected PApplet mainApplet;
	protected int id;
	protected int parentId;
	protected ArrayList<InteractiveObjectListener> listeners;
	protected boolean firstTime = true;
	protected Object userData;
	protected boolean overrideChanged;
	public static DecimalFormat decimalFormat;
	public static PFont basicFont;
	/**
	 * Basic constructor
	 */
	public AbstractInteractiveObject()
	{
		selected = false;
		changed = true;
		id = 0;
		listeners = new ArrayList<InteractiveObjectListener>();
		posX = 0;
		posY = 0;
		decimalFormat = new DecimalFormat("#.##");
	}
	/**
	 * Constructor
	 * @param applet the applet where the object is going to be
	 */
	public AbstractInteractiveObject(PApplet applet)
	{
		this();
		mainApplet = applet;
		basicFont = mainApplet.createFont("Arial", 14);
	}
	public AbstractInteractiveObject(PApplet applet, int posX, int posY)
	{
		this(applet);
		this.posX = posX;
		this.posY = posY;
	}
	
	/**
	 * Sets the user data for this object
	 * @param data user data
	 */
	public void setUserData(Object data)
	{
		this.userData = data;
	}
	/**
	 * Returns the user data
	 * @return user data
	 */
	public Object getUserData()
	{
		return userData;
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
	 * @return the parentId
	 */
	public int getParentId() {
		return parentId;
	}
	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(int parentId) {
		this.parentId = parentId;
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
		overrideChanged = true;
		changed = true;
	}
	
	/**
	 * Sets if the object is selected
	 */
	public void setSelected()
	{
		if(!selected)
		{
			selected = true;
			changed = true;
			notifyListeners(InteractiveObjectEvent.SELECTED);
		}
		else if(!overrideChanged)
		{
			changed = false;
		}
		
	}
	/**
	 * Get all the listeners of this object
	 * @return the listeners
	 */
	public ArrayList<InteractiveObjectListener> getListeners()
	{
		return listeners;
	}
	
	/**
	 * Notifies all listeners that a change has been made
	 */
	public void notifyListeners(int event)
	{
		for(InteractiveObjectListener listener : listeners)
		{
			listener.eventTriggered(new InteractiveObjectEvent(this, event));
		}
	}

	
	/**
	 * Adds a new listener for this object
	 * @param listener listener to object
	 */
	public void addListener(InteractiveObjectListener listener)
	{
		listeners.add(listener);
	}
	
	/**
	 * Remove the listener for this object
	 * @param listener the listener to be removed
	 */
	public void removeListener(InteractiveObjectListener listener)
	{
		listeners.remove(listener);
	}
	
	/**
	 * Sets if the point is unselected
	 */
	public void setUnselected()
	{
		if(firstTime)
		{
			firstTime = false;
		}
		else
		{
			if(selected)
			{
				selected = false;
				changed = true;
				notifyListeners(InteractiveObjectEvent.UNSELECTED);
			}
			else if (!overrideChanged)
			{
				changed = false;
			}
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
	 * @return if the state of the object has changed
	 */
	public boolean hasChanged()
	{
		return changed;
	}
	
	/**
	 * Draws the object
	 */
	public void drawObject()
	{
		
	}

}
