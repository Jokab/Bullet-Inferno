package se.dat255.bulletinferno.model.entity;

import se.dat255.bulletinferno.model.physics.PositionEntity;
import se.dat255.bulletinferno.model.team.Teamable;
import se.dat255.bulletinferno.util.Disposable;
import se.dat255.bulletinferno.util.GameActionEvent;
import se.dat255.bulletinferno.util.Listener;
import se.dat255.bulletinferno.util.ResourceIdentifier;

public interface Enemy extends PositionEntity, Disposable, Teamable, ResourceIdentifier {

	/**
	 * Returns the score that you get from destroying this enemy.
	 * 
	 * @return The score received.
	 */
	public int getScore();

	/**
	 * Returns the credits you get from destroying this enemy.
	 * 
	 * @return The credits received.
	 */
	public int getCredits();
	
	/**
	 * Sets the action listener to be notified on enemy actions
	 * @param actionListener
	 */
	public void setActionListener(Listener<GameActionEvent> actionListener);
}
