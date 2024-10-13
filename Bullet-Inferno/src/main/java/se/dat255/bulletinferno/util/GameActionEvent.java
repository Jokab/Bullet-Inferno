package se.dat255.bulletinferno.util;

public interface GameActionEvent {
	/**
	 * Returns the source from which the the action
	 * originated
	 * 
	 * @return source
	 */
	public ResourceIdentifier getSource();

	/**
	 * Returns the GameActionEvent's GameAction.
	 * 
	 * @return The GameAction.
	 */
	public GameAction getAction();
}
