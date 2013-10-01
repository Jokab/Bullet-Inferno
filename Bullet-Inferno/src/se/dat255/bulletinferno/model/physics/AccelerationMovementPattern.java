package se.dat255.bulletinferno.model.physics;

import com.badlogic.gdx.math.Vector2;

import se.dat255.bulletinferno.model.PhysicsBody;
import se.dat255.bulletinferno.model.PhysicsMovementPattern;

public class AccelerationMovementPattern implements PhysicsMovementPattern {

	private final Vector2 forceVector;
	
	public AccelerationMovementPattern(Vector2 forceVector) {
		this.forceVector = forceVector;
	}
	
	@Override
	public void update(float timeDelta, PhysicsBody body) {
		
		body.getBox2DBody().applyForceToCenter(forceVector, true);
		
	}

}
