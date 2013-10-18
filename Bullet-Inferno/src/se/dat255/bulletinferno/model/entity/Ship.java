package se.dat255.bulletinferno.model.entity;

import se.dat255.bulletinferno.model.physics.Collidable;
import se.dat255.bulletinferno.model.physics.PositionEntity;

/**
 * The main interface for a player ship
 */
public interface Ship extends Collidable, Destructible, PositionEntity {
	
}
