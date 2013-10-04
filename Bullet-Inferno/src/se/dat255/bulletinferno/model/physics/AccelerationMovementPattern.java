package se.dat255.bulletinferno.model.physics;

import com.badlogic.gdx.math.Vector2;


public class AccelerationMovementPattern implements PhysicsMovementPattern {

	private final Vector2 forceVector;
	
	public AccelerationMovementPattern(Vector2 forceVector) {
		this.forceVector = forceVector;
	}
	
	@Override
	public void update(float timeDelta, PhysicsBody body) {
		
		body.getBox2DBody().applyForceToCenter(forceVector, true);
		
	}

	@Override
	public PhysicsMovementPattern copy() {
		
		return new AccelerationMovementPattern(forceVector);
		
	}

}
