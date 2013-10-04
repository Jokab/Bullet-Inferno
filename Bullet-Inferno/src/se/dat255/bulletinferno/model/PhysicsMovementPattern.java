package se.dat255.bulletinferno.model;

public interface PhysicsMovementPattern {
	/**
	 * Updates the sate of the movement pattern
	 * 
	 * @param timeDelta
	 *        , time since last update in seconds
	 * @param body
	 *        , the body to apply the movement pattern
	 */
	public void update(float timeDelta, PhysicsBody body);
	
	public PhysicsMovementPattern copy();
}
