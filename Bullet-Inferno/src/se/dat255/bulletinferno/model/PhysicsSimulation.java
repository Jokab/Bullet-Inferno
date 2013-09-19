package se.dat255.bulletinferno.model;

import java.util.List;

public interface PhysicsSimulation {

	// TODO: Fill this in better.
	/**
	 * Updates all the game objects; position, collision, speed, etc.
	 * 
	 * @param delta The time passed since the last update.
	 */
	public void update(float delta);

	/**
	 * Registers Collidables with the PhysicsSimulation object.
	 * 
	 * @param collidables A lif of Collidables.
	 */
	public void setCollidables(List<? extends Collidable> collidables);
}
