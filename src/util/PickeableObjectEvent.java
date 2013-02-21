package util;

public class PickeableObjectEvent {

	public static final int SELECTED = 0;
	public static final int UNSELECTED = 1;
	private PickeableObject source;
	private int event;
	
	public PickeableObjectEvent(PickeableObject source, int event)
	{
		this.source = source;
		this.event = event;
	}

	/**
	 * @return the source
	 */
	public PickeableObject getSource() {
		return source;
	}

	/**
	 * @return the event
	 */
	public int getEvent() {
		return event;
	}
	
	
}
