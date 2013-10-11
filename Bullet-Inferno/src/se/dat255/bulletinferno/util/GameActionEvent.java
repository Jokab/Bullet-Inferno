package se.dat255.bulletinferno.util;

public interface GameActionEvent {
	/**
	 * Returns the source from which the the action
	 * originated
	 * @return source
	 */
	public ResourceIdentifier getSource();
	

	public void getAction();
}
