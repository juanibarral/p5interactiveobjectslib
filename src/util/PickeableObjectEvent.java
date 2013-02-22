package util;

/**
 *  It is an event launched by a Pickeable object
 * @author Juan Camilo Ibarra
 * @version 0.5b
 */
public class PickeableObjectEvent {

	public static final int SELECTED = 0;
	public static final int UNSELECTED = 1;
	private PickeableObject source;
	private int event;
	
	/**
	 * Basic Constructor
	 * @param source pickeable object that launched the event
	 * @param event event launched
	 */
	public PickeableObjectEvent(PickeableObject source, int event)
	{
		this.source = source;
		this.event = event;
	}

	/**
	 * @return the source of the event
	 */
	public PickeableObject getSource() {
		return source;
	}

	/**
	 * @return the event launched
	 */
	public int getEventType() {
		return event;
	}
}
