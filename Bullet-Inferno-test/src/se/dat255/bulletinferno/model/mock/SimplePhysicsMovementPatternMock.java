package se.dat255.bulletinferno.model.mock;

import se.dat255.bulletinferno.model.physics.PhysicsBody;
import se.dat255.bulletinferno.model.physics.PhysicsMovementPattern;

public class SimplePhysicsMovementPatternMock implements PhysicsMovementPattern {

	@Override
	public void update(float timeDelta, PhysicsBody body) {
	}

	@Override
	public PhysicsMovementPattern copy() {
		return new SimplePhysicsMovementPatternMock();
	}

}
