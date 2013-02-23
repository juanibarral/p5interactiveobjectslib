package util;

/**
 *  It is an event launched by an Interactive object
 * @author Juan Camilo Ibarra
 * @version 0.5b
 */
public class InteractiveObjectEvent {

	public static final int SELECTED = 0;
	public static final int UNSELECTED = 1;
	private AbstractInteractiveObject source;
	private int event;
	
	/**
	 * Basic Constructor
	 * @param source interactive object that launched the event
	 * @param event event launched
	 */
	public InteractiveObjectEvent(AbstractInteractiveObject source, int event)
	{
		this.source = source;
		this.event = event;
	}

	/**
	 * @return the source of the event
	 */
	public AbstractInteractiveObject getSource() {
		return source;
	}

	/**
	 * @return the event launched
	 */
	public int getEventType() {
		return event;
	}
}
