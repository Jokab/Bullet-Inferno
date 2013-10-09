package se.dat255.bulletinferno.model.entity;

import se.dat255.bulletinferno.model.physics.PositionEntity;
import se.dat255.bulletinferno.model.team.Teamable;
import se.dat255.bulletinferno.util.Disposable;

public interface Enemy extends PositionEntity, Disposable, Teamable {

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
	
	EnemyDefinitionImpl getType();
}
